package com.walmart.utils;

import java.util.concurrent.Callable;
import java.util.List;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.apache.log4j.Logger;

import com.walmart.models.RecommendedProduct;
import com.walmart.utils.URLUtils;
import com.walmart.utils.Utils;

public class RequestTask implements Callable<RecommendedProduct>{
    final static Logger logger = Logger.getLogger(RequestTask.class);


    RecommendedProduct rp;

    public RequestTask(RecommendedProduct rp) {
        this.rp = rp;
    }


    public RecommendedProduct call() throws Exception{
        
        logger.info("Getting data for "+this.rp.getItemId());
        
        String url = URLUtils.getAPIURL("reviews", ""+this.rp.getItemId());
        JSONObject jsonObject = URLUtils.getJSONResponse(url);
        
        if(jsonObject==null)
            logger.info(jsonObject);
        
        JSONArray jsonArray = null;
        List<String> reviewList = null;
        
        if(jsonObject.get("reviews")!=null) {
            jsonArray = (JSONArray) jsonObject.get("reviews");
            reviewList = Utils.getReviewListFromJSONArray(jsonArray);
        }
        else {
            reviewList = new ArrayList<String>();
        }
        this.rp.setReviewList(reviewList);
        
        JSONObject reviewStatistics = null;
        
        if(jsonObject.get("reviewStatistics")!=null) {
            reviewStatistics = (JSONObject)jsonObject.get("reviewStatistics");
            float averageOverallRating = Float.parseFloat((String)reviewStatistics.get("averageOverallRating"));
            float overallRatingRange = Float.parseFloat((String)reviewStatistics.get("overallRatingRange"));
            int totalReviewCount = Integer.parseInt((String)reviewStatistics.get("totalReviewCount"));
            this.rp.setTotalReviewCount(totalReviewCount);
            this.rp.setAverageOverallRating(averageOverallRating);
            this.rp.setOverallRatingRange(overallRatingRange);
        }
        else {
            this.rp.setTotalReviewCount(0);
            this.rp.setAverageOverallRating(0);
            this.rp.setOverallRatingRange(0);
        }
        
        return this.rp;
    }
}