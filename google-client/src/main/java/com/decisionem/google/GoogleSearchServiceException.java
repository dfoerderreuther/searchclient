package com.decisionem.google;

@SuppressWarnings("serial")
public class GoogleSearchServiceException extends Exception {

	public GoogleSearchServiceException(Exception e) {
		addSuppressed(e);
	}

}
