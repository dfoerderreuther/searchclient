package com.decisionem.wikipedia;

import static org.junit.Assert.assertNotNull;
import info.bliki.api.Page;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.decisionem.wikipedia.fastsearch.WikiSearchResult;

@ContextConfiguration("/com/decisionem/wikipedia/app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class WikipediaServiceTest {

	private static final Logger logger = Logger.getLogger(WikipediaServiceTest.class);
	
	@Autowired
	WikipediaServiceImpl wikipediaSearch;
	
	@Test
	public void testSearch() throws WikipediaSearchException {
		List<WikiSearchResult> results = wikipediaSearch.searchImpl("Michael Jackson");
		for (WikiSearchResult result : results) {
			logger.info(result.getTitle());
			assertNotNull(result.getTitle());
			assertNotNull(result.getSnippet());
		}
	}

	@Test
	public void testgetPage() throws WikipediaSearchException {
		Page result = wikipediaSearch.getPage("Michael Jackson");
		logger.info(result.getTitle());
		assertNotNull(result.getTitle());
	}

}
