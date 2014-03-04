package com.decisionem.wikipedia;

@SuppressWarnings("serial")
public class WikipediaSearchException extends Exception {

	public WikipediaSearchException(Exception e) {
		this.addSuppressed(e);
	}

}
