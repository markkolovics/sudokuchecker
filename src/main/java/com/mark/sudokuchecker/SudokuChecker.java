/**
 * 
 */
package com.mark.sudokuchecker;

import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.mark.sudokuchecker.board.Board;
import com.mark.sudokuchecker.board.BoardException;
import com.mark.sudokuchecker.loader.Loader;
import com.mark.sudokuchecker.loader.LoaderException;
import com.mark.sudokuchecker.validation.Validation;
import com.mark.sudokuchecker.validation.ValidationException;
import com.mark.sudokuchecker.validation.ValidationResult;
import com.mark.sudokuchecker.validation.ValidationRuleBitwise;
import com.mark.sudokuchecker.validation.ValidationStatus;

/**
 * @author mark
 *
 */
public final class SudokuChecker {

	private static final Logger LOGGER = Logger.getLogger(SudokuChecker.class.getName());

	private static final String SUDOKU_CHECKER_LOGLEVEL = "SUDOKU_CHECKER_LOGLEVEL";

	private static void setLogLevel(Level logLevel) {
		Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(logLevel);
		for (Handler h : rootLogger.getHandlers()) {
			h.setLevel(logLevel);
		}
	}

	private static void checkEnvironmentVariables() {
		Map<String, String> env = System.getenv();

		if (env.containsKey(SUDOKU_CHECKER_LOGLEVEL)) {
			try {
				Level logLevel = Level.parse(env.get(SUDOKU_CHECKER_LOGLEVEL));
				setLogLevel(logLevel);
			} catch (Exception e) {
				// Should be System.err since logging is off
				System.err.println("Invalid value for SUDOKU_CHECKER_LOGLEVEL environment variable: "
						+ env.get(SUDOKU_CHECKER_LOGLEVEL));
				System.err.println(
						"Valid values: ALL, CONFIG, FINE, FINER, FINEST, INFO, SEVERE, WARNING, OFF (default).");
				System.err.println("");
			}
		}
	}

	public static void printResult(ValidationStatus status, String reason) {
		// Standard output
		System.out.println(status);
		System.out.println("Cause: " + reason);
	}

	public static void printHelp() {
		// Standard output
		System.out.println("Usage: sudokuchecker <filename>|-h|--help");
		System.out.println("");
		System.out.println(
				"<filename>      Checks the sudoku in the parameter file. The sudoku should be in CSV format,");
		System.out.println("                the size should be 9x9 and can contain only numbers from 1 to 9.");
		System.out.println("");
		System.out.println("-h --help       Shows this help text.");
	}

	/**
	 * Command Line Sudoku Checker Application
	 * 
	 * Workflow Load CSV -> Populate Board -> Validate -> Display Results
	 * 
	 * @param args
	 *            command line parameters
	 */
	public static void main(String[] args) {

		int exitStatus = 0;
		try {

			// Default loglevel is OFF
			setLogLevel(Level.OFF);

			LOGGER.info("Sudokuchecker started.");

			// Environment variable check
			checkEnvironmentVariables();

			// Parameter checks only 1 but that is needed
			if (args == null || args.length == 0) {
				printResult(ValidationStatus.INVALID, "No filename parameter added.");
				printHelp();
				exitStatus = 1;
			} else if (args.length > 1) {
				printResult(ValidationStatus.INVALID, "Too many parameters(" + args.length + ")");
				printHelp();
				exitStatus = 2;
			} else if (args[0].equalsIgnoreCase("-h") || args[0].equalsIgnoreCase("--help")) {
				printHelp();
				exitStatus = 0;
			} else {

				// File loading
				int[][] sudoku = new Loader(args[0]).load();
				// Populating the board
				Board board = new Board(sudoku);
				// Validation
				ValidationResult result = new Validation().validate(board, ValidationRuleBitwise.class);

				printResult(result.getStatus(), result.getStatusText());

				if (result.getStatus() == ValidationStatus.INVALID) {
					exitStatus = 99;
				}
			}
		} catch (LoaderException e) {
			printResult(ValidationStatus.INVALID, e.getMessage());
			exitStatus = 3;
		} catch (BoardException e) {
			printResult(ValidationStatus.INVALID, e.getMessage());
			exitStatus = 4;
		} catch (ValidationException e) {
			printResult(ValidationStatus.INVALID, e.getMessage());
			exitStatus = 5;
		} catch (Exception e) {
			printResult(ValidationStatus.INVALID, e.getMessage());
			exitStatus = 98;
		}
		System.exit(exitStatus);

	}

}
