package com.decisionem.search.client;

import com.decisionem.search.client.api.SearchResult;

public class SearchResultImpl implements SearchResult {
	
	private String title = "";
	private String description = "";
	private String url = "";

	@Override
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String link) {
		this.url = link;
	}

}
