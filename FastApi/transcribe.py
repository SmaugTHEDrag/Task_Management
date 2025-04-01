import os
import whisper  # OpenAI Whisper
from fastapi import FastAPI, UploadFile, File, Form, HTTPException, BackgroundTasks
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
from dotenv import load_dotenv
import google.generativeai as genai
import logging
from typing import Optional

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

app = FastAPI(title="Audio Transcription and Paraphrasing API")

# Add CORS middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Allow all origins, can be changed for production
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Load environment variables
load_dotenv()

# Get API Key from .env
GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")
if not GEMINI_API_KEY:
    logger.error("Missing GEMINI_API_KEY in environment variables")
    raise RuntimeError("Missing GEMINI_API_KEY. Set it in .env")

# Initialize Google Gemini API
genai.configure(api_key=GEMINI_API_KEY)

# Create 'temp' folder if it doesn't exist
os.makedirs("temp", exist_ok=True)

# Global variable for the Whisper model
whisper_model = None

def load_whisper_model():
    """Load the Whisper model on startup"""
    global whisper_model
    try:
        whisper_model = whisper.load_model("base")
        logger.info("Whisper model loaded successfully")
        return whisper_model
    except Exception as e:
        logger.error(f"Error loading Whisper model: {str(e)}")
        raise RuntimeError(f"Error loading Whisper model: {str(e)}")

# Load model at startup
@app.on_event("startup")
async def startup_event():
    load_whisper_model()

@app.get("/")
def read_root():
    return {"status": "active", "message": "Audio Transcription and Paraphrasing API"}

def cleanup_temp_file(file_path: str):
    """Remove temporary file after processing"""
    try:
        if os.path.exists(file_path):
            os.remove(file_path)
            logger.info(f"Temporary file removed: {file_path}")
    except Exception as e:
        logger.error(f"Error removing temporary file {file_path}: {str(e)}")

@app.post("/transcribe")
async def transcribe_audio(
    background_tasks: BackgroundTasks,
    file: UploadFile = File(...),
):
    """
    Transcribe audio file to text using Whisper model.
    """
    global whisper_model
    
    # Check if model is loaded
    if whisper_model is None:
        try:
            whisper_model = load_whisper_model()
        except Exception as e:
            raise HTTPException(status_code=500, detail=f"Whisper model not available: {str(e)}")
    
    # Validate file is an audio format
    allowed_extensions = ['.mp3', '.wav', '.m4a', '.flac', '.ogg', '.mp4']
    file_ext = os.path.splitext(file.filename)[1].lower()
    if file_ext not in allowed_extensions:
        raise HTTPException(
            status_code=400, 
            detail=f"Unsupported file format. Supported formats: {', '.join(allowed_extensions)}"
        )
    
    try:
        # Create unique filename to avoid overwrites
        import uuid
        unique_filename = f"{uuid.uuid4()}{file_ext}"
        file_path = f"temp/{unique_filename}"
        
        # Save file to 'temp' folder
        with open(file_path, "wb") as f:
            content = await file.read()
            f.write(content)
        
        logger.info(f"Audio file saved to {file_path}")
        
        # Transcribe audio to text
        try:
            result = whisper_model.transcribe(file_path)
            
            # Schedule file cleanup after processing
            background_tasks.add_task(cleanup_temp_file, file_path)
            
            return JSONResponse(
                content={"text": result["text"], "status": "success"},
                status_code=200
            )
        except Exception as e:
            # Make sure to clean up even if transcription fails
            background_tasks.add_task(cleanup_temp_file, file_path)
            logger.error(f"Transcription error: {str(e)}")
            raise HTTPException(status_code=500, detail=f"Error during transcription: {str(e)}")
            
    except Exception as e:
        logger.error(f"Error processing audio file: {str(e)}")
        raise HTTPException(status_code=500, detail=f"Error processing audio file: {str(e)}")

@app.post("/paraphrase")
async def get_paraphrased_text(
    text: str = Form(...), 
    add_more_details: Optional[str] = Form(""),
    save_output: bool = Form(False)
):
    """
    Paraphrases the given text and optionally adds more details to it.
    """
    try:
        # Check if 'text' is not empty
        if not text.strip():
            raise HTTPException(status_code=400, detail="Text cannot be empty")
        
        # Use Gemini API to generate the paraphrased response
        try:
            model = genai.GenerativeModel("gemini-2.0-flash")
            
            # Create a prompt to paraphrase the given text (only the paraphrased content)
            prompt = f"Paraphrase the following text into a professional and concise paragraphs:\n\n{text}\n\nResponse:"
            
            # Generate paraphrased text using Gemini API
            response = model.generate_content(prompt)

            # Extract the generated content (ensure to trim any extra spaces)
            if not hasattr(response, 'text'):
                raise HTTPException(
                    status_code=500, 
                    detail="Gemini API returned an unexpected response format"
                )

            generated_text = response.text.strip()  # Ensure there's no unwanted space or newlines

            # If the user wants more details added, incorporate them
            if add_more_details and add_more_details.strip():
                # Add more professional details if requested, without repeating the original input
                detail_prompt = f"{generated_text}\n\n{add_more_details.strip()}"
                 # Replace the generated text with the combined result

            
            # Save the paraphrased result to a .txt file if requested
            output_path = None
            if save_output:
                import time
                timestamp = int(time.time())
                output_path = f"temp/paraphrased_text_{timestamp}.txt"
                with open(output_path, "w", encoding="utf-8") as f:
                    f.write(generated_text)
                logger.info(f"Paraphrased text saved to {output_path}")
            
            return JSONResponse(
                content={"paraphrased_text": generated_text},
                status_code=200
            )
            
        except Exception as e:
            logger.error(f"Gemini API error: {str(e)}")
            raise HTTPException(status_code=500, detail=f"Error with Gemini API: {str(e)}")
            
    except HTTPException:
        # Re-raise HTTP exceptions to preserve status codes
        raise
    except Exception as e:
        logger.error(f"Unexpected error: {str(e)}")
        raise HTTPException(status_code=500, detail=f"Unexpected error: {str(e)}")


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
