package com.mark.sudokuchecker.validation;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 3413695356703777850L;

	public ValidationException (String message) {
		super(message);
	}
}
