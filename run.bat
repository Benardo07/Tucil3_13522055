@echo off
setlocal

:: Directory variables
set SRC_DIR=src
set BIN_DIR=bin

:: Create bin directory if it doesn't exist
if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"

:: Compile the Java program
echo Compiling Java files from %SRC_DIR% to %BIN_DIR%...
javac -d %BIN_DIR% %SRC_DIR%/*.java

:: Check if compilation was successful
if %ERRORLEVEL% neq 0 (
    echo Compilation failed. Exiting.
    exit /b %ERRORLEVEL%
)

:: Run the Java program
echo Running the program...
java -cp %BIN_DIR% Main

:: End of script
endlocal
