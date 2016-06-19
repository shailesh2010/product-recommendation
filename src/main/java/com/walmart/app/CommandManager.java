package com.walmart.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CommandManager {
	
	// Method to print the main menu	
	void printMenu(){
		System.out.println("\n\nWalmart Product Recommendation");
		System.out.println("\nMenu\n");
		System.out.println("1. Query about the product");
		System.out.println("2. Quit");
		System.out.print("Please select an option(enter a number) : ");
	}
	
	
	// Method to read command from command line	
	String getUserInput(){
		String input = null;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			input = br.readLine();
		}
		catch(Exception e){
			System.out.println("Something went wrong with the system input!!   Please try again.");
		}
		return input;
	}

	public int parseCommand(String strCommand) {
		int command = -1;
		if (strCommand == null || strCommand.length() == 0) {
			return command;
		}
		try {
			command = Integer.parseInt(strCommand);
		}
		catch (NumberFormatException nfe) {
			command = 0;
		}
		return command;
	}

	void startSystem() {
		RecommendationEngine re = new RecommendationEngine();
		while(true) {
			this.printMenu();
			String userInput = this.getUserInput();
			int command = this.parseCommand(userInput);
			if (command == 2) {
				System.exit(0);
			}
			else if(command == 1) {
				System.out.println("Please input the product to search : ");
				String userQuery = this.getUserInput();
				re.getRecommendedProducts(userQuery);
			}
			else {
				System.out.println("\n\tInvalid Option! Please try again.\n");
			}
		}
	}

}
