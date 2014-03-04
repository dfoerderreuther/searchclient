package com.decisionem.search.client.api;

public class SearchException extends Exception {

	private static final long serialVersionUID = 7252651881985132128L;
	
	public SearchException() {
		
	}
	
	public SearchException(Throwable e) {
		this.addSuppressed(e);
	}
	

}
