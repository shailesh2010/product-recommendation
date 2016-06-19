package com.walmart.app;

import com.walmart.utils.PropertyReader;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;


class App {

	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		BasicConfigurator.configure();
		PropertyReader.initializeProperties();
		System.out.println("Walmart Product Recommendation");
		CommandManager commandManager = new CommandManager();
		commandManager.startSystem();
	}
}
