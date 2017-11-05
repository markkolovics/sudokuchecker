package com.mark.sudokuchecker.board;

/**
 * Represents a box in the sudoku board.
 * @author mark
 *
 */
public class BoardBox extends BoardSection {

	public BoardBox(final int[][] board, final int sectionNumV, final int sectionNumH, final int boxWidth) {
		this.sectionNum = sectionNumV * boxWidth + sectionNumH;
		this.sectionCode = "B" + sectionNum;
		this.valueArray = new int[board.length];

		for (int i = 0; i < boxWidth; i++) {
			for (int j = 0; j < boxWidth; j++) {
				this.valueArray[i * boxWidth + j] = board[sectionNumV * boxWidth + i][sectionNumH * boxWidth + j];
			}
		}
	}
}
