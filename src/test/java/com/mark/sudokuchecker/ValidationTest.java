package com.mark.sudokuchecker;

import static org.junit.Assert.assertEquals;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.mark.sudokuchecker.board.Board;
import com.mark.sudokuchecker.board.BoardException;
import com.mark.sudokuchecker.validation.Validation;
import com.mark.sudokuchecker.validation.ValidationException;
import com.mark.sudokuchecker.validation.ValidationResult;
import com.mark.sudokuchecker.validation.ValidationRuleBitwise;
import com.mark.sudokuchecker.validation.ValidationStatus;

public class ValidationTest {
	@Before
	public void beforeMethod() {
		// Turn off logging
		Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(Level.OFF);
		for (Handler h : rootLogger.getHandlers()) {
		    h.setLevel(Level.OFF);
		}		
	}

	@Test
	public void positiveValidationTest() throws ValidationException, BoardException {
		int[][] sudoku = {
				{4,3,5,2,6,9,7,8,1},
				{6,8,2,5,7,1,4,9,3},
				{1,9,7,8,3,4,5,6,2},
				{8,2,6,1,9,5,3,4,7},
				{3,7,4,6,8,2,9,1,5},
				{9,5,1,7,4,3,6,2,8},
				{5,1,9,3,2,6,8,7,4},
				{2,4,8,9,5,7,1,3,6},
				{7,6,3,4,1,8,2,5,9}
				};
		Board board = new Board(sudoku);
		ValidationResult result = new Validation().validate(board, ValidationRuleBitwise.class);
		assertEquals("Valid sudoku test", ValidationStatus.VALID,result.getStatus());
	}
	
	@Test
	public void negativeValidationTest() throws ValidationException, BoardException {
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
		Board board = new Board(sudoku);
		ValidationResult result = new Validation().validate(board, ValidationRuleBitwise.class);
		assertEquals("Invalid sudoku test", ValidationStatus.INVALID,result.getStatus());
	}	

}
