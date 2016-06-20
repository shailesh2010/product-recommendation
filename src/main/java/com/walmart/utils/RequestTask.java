package com.walmart.utils;

import java.util.concurrent.Callable;

import org.json.simple.JSONObject;
import org.apache.log4j.Logger;

import com.walmart.models.RecommendedProduct;
import com.walmart.utils.URLUtils;

public class RequestTask implements Callable<RecommendedProduct>{
    final static Logger logger = Logger.getLogger(RequestTask.class);


    RecommendedProduct rp;

    public RequestTask(RecommendedProduct rp) {
        this.rp = rp;
    }


    public RecommendedProduct call() throws Exception{
        String url = URLUtils.getAPIURL("reviews", ""+this.rp.itemId);
        logger.info("Getting data for "+this.rp.itemId);
        JSONObject jsonObject = URLUtils.getJSONResponse(url);
        return this.rp;
    }
}