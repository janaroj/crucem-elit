@echo off

echo "Setting Enviroment..."

set "CURRENT_DIR=%cd%"

set "JAVA_OPTS= -Xmx384m -Xss512k"
set "MAVEN_OPTS=-Xmx384m -Xss512k"

set "DATABASE_URL=postgres://vvkzfbhgijnfop:xeX1a_YN7yfVmUrxZ9oP0cssMc@ec2-54-228-195-52.eu-west-1.compute.amazonaws.com:5432/d2ai3r0vflsvog"

:: ----- Set Commands -----

set "MVN_CMD=C:\soft\apache-maven-3.1.1\bin\mvn"
set "HEROKU_CMD=C:\Program Files (x86)\Heroku\bin\foreman"

:: ----- Finished -----

echo "Enviroment setup finished."


