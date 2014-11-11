@echo off

call "build-notests.bat"

cd "%CURRENT_DIR%/.."

"%HEROKU_CMD%" start web --procfile=Procfile_windows & cd "%CURRENT_DIR%"