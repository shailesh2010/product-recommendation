package com.walmart.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class PropertyReader {
	public static String API_URL;
	public static String SEARCH_ENDPOINT;
	public static String RECOMMENDATION_ENDPOINT;
	public static String REVIEW_ENDPOINT;
	public static String API_KEY;
	public static boolean DEBUG;

	final static Logger logger = Logger.getLogger(PropertyReader.class);

	public static void initializeProperties() {
		Properties properties = new Properties();
		InputStream propertiesFile = null;
		try {
			propertiesFile = new FileInputStream("config.properties");
			properties.load(propertiesFile);
			DEBUG =  Boolean.parseBoolean(properties.getProperty("DEBUG"));
			API_URL =  properties.getProperty("URL");
			SEARCH_ENDPOINT =  properties.getProperty("SEARCH_ENDPOINT");
			RECOMMENDATION_ENDPOINT =  properties.getProperty("RECOMMENDATION_ENDPOINT");
			REVIEW_ENDPOINT =  properties.getProperty("REVIEW_ENDPOINT");
			API_KEY =  properties.getProperty("API_KEY");
			if (DEBUG) {
				LogManager.getRootLogger().setLevel(Level.DEBUG);
			}
			else {
				LogManager.getRootLogger().setLevel(Level.ERROR);
			}
			logger.info("Properties initialized.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (propertiesFile != null) {
				try {
					propertiesFile.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
}
