package com.walmart.app;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;


import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import com.walmart.utils.URLUtils;
import com.walmart.utils.Utils;
import com.walmart.utils.RequestTask;

import com.walmart.models.RecommendedProduct;

class RecommendationEngine {

    final static Logger logger = Logger.getLogger(RecommendationEngine.class);
    final int concurrentAPIRequest = 5;
    final int noOfProductRecommendation = 10;

    List<RecommendedProduct> getRecommendedProducts(String product) {
        
        List<RecommendedProduct> recommendedProducts = new ArrayList<RecommendedProduct>();
        logger.info("In getRecommendedProducts");

        Long itemId = this.getFirstItemId(product);
        if(itemId!=null) {
            logger.info(itemId);
            JSONArray jsonArray = this.getRecommendedProducts(itemId);
            if(jsonArray==null)
                jsonArray = new JSONArray();
            int size = noOfProductRecommendation<jsonArray.size()?noOfProductRecommendation:jsonArray.size();

            ExecutorService requestTaskExecutor = Executors.newFixedThreadPool(concurrentAPIRequest);
            CompletionService<RecommendedProduct> requestTaskCompletionService = 
                new ExecutorCompletionService<RecommendedProduct>(requestTaskExecutor);

            int taskCounter = 0;
            for (taskCounter=0; taskCounter<size; taskCounter++) {
                RecommendedProduct rp = new RecommendedProduct();
                JSONObject jsonObject = (JSONObject) jsonArray.get(taskCounter);
                rp.setItemId((Long)jsonObject.get("itemId"));
                rp.setItemName((String)jsonObject.get("name"));
                requestTaskCompletionService.submit(new RequestTask(rp));
            }

            for(int completedTask=0; completedTask<taskCounter; completedTask++) {
                try {
                    Future<RecommendedProduct> requestResult = requestTaskCompletionService.take();
                    RecommendedProduct rp = requestResult.get();
                    recommendedProducts.add(rp);
                }
                catch(Exception e) {
                    logger.error("cause:"+e.getCause());
                    logger.error("Error in task completion: ",e);
                }
            }
            logger.info("Before sorting:");
            logger.info(recommendedProducts);
            logger.info("After sorting:");
            Collections.sort(recommendedProducts);
            logger.info(recommendedProducts);
        }
        return recommendedProducts;
    }


    Long getFirstItemId(String product) {
        logger.info("In getItemId");
        logger.info("Searching the product");
        String url = URLUtils.getAPIURL("search", product);
        logger.info("Making serach request");
        JSONObject jsonObject = URLUtils.getJSONResponse(url);
        JSONArray items = null;
        Long itemId = null;
        if (jsonObject.get("items")!=null) {
            items = (JSONArray)jsonObject.get("items");
            JSONObject item = (JSONObject)items.get(0);
            itemId = (Long)item.get("itemId");
        }
        return itemId;
    }


    JSONArray getRecommendedProducts(long itemId) {
        logger.info("In getRecommendedProducts");
        logger.info("Searching for the recommended products");
        String url = URLUtils.getAPIURL("nbp", ""+itemId);
        logger.info("Making recommendation request");
        JSONObject jsonObject = URLUtils.getJSONResponse(url);
        JSONArray items = null;
        if (jsonObject.get("items")!=null) {
            items = (JSONArray)jsonObject.get("items");
        }
        return items;
    }
}
