package com.decisionem.wikipedia;

import info.bliki.api.Page;
import info.bliki.api.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom2.JDOMException;

import com.decisionem.search.client.SearchResultImpl;
import com.decisionem.search.client.api.SearchException;
import com.decisionem.search.client.api.SearchResult;
import com.decisionem.search.client.api.SearchService;
import com.decisionem.wikipedia.fastsearch.FastSearch;
import com.decisionem.wikipedia.fastsearch.WikiSearchResult;

public class WikipediaServiceImpl implements SearchService {

	private static final Logger logger = Logger.getLogger(WikipediaServiceImpl.class);
	
	private FastSearch fastSearch;
	private User user;
	
	private String endpoint;
	
	public WikipediaServiceImpl(String endpoint) {
		init(endpoint);
	}
	
	public WikipediaServiceImpl(Properties properties) {
		logger.info("init WikipediaServiceImpl");
		String endpoint = properties.getProperty("wikipedia.endpoint");
		init(endpoint);
	}
	
	private String getDetailUrl(String title) {
		return String.format("http://%s/wiki/%s", endpoint, title.replaceAll(" ", "_"));
	}
	
	private void init(String endpoint) {
		this.endpoint = endpoint;
		this.fastSearch = new FastSearch(endpoint);
		this.user = new User("", "", String.format("http://%s/w/api.php", endpoint));
	}
	
	public List<WikiSearchResult> searchImpl(String search) throws WikipediaSearchException {
		try {
			return fastSearch.search(search);
		} catch (JDOMException | IOException e) {
			throw new WikipediaSearchException(e);
		}
	}
	
	public Page getPage(String title) {
		user.login();
		List<Page> listOfPages = user.queryContent(new String[]{title});
		for (Page page : listOfPages) {
			return page;
		}
		return null;
	}


	@Override
	public List<SearchResult> search(String search) throws SearchException {
		List<SearchResult> list = new ArrayList<SearchResult>();
		try {
			List<WikiSearchResult> items = this.searchImpl(search);
			for (WikiSearchResult item : items) {
				SearchResultImpl result = new SearchResultImpl();
				result.setTitle(item.getTitle());
				result.setDescription(item.getSnippet());
				result.setUrl(this.getDetailUrl(item.getTitle()));
				list.add(result);
			}
		} catch (WikipediaSearchException e) {
			throw new SearchException(e);
		}
		return list;
	}
}
