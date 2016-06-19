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
	public static String URL;
	public static String API_KEY;
	public static boolean DEBUG;

	final static Logger logger = Logger.getLogger(PropertyReader.class);

	public static void initializeProperties() {
		Properties properties = new Properties();
		InputStream propertiesFile = null;
		try {
			propertiesFile = new FileInputStream("config.properties");
			properties.load(propertiesFile);
			URL =  properties.getProperty("URL");
			API_KEY =  properties.getProperty("API_KEY");
			DEBUG =  Boolean.parseBoolean(properties.getProperty("DEBUG"));
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
