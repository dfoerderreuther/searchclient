package com.decisionem.wikipedia.fastsearch;

public class WikiSearchResult {

	private String title;
	private String snippet;

	public WikiSearchResult() {

	}

	public WikiSearchResult(String title, String snippet) {
		this.title = title;
		this.snippet = snippet;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String description) {
		this.snippet = description;
	}

}
