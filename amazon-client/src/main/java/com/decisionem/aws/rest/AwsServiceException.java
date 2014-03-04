package com.decisionem.aws.rest;

@SuppressWarnings("serial")
public class AwsServiceException extends Exception {

	public AwsServiceException(Exception e) {
		addSuppressed(e);
	}

}
