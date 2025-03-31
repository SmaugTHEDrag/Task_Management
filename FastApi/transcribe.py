import os
import whisper  # OpenAI Whisper
from fastapi import FastAPI, UploadFile, File

app = FastAPI()

# Tạo thư mục 'temp' nếu chưa có
os.makedirs("temp", exist_ok=True)

# Load model của Whisper
model = whisper.load_model("base")

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.post("/transcribe")
async def transcribe_audio(file: UploadFile = File(...)):
    file_path = f"temp/{file.filename}"

    # Lưu file vào thư mục 'temp'
    with open(file_path, "wb") as f:
        f.write(await file.read())

    # Chuyển giọng nói thành văn bản
    result = model.transcribe(file_path)

    return {"text": result["text"]}
