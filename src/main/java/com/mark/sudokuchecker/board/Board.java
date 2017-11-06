package com.mark.sudokuchecker.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains the Sudoku Board and its parts. The sudoku board has 9 rows, 9
 * columns and 9 boxes with 3x3 size.
 * 
 * Note: this class can be improved at least in two directions:
 * 
 * <ul>
 * <li>
 * <p>
 * can manage other size of sudokus than 9x9, e.g.: 4x4 or 16x16
 * <li>
 * <p>
 * the sections can be extended with the Diagonal class which can hold two
 * diagonals of the matrix
 * </ul>
 * 
 * @author mark
 *
 */
public class Board {
	private static final Logger LOGGER = Logger.getLogger(Board.class.getName());

	private final int[][] sudokuMatrix;
	private final ArrayList<BoardSection> sectionList = new ArrayList<>();

	private final int maxValue;
	private final int boxWidth;

	/**
	 * Constructor, sets the Sudoku default parameters (maxValue = 9, boxWidth =3)
	 * and calls populateBoard method to generate BoardSection elements.
	 * 
	 * @param sudokuMatrix
	 *            the source of the board
	 * @throws BoardException
	 */
	public Board(final int[][] sudokuMatrix) throws BoardException {
		LOGGER.info("Board init, starts.");
		this.sudokuMatrix = sudokuMatrix;
		this.maxValue = 9;
		this.boxWidth = 3;
		LOGGER.log(Level.INFO, "Board init, maxValue= %s", this.maxValue);
		populateBoard(sudokuMatrix);
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info(prettyPrint());
		}
		LOGGER.info("Board init, done.");
	}

	/**
	 * Generate a list of BoardSection elements with its proper class (Row, Column,
	 * Box) and stores it in sectionList class variable.
	 * <p>
	 * Throws <tt>BoardException</tt> if there is any error with the generation of
	 * the section elements.
	 * 
	 * @param matrix
	 *            the source of the board
	 * @throws BoardException
	 */
	private void populateBoard(final int[][] matrix) throws BoardException {
		try {
			LOGGER.info("Board population, starts.");
			// rows
			for (int i = 0; i < maxValue; i++) {
				BoardSection section = new BoardRow(matrix, i);
				LOGGER.log(Level.INFO, "Board row added: %s", section.sectionCode);
				sectionList.add(section);
			}

			// columns
			for (int i = 0; i < maxValue; i++) {
				BoardSection section = new BoardColumn(matrix, i);
				LOGGER.log(Level.INFO, "Board column added: %s", section.sectionCode);
				sectionList.add(section);
			}

			// boxes
			for (int i = 0; i < boxWidth; i++) {
				for (int j = 0; j < boxWidth; j++) {
					BoardSection section = new BoardBox(matrix, i, j, boxWidth);
					LOGGER.log(Level.INFO, "Board box added: %s", section.sectionCode);
					sectionList.add(section);
				}
			}
			LOGGER.info("Board population, done.");
		} catch (Exception e) {
			LOGGER.severe("Validation error:" + e.getMessage());
			throw new BoardException(e.getMessage());
		}

	}

	/**
	 * Returns shallow (not safe) copy of the list of sections
	 * 
	 * @return the list of sections
	 * @throws BoardException
	 */

	@SuppressWarnings("unchecked")
	public List<BoardSection> getSectionList() {
		return (ArrayList<BoardSection>) sectionList.clone();
	}

	/**
	 * Returns a HashMap with the code(K) and valueArray(V) of sections
	 * 
	 * @return the HashMap with the code(K) and valueArray(V) of sections
	 */
	public Map<String, int[]> getSectionMap() {
		HashMap<String, int[]> resultSectionMap = new HashMap<>();

		sectionList.forEach(section ->
			resultSectionMap.put(section.getSectionCode(), section.getValueArray())
		);

		return resultSectionMap;
	}

	/**
	 * Pretty prints the Sudoku matrix
	 */
	public String prettyPrint() {

		StringBuilder printOut = new StringBuilder();
		String columnDelimiter = "|";
		int digits = Integer.toString(maxValue).length();

		StringBuilder boxDelimiter = new StringBuilder();
		boxDelimiter.append("+");
		for (int i = 0; i < boxWidth; i++) {
			for (int j = 0; j < (boxWidth * (digits + 1) - 1); j++) {
				boxDelimiter.append("-");
			}
			boxDelimiter.append("+");
		}

		printOut.append(boxDelimiter).append('\n');
		for (int i = 0; i < maxValue; i++) {
			printOut.append(columnDelimiter);
			for (int j = 0; j < maxValue; j++) {
				printOut.append(sudokuMatrix[i][j]);
				if (j % boxWidth == boxWidth - 1) {
					printOut.append(columnDelimiter);
				} else {
					printOut.append(" ");
				}
			}
			printOut.append('\n');
			if (i % boxWidth == boxWidth - 1) {
				printOut.append(boxDelimiter).append('\n');
			}

		}
		return printOut.toString();
	}
}
