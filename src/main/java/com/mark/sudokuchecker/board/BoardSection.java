package com.mark.sudokuchecker.board;

/**
 * Abstract class for sudoku board parts.
 *
 * @author mark
 *
 */
public abstract class BoardSection {

	protected int sectionNum;
	protected String sectionCode;
	protected int[] valueArray;

	public int getSectionNum() {
		return sectionNum;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public int[] getValueArray() {
		return valueArray;
	}

}
