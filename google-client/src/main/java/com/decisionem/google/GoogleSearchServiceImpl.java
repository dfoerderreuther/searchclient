package com.decisionem.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.decisionem.google.model.HttpResult;
import com.decisionem.google.model.Result;
import com.decisionem.search.client.SearchResultImpl;
import com.decisionem.search.client.api.SearchException;
import com.decisionem.search.client.api.SearchResult;
import com.decisionem.search.client.api.SearchService;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

public class GoogleSearchServiceImpl implements SearchService {

	private static final Logger logger = Logger
			.getLogger(GoogleSearchServiceImpl.class);

	private HttpTransport httpTransport = new NetHttpTransport();
	private HttpRequestFactory requestFactory =
			httpTransport.createRequestFactory(new HttpRequestInitializer() {
	            @Override
	          public void initialize(HttpRequest request) {
	            request.setParser(new JsonObjectParser(new JacksonFactory()));
	          }
	        });

	public GoogleSearchServiceImpl(String endpoint) {
		logger.info("init GoogleSearchServiceImpl");
		init(endpoint);
	}

	public GoogleSearchServiceImpl(Properties properties)
			throws GoogleSearchServiceException {
		logger.info("init GoogleSearchServiceImpl");
		String endpoint = properties.getProperty("google.endpoint");

		init(endpoint);
	}

	private void init(String endpoint) {

	}

	private List<Result> searchImpl(String search)
			throws GoogleSearchServiceException, IOException {
		String searchUrl = String.format("http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=%s", search);
		GenericUrl url = new GenericUrl(searchUrl.replaceAll(" ", "%20"));
		HttpRequest request = requestFactory.buildGetRequest(url);
		HttpResponse response = request.execute();
		return parseResponse(response);

	}

	private static List<Result> parseResponse(HttpResponse response) throws IOException {
		HttpResult httpResult = response.parseAs(HttpResult.class);
		if (!httpResult.getResponseData().getResults().isEmpty()) {
			return httpResult.getResponseData().getResults();
		}
		return new ArrayList<Result>();
	}

	@Override
	public List<SearchResult> search(String search) throws SearchException { 
		List<SearchResult> list = new ArrayList<SearchResult>();
		try {
			List<Result> items = this.searchImpl(search);
			for (Result item : items) {
				SearchResultImpl result = new SearchResultImpl();
				result.setTitle(item.getTitle());
				result.setDescription(item.getContent());
				result.setUrl(item.getUrl());
				list.add(result);
			}
		} catch (GoogleSearchServiceException e) {
			throw new SearchException(e);
		} catch (IOException e) {
			throw new SearchException(e);
		}
		return list;
	}

}
