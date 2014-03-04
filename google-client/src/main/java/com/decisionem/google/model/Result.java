package com.decisionem.google.model;

import com.google.api.client.util.Key;

public class Result {
	
	@Key
	private String unescapedUrl;
	
	@Key
	private String url;
	
	@Key
	private String visibleUrl;
	
	@Key
	private String cacheUrl;
	
	@Key
	private String title;
	
	@Key
	private String titleNoFormatting;
	
	@Key
	private String content;
	
	public Result() { }

	public String getUnescapedUrl() {
		return unescapedUrl;
	}

	public void setUnescapedUrl(String unescapedUrl) {
		this.unescapedUrl = unescapedUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVisibleUrl() {
		return visibleUrl;
	}

	public void setVisibleUrl(String visibleUrl) {
		this.visibleUrl = visibleUrl;
	}

	public String getCacheUrl() {
		return cacheUrl;
	}

	public void setCacheUrl(String cacheUrl) {
		this.cacheUrl = cacheUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleNoFormatting() {
		return titleNoFormatting;
	}

	public void setTitleNoFormatting(String titleNoFormatting) {
		this.titleNoFormatting = titleNoFormatting;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
