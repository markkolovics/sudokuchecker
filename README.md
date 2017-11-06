# Sudoku Checker Command Line Tool

## Requirements
Create a java based command line tool for validating a standard 9x9 Sudoku puzzle.

Command line: `validate.bat puzzleName.txt`

File format: CSV format each line representing a row e.g.: 
```
 1,2,3,4,5,6,7,8,9 
 1,2,3,4,5,6,7,8,9 
 1,2,3,4,5,6,7,8,9 
 1,2,3,4,5,6,7,8,9 
 1,2,3,4,5,6,7,8,9 
 1,2,3,4,5,6,7,8,9 
 1,2,3,4,5,6,7,8,9 
 1,2,3,4,5,6,7,8,9 
 1,2,3,4,5,6,7,8,9 
```
The program should print **VALID** or **INVALID** on `stdout` with the error text (in case of an invalid solution or file) and an appropriate status code. 
 
There should be unit tests covering a range of error conditions and the project should be maven based. 
It should be possible to unpack the code from a zip, generate a surefire report, build it and use a batch file to call the code from a packaged jar. 
 

## Solution

#### Exit codes

N | Description
:---:| :---
0 | Normal exit, sudoku is VALID
1 | Missing command line parameter 
2 | Too many command line parameters
3 | File loading error with various reasons, e.g.: missing file, malformed content.
4 | Board population error.
5 | Validation error with cause.
98 | General error with cause.
99 | Sudoku is INVALID 


#### Usage and unit test
The application is built as a maven project, to install please follow these steps:

1.	Download and unzip the source to the destination folder or use `git pull` 
2.	Run unit test with `mvn test`
3.  Build the application with `mvn package`
4.  Run `/SudokuChecker/bin/validate.bat` in the `target` folder

> The logging is `OFF` by default and can be override with the SUDOKU_CHECKER_LOGLEVEL environment variable, e.g.:
> on Windows: 'set SUDOKU_CHECKER_LOGLEVEL=ALL'
> on Unix: 'export SUDOKU_CHECKER_LOGLEVEL=ALL' 


