#!/bin/bash

# Directory variables
SRC_DIR="src"
BIN_DIR="bin"

# Create bin directory if it doesn't exist
if [ ! -d "$BIN_DIR" ]; then
    mkdir "$BIN_DIR"
fi

# Compile the Java program
echo "Compiling Java files from $SRC_DIR to $BIN_DIR..."
javac -d "$BIN_DIR" "$SRC_DIR"/*.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed. Exiting."
    exit 1
fi

# Run the Java program
echo "Running the program..."
java -cp "$BIN_DIR" Main

# End of script
