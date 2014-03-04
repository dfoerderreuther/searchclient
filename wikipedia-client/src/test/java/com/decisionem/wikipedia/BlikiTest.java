package com.decisionem.wikipedia;

import info.bliki.api.Page;
import info.bliki.api.User;
import info.bliki.wiki.model.WikiModel;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

public class BlikiTest {

	private static final Logger logger = Logger.getLogger(BlikiTest.class);

	@Test
	public void testTest() {
		String[] listOfTitleStrings = { "Michael Jackson" };
		User user = new User("", "", "http://en.wikipedia.org/w/api.php");
		user.login();
		List<Page> listOfPages = user.queryContent(listOfTitleStrings);
		for (Page page : listOfPages) {
		  logger.info(page.getTitle());
		  logger.info(page.getImageThumbUrl());
		  WikiModel wikiModel = new WikiModel(page.getTitle(), "${title}");
		  String html = wikiModel.render(page.toString());
		  System.out.println(html);
		}
	}
	
}
