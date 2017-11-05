package com.mark.sudokuchecker;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mark.sudokuchecker.loader.Loader;
import com.mark.sudokuchecker.loader.LoaderException;

public class LoaderTest {

	String path = "src/test/resources/";
	
	@Before
	public void beforeMethod() {
		// Turn off logging
		Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(Level.OFF);
		for (Handler h : rootLogger.getHandlers()) {
		    h.setLevel(Level.OFF);
		}		
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void positiveTest() throws IOException, LoaderException {
		String filename = path + "sudokuGoodFormatInvalid.txt";
		int[][] sudoku = new Loader(filename).load();
		assertEquals("Sudoku should have 9 rows.", 9, sudoku.length);
	}

	@Test
	public void inOneRowLessValuesThanExpected() throws IOException, LoaderException {
		thrown.expect(LoaderException.class);
		thrown.expectMessage(containsString("not enough values"));
		String filename = path + "sudokuLessValue.txt";
		new Loader(filename).load();
	}

	@Test
	public void inOneRowMoreValuesThanExpected() throws IOException, LoaderException {
		thrown.expect(LoaderException.class);
		thrown.expectMessage(containsString("too much values"));
		String filename = path + "sudokuMoreValue.txt";
		new Loader(filename).load();
	}
	
	@Test
	public void withOneRowLessThanExpected() throws IOException, LoaderException {
		thrown.expect(LoaderException.class);
		thrown.expectMessage(containsString("not enough rows"));
		String filename = path + "sudokuLessRow.txt";
		new Loader(filename).load();
	}

	@Test
	public void withOneRowMoreThanExpected() throws IOException, LoaderException {
		thrown.expect(LoaderException.class);
		thrown.expectMessage(containsString("too much rows"));
		String filename = path + "sudokuMoreRow.txt";
		new Loader(filename).load();
	}	
	
	@Test
	public void wrongValueTypeNotNumber() throws IOException, LoaderException {
		thrown.expect(LoaderException.class);
		thrown.expectMessage(containsString("invalid value, number expected"));
		String filename = path + "sudokuNotInt.txt";
		new Loader(filename).load();
	}	
	
	@Test
	public void wrongValueOutOfRange() throws IOException, LoaderException {
		thrown.expect(LoaderException.class);
		thrown.expectMessage(containsString("invalid value, out of range"));
		String filename = path + "sudokuOutOfRange.txt";
		new Loader(filename).load();
	}		

}
