package com.walmart.utils;


import java.util.List;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.walmart.models.RecommendedProduct;

public class Utils {

    public static List<RecommendedProduct> getProductListFromJSONArray(JSONArray jsonArray, int size) {
        size = size<jsonArray.size()?size:jsonArray.size();
        List<RecommendedProduct> productList = new ArrayList<RecommendedProduct>();
        for (int cnt=0; cnt<size; cnt++) {
            RecommendedProduct rp = new RecommendedProduct();
            JSONObject jsonObject = (JSONObject) jsonArray.get(cnt);
            rp.itemId = (Long)jsonObject.get("itemId");
            rp.itemName = (String)jsonObject.get("name");

            productList.add(rp);
        }
        return productList;
    }
}