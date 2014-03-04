package com.decisionem.aws.rest;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.amazon.advertising.api.sample.SignedRequestsHelper;
import com.decisionem.ext.awse.jax.gen.Item;
import com.decisionem.ext.awse.jax.gen.ItemLookupResponse;
import com.decisionem.ext.awse.jax.gen.ItemSearchResponse;
import com.decisionem.search.client.SearchResultImpl;
import com.decisionem.search.client.api.SearchException;
import com.decisionem.search.client.api.SearchResult;
import com.decisionem.search.client.api.SearchService;
import com.decisionem.util.XmlUtil;

public class AwsServiceImpl implements SearchService {

	private static final Logger logger = Logger.getLogger(AwsServiceImpl.class);

	private String associateTag;

	private SignedRequestsHelper requestHelper;
	
	private Unmarshaller unmarshaller;

	public AwsServiceImpl(String endpoint, String accessKeyId,
			String secretKey, String associateTag) throws AwsServiceException {
		logger.info("init AwsServiceImpl");

		init(endpoint, accessKeyId, secretKey, associateTag);
	}

	public AwsServiceImpl(Properties properties) throws AwsServiceException {
		logger.info("init AwsServiceImpl");
		String endpoint = properties.getProperty("aws.endpoint");
		String accessKeyId = properties.getProperty("aws.accessKeyId");
		String secretKey = properties.getProperty("aws.secretKey");
		this.associateTag = properties.getProperty("aws.associateTag");

		init(endpoint, accessKeyId, secretKey, associateTag);
	}
	
	private void init(String endpoint, String accessKeyId,
			String secretKey, String associateTag) throws AwsServiceException {
		this.associateTag = associateTag;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("com.decisionem.ext.awse.jax.gen");
	        this.unmarshaller = jaxbContext.createUnmarshaller();
	        
			requestHelper = SignedRequestsHelper.getInstance(endpoint, accessKeyId, secretKey);
		} catch (InvalidKeyException | IllegalArgumentException | UnsupportedEncodingException | NoSuchAlgorithmException | JAXBException e) {
			throw new AwsServiceException(e);
		}
	}

	public List<Item> search(AwsSearchParams search) throws AwsServiceException {
		Map<String, String> params = createParams();
		params.put("Operation", "ItemSearch");
		switch (search.getType()) {
		case SIMPLE:
			params.put("Keywords", search.getText());
			params.put("SearchIndex", "All");
		}
		String url = requestHelper.sign(params);

        ItemSearchResponse response = (ItemSearchResponse)unmarshall(url);
        
		// TODO handle empty result
		return response.getItems().get(0).getItem();
	}

	public Item getByItemId(String id) throws AwsServiceException {
		Map<String, String> params = createParams();
		params.put("Operation", "ItemLookup");
		params.put("ItemId", id);

		String url = requestHelper.sign(params);
		
        ItemLookupResponse itemLookupResponse = (ItemLookupResponse)unmarshall(url);

		// TODO handle empty result
		return itemLookupResponse.getItems().get(0).getItem().get(0);
	}
	
	private Object unmarshall(String url) throws AwsServiceException {
		try {
			return unmarshaller.unmarshal(new URL(url));
		} catch (MalformedURLException | JAXBException e) {
			throw new AwsServiceException(e);
		}
		
	}

	private Map<String, String> createParams() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("AssociateTag", this.associateTag);
		return params;
	}

	@SuppressWarnings("unused")
	private void logResponseXml(String url) {
		try {
			logger.info(XmlUtil.printXml(url));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public List<SearchResult> search(String search) throws SearchException {
		List<SearchResult> list = new ArrayList<SearchResult>();
		try {
			List<Item> items = this.search(AwsSearchParams.createSimpleSearch(search));
			for (Item item : items) {
				SearchResultImpl result = new SearchResultImpl();
				result.setTitle(item.getItemAttributes().getTitle());
				result.setUrl(item.getDetailPageURL());
				list.add(result);
			}
		} catch (AwsServiceException e) {
			throw new SearchException(e);
		}
		return list;
	}


}
