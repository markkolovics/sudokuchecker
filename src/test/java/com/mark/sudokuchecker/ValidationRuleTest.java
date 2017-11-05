package com.mark.sudokuchecker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mark.sudokuchecker.board.BoardRow;
import com.mark.sudokuchecker.validation.ValidationRule;
import com.mark.sudokuchecker.validation.ValidationRuleBitwise;
import com.mark.sudokuchecker.validation.ValidationStatus;

public class ValidationRuleTest {
	int[][] sudoku = {
			{1,2,3,4,5,6,7,8,9},
			{4,5,6,7,8,9,1,2,3},
			{7,8,9,1,2,3,4,5,6},
			{2,3,4,5,6,7,8,9,1},
			{6,7,8,9,1,2,3,4,5},
			{1,5,9,2,8,4,2,1,5},
			{4,5,6,7,8,9,1,2,3},
			{2,3,4,5,6,7,8,9,1},
			{6,7,8,9,1,2,2,4,5}
	};

	@Test
	public void bitwiseValidationRulePositiveTest() {
		BoardRow row = new BoardRow (sudoku,0);
		ValidationRule validationRule = new ValidationRuleBitwise();
		assertEquals("Positive bitwise validation rule test", ValidationStatus.VALID, validationRule.validate(row));
	}
	
	@Test
	public void bitwiseValidationRuleNegativeTest() {
		BoardRow row = new BoardRow (sudoku,8);
		ValidationRule validationRule = new ValidationRuleBitwise();
		assertEquals("Positive bitwise validation rule test", ValidationStatus.INVALID, validationRule.validate(row));
	}	

}
