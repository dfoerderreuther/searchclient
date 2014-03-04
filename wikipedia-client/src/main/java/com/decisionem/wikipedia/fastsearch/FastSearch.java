package com.decisionem.wikipedia.fastsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class FastSearch {

	private static final Logger logger = Logger.getLogger(FastSearch.class);
	
	private String endpoint;
	
	public FastSearch(String endpoint) {
		this.endpoint = endpoint;
	}
	
	private String getUrl(String search) {
		String urlPattern = "http://%s/w/api.php?action=query&list=search&srsearch=%s&format=xml&srprop=snippet";
		return String.format(urlPattern, this.endpoint, search);
	}
	
	public List<WikiSearchResult> search(String search) throws JDOMException, IOException  {
		List<WikiSearchResult> ret = new ArrayList<WikiSearchResult>();
		String searchUrl = getUrl(search);
		logger.info(searchUrl);
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(searchUrl);
		List<Element> resultElements = document.getRootElement().getChild("query").getChild("search").getChildren("p");
		for (Element resultElement : resultElements) {
			ret.add(new WikiSearchResult(resultElement.getAttributeValue("title"), resultElement.getAttributeValue("snippet")));
		}
		return ret;
	}

}
