package com.decisionem.aws.rest;

public class AwsSearchParams {

	public enum Type  {
		SIMPLE
	}
	
	private Type type;
	private String text;

	public AwsSearchParams() {
		
	}
	
	public static AwsSearchParams createSimpleSearch(String text) {
		AwsSearchParams ret = new AwsSearchParams();
		ret.setType(Type.SIMPLE);
		ret.setText(text);
		return ret;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
