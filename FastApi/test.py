import whisper

model = whisper.load_model("base")
result = model.transcribe("temp/32-21634-0002.flac")
print(result["text"])