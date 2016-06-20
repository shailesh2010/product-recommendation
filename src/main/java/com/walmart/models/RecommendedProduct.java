package com.walmart.models;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


public class RecommendedProduct {
	public long itemId;
	public String itemName;
	public JSONArray reviews;
	public JSONObject reviewStatistics;


	public String toString(){
    return("Number = "+this.itemId+" and Name = "+this.itemName);
}
}