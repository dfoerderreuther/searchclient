package com.decisionem.search.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.decisionem.search.client.api.SearchException;
import com.decisionem.search.client.api.SearchResult;
import com.decisionem.search.client.api.SearchService;

@ContextConfiguration("/com/decisionem/aws/app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SearchServiceTest {

	private static final Logger logger = Logger.getLogger(SearchServiceTest.class);
	
	@Autowired
	SearchService awsService;
	
	@Test
	public void testSearch() throws SearchException {
		List<SearchResult> results = awsService.search("Casio");

		for (SearchResult result : results) {
			assertNotNull(result.getTitle());
			assertNotNull(result.getUrl());
			logger.info("Amazon: " + result.getTitle() + " / " + result.getUrl());
		}
	}

}
