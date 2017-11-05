package com.mark.sudokuchecker;

import static org.junit.Assert.assertTrue;

import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.ExpectedException;

/**
 * End to end unit test of the Main method for 0-3 and 99 exit codes.
 * 
 * @author mark
 *
 */
public class EndToEndTest {
	String path = "src/test/resources/";
	
	/**
	 * Suppress console messages for these tests. 
	 */
	@Before
	public void beforeAll () {
	    System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                //DO NOTHING
            }
        }));
	}

	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void noInputParameterTest() {
		exit.expectSystemExitWithStatus(1);
		String[] args = {};
		SudokuChecker.main(args);
		assertTrue(true);
	}

	@Test
	public void tooMuchInputParameterTest() {
		exit.expectSystemExitWithStatus(2);
		String[] args = { "sudoku.txt", "sudoku2.txt" };
		SudokuChecker.main(args);
		assertTrue(true);
	}

	@Test
	public void LoaderErrorMissingFileTest() {
		exit.expectSystemExitWithStatus(3);
		String[] args = { path + "missingsudoku.txt" };
		SudokuChecker.main(args);
		assertTrue(true);
	}

	@Test
	public void LoaderErrorFileContentDevianceTest() {
		exit.expectSystemExitWithStatus(3);
		String[] args = { path + "sudokuLessValue.txt" };
		SudokuChecker.main(args);
		assertTrue(true);
	}
	
	@Test
	public void properFileInvalidSudokuTest() {
		exit.expectSystemExitWithStatus(99);
		String[] args = { path + "sudokuGoodFormatInvalid.txt" };
		SudokuChecker.main(args);
		assertTrue(true);
	}	
	
	@Test
	public void helpArgumentTest() {
		exit.expectSystemExitWithStatus(0);
		String[] args = { "-h" };
		SudokuChecker.main(args);
		assertTrue(true);
	}	
	
	@Test
	public void properFileValidSudokuTest() {
		exit.expectSystemExitWithStatus(0);
		String[] args = { path + "sudokuValid.txt" };
		SudokuChecker.main(args);
		assertTrue(true);
	}		

}
