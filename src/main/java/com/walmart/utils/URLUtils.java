package com.walmart.utils;

import java.net.URL;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;



public class URLUtils {

    final static Logger logger = Logger.getLogger(URLUtils.class);

    // method to get api url
    public static String getAPIURL(String resource, String searchParameter) {
        StringBuilder url = new StringBuilder(PropertyReader.API_URL);
        if (resource.equals("search")) {
            url.append(PropertyReader.SEARCH_ENDPOINT);
            url.append("?apiKey=");
            url.append(PropertyReader.API_KEY);
            url.append("&format=json&numItems=1&query=");
            url.append(searchParameter);
        }
        else if (resource.equals("nbp")) {
            url.append(PropertyReader.RECOMMENDATION_ENDPOINT);
            url.append("?apiKey=");
            url.append(PropertyReader.API_KEY);
            url.append("&format=json&itemId=");
            url.append(searchParameter);
        }
        else if (resource.equals("reviews")) {
            url.append(PropertyReader.REVIEW_ENDPOINT);
            url.append("/");
            url.append(searchParameter);
            url.append("?apiKey=");
            url.append(PropertyReader.API_KEY);
            url.append("&format=json");
        }
        return url.toString();
    }

    // method to request the url and get json response
    public static JSONObject getJSONResponse(String url) {
        JSONObject jsonObject = new JSONObject();
        try {
            URL  urlObject= new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                
                String outputLine = new String();
                StringBuilder response = new StringBuilder();

                while ((outputLine = in.readLine()) != null) {
                    response.append(outputLine);
                }
                in.close();
                // Object json = new JSONTokener(response.toString()).nextValue();
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(response.toString());
                if (obj instanceof JSONObject) {
                    jsonObject = (JSONObject) parser.parse(response.toString());
                }
                else if (obj instanceof JSONArray){
                    jsonObject = new JSONObject();
                    jsonObject.put("items", (JSONArray)parser.parse(response.toString()));
                }
            }
        }
        catch(Exception e) {
            logger.error("Error in url utils: ", e);            
        }
        return jsonObject;
    }
}
