package com.mark.sudokuchecker.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

	private static final int MAX_VALUE = 9;
	private static final int MIN_VALUE = 1;

	private final String fileName;

	public Loader(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Loads the sudoku file, calls the parser and returns with the parsed sudoku
	 * matrix.
	 * <p>
	 * Upon <tt>IOException</tt> throws a <tt>LoaderException</tt> with the
	 * filename.
	 * 
	 * @return a fully parsed sudoku matrix.
	 * @throws LoaderException
	 */
	public int[][] load() throws LoaderException {
		try (Stream<String> stream = Files.lines(Paths.get(this.fileName))) {
			String[] stringArray = stream.toArray(String[]::new);
			return parse(stringArray);
		} catch (IOException e) {
			throw new LoaderException("File reading error:" + this.fileName);
		}
	}

	/**
	 * Parses the input CSV style stringArray to sudoku matrix.
	 * 
	 * In case of the following parsing error throws a <tt>LoaderException</tt> with
	 * the reason:
	 * <ul>
	 * <li>
	 * <p>
	 * not enough (less than 9) rows in the file
	 * <li>
	 * <p>
	 * too much (more than 9) rows in the file
	 * <li>
	 * <p>
	 * not enough (less than 9) values in a row
	 * <li>
	 * <p>
	 * too much (more than 9) values in a row
	 * <li>
	 * <p>
	 * value is out of range (value < 1 or value > 9)
	 * <li>
	 * <p>
	 * value has wrong type, cannot convert to int.
	 * </ul>
	 * 
	 * @param stringArray
	 *            input string array with the sudoku lines
	 * @return a fully parsed sudoku matrix.
	 * @throws LoaderException
	 */
	public int[][] parse(String[] stringArray) throws LoaderException {
		int[][] result = new int[MAX_VALUE][MAX_VALUE];

		if (stringArray == null || stringArray.length < result.length) {
			String errorText = String.format("Loading error: not enough rows (%s), expected %s",
					(stringArray == null ? 0 : stringArray.length), result.length);
			LOGGER.severe(errorText);
			throw new LoaderException(errorText);

		} else if (stringArray.length > result.length) {
			String errorText = String.format("Loading error: too much rows (%s), expected %s", stringArray.length,
					result.length);
			LOGGER.severe(errorText);
			throw new LoaderException(errorText);
		}

		for (int i = 0; i < stringArray.length; i++) {
			String[] numbers = stringArray[i].split(",");

			if (numbers == null || numbers.length < MAX_VALUE) {

				String errorText = String.format("Loading error (line %s): not enough values (%s), expected %s",
						(i + 1), (numbers == null ? 0 : numbers.length), MAX_VALUE);

				LOGGER.severe(errorText);
				throw new LoaderException(errorText);

			} else if (numbers.length > MAX_VALUE) {

				String errorText = String.format("Loading error (line %s): too much values (%s), expected %s", (i + 1),
						numbers.length, MAX_VALUE);

				LOGGER.severe(errorText);
				throw new LoaderException(errorText);
			}

			for (int j = 0; j < result[i].length; j++) {
				try {
					// Small tuning:trim whitespace
					result[i][j] = Integer.parseInt(numbers[j].trim());

					if (result[i][j] < MIN_VALUE || result[i][j] > MAX_VALUE) {

						String errorText = String.format(
								"Loading error (line %s, pos %s, value[%s]): invalid value, out of range (%s to %s).",
								(i + 1), (j + 1), numbers[j], MIN_VALUE, MAX_VALUE);

						LOGGER.severe(errorText);
						throw new LoaderException(errorText);
					}
				} catch (NumberFormatException e) {
					String errorText = String.format(
							"Loading error (line %s, pos %s value[%s]): invalid value, number expected.", (i + 1),
							(j + 1), numbers[j]);
					LOGGER.severe(errorText);
					throw new LoaderException(errorText);
				}
			}
		}

		return result;
	}

}
