package com.mark.sudokuchecker;

import static org.junit.Assert.assertArrayEquals;

import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.mark.sudokuchecker.board.Board;
import com.mark.sudokuchecker.board.BoardException;

public class BoardTest {
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
	public void properExtractionTestOf9x9() throws BoardException {
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
		Map<String, int[]> sudokuMap = board.getSectionMap();
		
		int[] expectedArrayR1 = {4,5,6,7,8,9,1,2,3};
		assertArrayEquals("Row 1",expectedArrayR1, sudokuMap.get("R1"));
		
		int[] expectedArrayR5 = {1,5,9,2,8,4,2,1,5};
		assertArrayEquals("Row 5",expectedArrayR5, sudokuMap.get("R5"));	
		
		int[] expectedArrayC2 = {3,6,9,4,8,9,6,4,8};
		assertArrayEquals("Col 2",expectedArrayC2, sudokuMap.get("C2"));
		
		int[] expectedArrayC7 = {8,2,5,9,4,1,2,9,4};
		assertArrayEquals("Col 7",expectedArrayC7, sudokuMap.get("C7"));
		
		int[] expectedArrayB1 = {4,5,6,7,8,9,1,2,3};
		assertArrayEquals("Box 1",expectedArrayB1, sudokuMap.get("B1"));
		
		int[] expectedArrayB3 = {2,3,4,6,7,8,1,5,9};
		assertArrayEquals("Box 3",expectedArrayB3, sudokuMap.get("B3"));		
	}
	
}
