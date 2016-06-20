package com.walmart.app;

import java.util.List;
import java.util.ArrayList;
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


    void getRecommendedProducts(String product) {
        logger.info("In getRecommendedProducts");
        long itemId = this.getFirstItemId(product);

        logger.info(itemId);

        JSONArray jsonArray = this.getRecommendedProducts(itemId);
        
        logger.info(jsonArray.size());

        int size = 10<jsonArray.size()?10:jsonArray.size();

        // logger.info("Recommended product list size: " + recommendedProducts.size());

        // List<RecommendedProduct> recommendedProducts = Utils.getProductListFromJSONArray(recommendedProducts, 10);
        // logger.info(selectedRecommendedProducts);


        ExecutorService requestTaskExecutor = Executors.newFixedThreadPool(5);
        CompletionService<RecommendedProduct> requestTaskCompletionService = 
            new ExecutorCompletionService<RecommendedProduct>(requestTaskExecutor);

        List<RecommendedProduct> recommendedProducts = new ArrayList<RecommendedProduct>();
        int taskCounter = 0;
        for (taskCounter=0; taskCounter<size; taskCounter++) {
            RecommendedProduct rp = new RecommendedProduct();
            JSONObject jsonObject = (JSONObject) jsonArray.get(taskCounter);
            rp.itemId = (Long)jsonObject.get("itemId");
            rp.itemName = (String)jsonObject.get("name");
            requestTaskCompletionService.submit(new RequestTask(rp));
        }

        for(int completedTask=0; completedTask<taskCounter; completedTask++) {
            try {
                Future<RecommendedProduct> requestResult = requestTaskCompletionService.take();
                RecommendedProduct rp = requestResult.get();
                recommendedProducts.add(rp);
            }
            catch(Exception e) {
                logger.error("Error in task completion: ",e);
            }
        }
        logger.info(recommendedProducts);

        // sort recommended products

        // return recommendedProducts;
    }


    long getFirstItemId(String product) {
        logger.info("In getItemId");
        logger.info("Searching the product");
        String url = URLUtils.getAPIURL("search", product);
        logger.info("Making serach request");
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