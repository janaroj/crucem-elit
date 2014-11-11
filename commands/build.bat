@echo off

:: ----- Set Enviroment -----

call "set-env.bat"

cd "%CURRENT_DIR%/.."

"%MVN_CMD%" clean install && cd "%CURRENT_DIR%"




