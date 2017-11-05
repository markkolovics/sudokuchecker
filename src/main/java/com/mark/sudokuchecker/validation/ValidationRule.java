package com.mark.sudokuchecker.validation;

import com.mark.sudokuchecker.board.BoardSection;

/**
 * Abstract class for sudoku validation rules.
 * 
 * @author mark
 *
 */
public abstract class ValidationRule {
	public abstract ValidationStatus validate (BoardSection section);
}
