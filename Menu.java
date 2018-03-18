import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	
	private String options[];
	private String menuTitle;
	private static Scanner sc = new Scanner(System.in);

	public Menu(String menuTitle, String options[]) {
		this.menuTitle = menuTitle;
		this.options = options;
	}
	
	public String[] getOptions() { return this.options; }

	public void displayMenu() {
		// System.out.println("\n\t ========= Menu ========================== ");
		System.out.println("\n\t ========= " + this.menuTitle + " ====== ");
		for (int i = 0; i < options.length; i++)
			System.out.println("\t\t " + (i) + "\t" + options[i]);
		// System.out.println("\t\t 0 \tExit");
		// System.out.println("\t\t ======================================== ");
	}

	public int getValidOption() {
		int choice = -1; 
		do {
			try {
				System.out.print("\t === Enter choice : ");
				choice = sc.nextInt();
				//choice = Integer.parseInt(sc.nextLine());
			} catch (InputMismatchException ex) {
				System.out.print("\t *** Invalid input. Enter again.\n");
				sc.nextLine();
			}
		} while (choice < 0 || choice > (options.length-1));
		return choice;
	}
	
	public static int YesOrNoOption(String message) {
		int choice = -1;
		do {
			try {
				System.out.print("\n\t === " + message + " (1-Yes / 0-No) Enter choice : ");
				choice = sc.nextInt();
			} catch (InputMismatchException ex) {
				System.out.print("\t *** Invalid input. Enter again.\n");
				sc.nextLine();
			}
		} while ( !(choice == 0 || choice == 1) );
		return choice;
	}

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