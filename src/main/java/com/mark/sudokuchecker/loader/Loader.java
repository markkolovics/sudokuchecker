package com.mark.sudokuchecker.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Class for loading and parsing CSV files for sudoku checking.
 * 
 * @author mark
 *
 */
public class Loader {
	private static final Logger LOGGER = Logger.getLogger(Loader.class.getName());

	private static final int maxValue = 9;
	private static final int minValue = 1;

	private final String fileName;
	
	public Loader(String fileName) {
		this.fileName = fileName;
	}

	
	/**
	 * Loads the sudoku file, calls the parser and returns with the parsed sudoku matrix.
	 * <p>
	 * Upon <tt>IOException</tt> throws a <tt>LoaderException</tt> with the filename.
	 * 
	 * @return a fully parsed sudoku matrix.
	 * @throws LoaderException
	 */
	public int[][] load() throws LoaderException {
		try (Stream<String> stream = Files.lines(Paths.get(this.fileName))) {
			String[] stringArray = stream.toArray(String[]::new);
			return parse(stringArray);
		} catch (IOException e) {
			throw new LoaderException ("File reading error:"+ this.fileName);
		}
	}

	/**
	 * Parses the input CSV style stringArray to sudoku matrix.
	 * 
	 * In case of the following parsing error throws a <tt>LoaderException</tt> with the reason:
	 * <ul>
	 * <li><p> not enough (less than 9) rows in the file
	 * <li><p> too much (more than 9) rows in the file
	 * <li><p> not enough (less than 9) values in a row
	 * <li><p> too much (more than 9) values in a row 
	 * <li><p> value is out of range (value < 1 or value > 9)
	 * <li><p> value has wrong type, cannot convert to int.
	 * </ul>
	 * 
	 * @param stringArray input string array with the sudoku lines
	 * @return a fully parsed sudoku matrix.
	 * @throws LoaderException
	 */
	public int[][] parse(String[] stringArray) throws LoaderException {
		int[][] result = new int[maxValue][maxValue];

		if (stringArray == null || stringArray.length < result.length) {
			LOGGER.log(Level.SEVERE,"Loading error: not enough rows (" + stringArray.length + "), expected " + result.length);
			throw new LoaderException(
					"Loading error: not enough rows (" + stringArray.length + "), expected " + result.length);
		} else if (stringArray.length > result.length) {
			LOGGER.severe("Loading error: too much rows (" + stringArray.length + "), expected " + result.length);
			throw new LoaderException(
					"Loading error: too much rows (" + stringArray.length + "), expected " + result.length);
		}

		for (int i = 0; i < stringArray.length; i++) {
			String[] numbers = stringArray[i].split(",");
			if (numbers == null || numbers.length < maxValue) {
				LOGGER.severe("Loading error (line " + (i + 1) + "): not enough values ("
						+ numbers.length + "), expected " + maxValue);
				throw new LoaderException("Loading error (line " + (i + 1) + "): not enough values ("
						+ numbers.length + "), expected " + maxValue);
			} else if (numbers.length > maxValue) {
				LOGGER.severe("Loading error (line " + (i + 1) + "): too much values ("
						+ numbers.length + "), expected " + maxValue);
				throw new LoaderException("Loading error (line " + (i + 1) + "): too much values ("
						+ numbers.length + "), expected " + maxValue);
			}

			for (int j = 0; j < result[i].length; j++) {
				try {
					// Small tuning:trim whitespace
					result[i][j] = Integer.parseInt(numbers[j].trim());
					if (result[i][j] < minValue || result[i][j] > maxValue) {
						LOGGER.severe("Loading error (line " + (i + 1) + ", pos " + (j + 1) + " value[" + numbers[j]
								+ "]): invalid value, out of range (" + minValue + " to " + maxValue + ").");
						throw new LoaderException(
								"Loading error (line " + (i + 1) + ", pos " + (j + 1) + " value[" + numbers[j]
										+ "]): invalid value, out of range (" + minValue + " to " + maxValue + ").");
					}
				} catch (NumberFormatException e) {
					LOGGER.severe("Loading error (line " + (i + 1) + ", pos " + (j + 1) + " value["
							+ numbers[j] + "]): invalid value, number expected.");
					throw new LoaderException("Loading error (line " + (i + 1) + ", pos " + (j + 1) + " value["
							+ numbers[j] + "]): invalid value, number expected.");
				}
			}
		}

		return result;
	}

}
