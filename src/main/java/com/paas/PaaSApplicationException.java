package com.paas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaaSApplicationException extends RuntimeException {

	private static final long serialVersionUID = 4650037236983286671L;

	private String errorMessage;

	public PaaSApplicationException() {

	}

	public PaaSApplicationException(String message) {
		super(message);
		errorMessage = message;
	}

	public <T> PaaSApplicationException(Class<T> aClass, String message) {
		Logger LOG = LoggerFactory.getLogger(aClass);
		errorMessage = message;
		LOG.error(message);
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
