package com.decisionem.aws.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.decisionem.aws.rest.AwsSearchParams;
import com.decisionem.aws.rest.AwsServiceException;
import com.decisionem.aws.rest.AwsServiceImpl;
import com.decisionem.ext.awse.jax.gen.Item;

@ContextConfiguration("/com/decisionem/aws/app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AwsServiceTest {

	private static final Logger logger = Logger.getLogger(AwsServiceTest.class);
	
	@Autowired
	AwsServiceImpl awsService;
	
	@Test
	public void testSearch() throws AwsServiceException {
		List<Item> items = awsService.search(AwsSearchParams.createSimpleSearch("Casio"));
		for (Item item : items) {
			logger.info(item.getASIN() + " " + item.getItemAttributes().getTitle());
			assertNotNull(item.getASIN());
		}
	}
	
	@Test
	public void testLookup() throws AwsServiceException {
		Item item = awsService.getByItemId("B000J34HN4");
		logger.info(item.getASIN() + " " + item.getItemAttributes().getTitle());
		assertNotNull(item.getASIN());
	}

}
