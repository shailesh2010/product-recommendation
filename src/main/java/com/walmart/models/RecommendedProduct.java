package com.models;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


class RecommendedProduct {
	long itemId;
	String itemName;
	JSONArray reviews;
	JSONObject reviewStatistics;
}