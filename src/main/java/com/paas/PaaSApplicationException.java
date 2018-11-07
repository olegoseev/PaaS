package com.paas;

public class PaaSApplicationException extends RuntimeException {


	private static final long serialVersionUID = 4650037236983286671L;

	private String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public PaaSApplicationException() {
		
	}

	public PaaSApplicationException(String message) {
//		super(message);
		errorMessage = message;
	}
	
	public PaaSApplicationException(Throwable cause) {
		super(cause);
	}

	public PaaSApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaaSApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
