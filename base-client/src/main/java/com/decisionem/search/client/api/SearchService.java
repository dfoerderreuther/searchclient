package com.decisionem.search.client.api;

import java.util.List;

public interface SearchService {

	public List<SearchResult> search(String search) throws SearchException;
	
}
