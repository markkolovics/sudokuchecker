package com.mark.sudokuchecker.validation;

public class ValidationResult {
	private ValidationStatus status;
	private String statusText;
	
	public ValidationResult(ValidationStatus status, String statusText) {
		super();
		this.status = status;
		this.statusText = statusText;
	}

	public ValidationStatus getStatus() {
		return status;
	}

	public String getStatusText() {
		return statusText;
	}
	
}
