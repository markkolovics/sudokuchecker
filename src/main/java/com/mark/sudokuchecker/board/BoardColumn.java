package com.mark.sudokuchecker.board;

/**
 * Represents a column in the sudoku board.
 * 
 * @author mark
 *
 */
public class BoardColumn extends BoardSection {

	public BoardColumn(final int[][] board, final int sectionNum) {
		this.sectionNum = sectionNum;
		this.sectionCode = "C" + sectionNum;
		this.valueArray = new int[board.length];
		for (int i=0;i<board.length;i++) {
		this.valueArray[i] = board[i][sectionNum];
		}
	}

}
