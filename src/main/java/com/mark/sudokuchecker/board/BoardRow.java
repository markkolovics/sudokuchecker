package com.mark.sudokuchecker.board;

/**
 * Represents a row in the sudoku board.
 * @author mark
 *
 */
public class BoardRow extends BoardSection {

	public BoardRow(final int[][] board, final int sectionNum) {
		this.sectionNum = sectionNum;
		this.sectionCode = "R" + sectionNum;
		this.valueArray = board[sectionNum];
	}

}
