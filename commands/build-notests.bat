@echo off

:: ----- Set Enviroment -----

call "set-env.bat"

cd "%CURRENT_DIR%/.."

"%MVN_CMD%" clean install -DskipTests && cd "%CURRENT_DIR%"




