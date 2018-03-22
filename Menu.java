
/*
 * ====== Menu.java
 * This class is a built class to allow the reproduction of menus. This 
 * menu was adapted from my assignment back in programming fundamentals, 
 * were a menu was also needed. Using this class menus, and submenus for 
 * this application can be made without repetition. This class also contains 
 * a number of static methods that validates user input. These static methods 
 * were used throughout the console-based program. 
 * 
 * */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	
	// Menu class instance  variables
	private String options[];
	private String menuTitle;
	private static Scanner sc = new Scanner(System.in);

	// constructor for the Menu class
	public Menu(String menuTitle, String options[]) {
		this.menuTitle = menuTitle;
		this.options = options;
	}
	
	// getter - return the set of options for a certain manu object
	public String[] getOptions() { return this.options; }

	// method to display the menu and its options
	public void displayMenu() {
		System.out.println("\n\t ========= " + this.menuTitle + " ====== ");
		if (options.length < 6) {
			for (int i = 0; i < options.length; i++)
				System.out.println("\t\t " + (i) + "\t" + options[i]);
		} else {
			for (int i = 0; i < options.length/2; i++)
				System.out.printf("\t\t %-2d   %-15s %-2d   %-10s \n", (i), options[i], (i+5), options[i+5]);
		}
	}

	// method to obtain input from keyboard based on the number of options
	// for a certain menu object
	public int getValidOption() {
		int choice = -1; 
		
		do {
			try {
				System.out.print("\t === Enter choice : ");
				choice = sc.nextInt();
				
				if (choice < 0 || choice > (options.length-1))
					System.out.print("\t *** Invalid input. Enter again.\n");
				
			} catch (InputMismatchException ex) {
				System.out.print("\t *** Invalid input. Enter again.\n");
				sc.nextLine();
			}
		} while (choice < 0 || choice > (options.length-1));
		return choice;
	}
	
	// static method - this method is used throughout the Driver class
	// to be specific, in situations were a binary input from the keyboard is needed
	public static int YesOrNoOption(String message) {
		int choice = -1;
		
		do {
			try {
				System.out.print("\n\t === " + message + " (1-Yes / 0-No) Enter choice : ");
				choice = sc.nextInt();
				
				if (!(choice == 0 || choice == 1))
					System.out.print("\t *** Invalid input. Enter again.\n");
				
			} catch (InputMismatchException ex) {
				System.out.print("\t *** Invalid input. Enter again.\n");
				sc.nextLine();
			}
		} while ( !(choice == 0 || choice == 1) );
		return choice;
	}

	// static method - this method is also used throughout the Driver class
	// to be specific, in situations an integer value input is needed
	// is method was made to ensure that input mismatches are caught, 
	// and the social network will still run without any complaints or errors
	public static int getIntInput(String message) {
		int num = 0; 
		boolean done = false;
		
		do {
			try {
				System.out.print("\t === " + message + " : ");
				num = sc.nextInt();
				done = true;
			} catch (InputMismatchException ex) {
				System.out.print("\t *** Invalid input. Enter again.\n");
				sc.nextLine();
			}
		} while ( !done );
		return num;
	}

}