package com.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONobject;


class RecommendedProduct {
	long itemId;
	String itemName;
	JSONArray reviews;
	JSONObject reviewStatistics;
}