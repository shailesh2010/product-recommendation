package com.walmart.app;

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import com.walmart.utils.URLUtils;

class RecommendationEngine {

	final static Logger logger = Logger.getLogger(RecommendationEngine.class);


	void getRecommendedProducts(String product) {
		logger.info("In getRecommendedProducts");
		long itemId = this.getFirstItemId(product);

		logger.info(itemId);

		JSONArray recommendedProducts = this.getRecommendedProducts(itemId);
		logger.info(recommendedProducts.size());
		// sort recommended products

		// return recommendedProducts;
	}

	long getFirstItemId(String product) {
		logger.info("In getItemId");
		logger.info("Searching the product");
		String url = URLUtils.getAPIURL("search", product);
		logger.info("making serach request");
		JSONObject jsonObject = URLUtils.getJSONResponse(url);
		JSONArray items = (JSONArray)jsonObject.get("items");
		JSONObject item = (JSONObject)items.get(0);
		Long itemId = (Long)item.get("itemId");
		return itemId;
	}


	JSONArray getRecommendedProducts(long itemId) {
		logger.info("In getRecommendedProducts");
		logger.info("Searching for the recommended products");
		String url = URLUtils.getAPIURL("nbp", ""+itemId);
		logger.info("Making recommendation request");
		JSONObject jsonObject = URLUtils.getJSONResponse(url);
		JSONArray items = (JSONArray)jsonObject.get("items");
		
		return items;

	}



}