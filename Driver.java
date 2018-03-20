
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

	private Scanner sc = new Scanner(System.in);
	private UserProfiles userDatabase = new UserProfiles();
	private ArrayList<Relationship> relationships = new ArrayList<Relationship>();

	public Driver() {
		userDatabase.addUser(new Adult("Alice"));
		userDatabase.addUser(new Adult("Bob"));
		userDatabase.addUser(new Adult("Cathy"));
		userDatabase.addUser(new Adult("Don"));
		userDatabase.addUser(new Adult("John"));
		userDatabase.addUser(new Adult("Jane"));

		userDatabase.addUser(new Child("Catherine", 14, ((Adult) userDatabase.getProfile("Bob")),
				((Adult) userDatabase.getProfile("Alice"))));
		userDatabase.addUser(new Child("Mich", 15, ((Adult) userDatabase.getProfile("Don")),
				((Adult) userDatabase.getProfile("Cathy"))));

		if (userDatabase.getProfile("Alice").addFriend(userDatabase.getProfile("Bob"))) {
			relationships.add(new Relationship("Alice", "Bob"));
			relationships.add(new Relationship("Bob", "Alice"));
		}

		if (userDatabase.getProfile("Alice").addFriend(userDatabase.getProfile("Don"))) {
			relationships.add(new Relationship("Alice", "Don"));
			relationships.add(new Relationship("Don", "Alice"));
		}
	}

	public void displayAllUsers() {
		String type;
		
		System.out.print("\n\t *** Returning all usernames currently stored");
		System.out.println("\n\t ========= Profiles " + "====== ");
		
		for (int i = 0; i < userDatabase.getAllProfiles().size(); i++) {
			if (userDatabase.getAllProfiles().get(i) instanceof Adult)
				type = "( A )";
			else type = "( C )";

			if ((i + 1) % 3 == 1)
				System.out.printf("\t %3d : %s %-10s", (i + 1), type, userDatabase.getAllProfiles().get(i).getUsername());
			else if ((i + 1) % 3 == 2)
				System.out.printf("\t %3d : %s %-10s", (i + 1), type, userDatabase.getAllProfiles().get(i).getUsername());
			else if ((i + 1) % 3 == 0)
				System.out.printf("\t %3d : %s %-10s\n", (i + 1), type, userDatabase.getAllProfiles().get(i).getUsername());
		}
		
		System.out.print("\n\n\t *** User no. : ( User type ) Username");
		System.out.print("\n\t *** ( A ) Adult Profile");
		System.out.print("\n\t *** ( C ) Child Profile");
		System.out.println();
	}

	public void searchProfile() {
		System.out.print("\n\t ========= Search Profile " + "====== ");
		System.out.print("\n\t === Enter username : ");
		String searchName = sc.nextLine();
		searchName = searchName.trim();
		
		if (userDatabase.existingUser(searchName)) { ////// think about trim
			System.out.print("\n\t *** User Profile '" + searchName + "' found");
			userDatabase.getProfile(searchName).printProfile();
			
			System.out.println();
			if ( Menu.YesOrNoOption("Edit Profile?") == 1 ) {
				editProfile(userDatabase.getProfile(searchName));
			}
				
		} else
			System.out.print("\n\t *** User Profile with username '" + searchName + "' does not exist");
	}
	
	public String[] getEditProfileOptions(User userProfile) {
		String[] options =  { "Show Profile", "Edit Status", "Edit Image", "Edit Age", "Add a network", 
				"Delete a network", "Find a friend", "Back to main menu"};
		
		if ( userProfile.getStatus().isEmpty() )
			options[1] = "Add Status";
		
		if ( userProfile.getImage().isEmpty() )
			options[2] = "Add Image";
		
		if ( userProfile.getAge() == 0 )
			options[3] = "Add Age";
		
		return options;
	}
	
	public void editProfile(User userProfile) {
		int choice;
		
		do {
			Menu editSubmenu = new Menu("Edit " + userProfile.getUsername() + "'s Profile", getEditProfileOptions(userProfile));
			editSubmenu.displayMenu();
			choice = editSubmenu.getValidOption();
			
			switch (choice) {
				case 0: {
					System.out.println();
					userProfile.printProfile();  
					break;
				}
				case 1: {
					System.out.print("\t === Enter Status : ");
					userProfile.setStatus(sc.nextLine());
					break; 
				}
				case 2: {
					System.out.print("\t === Enter Image : ");
					userProfile.setImage(sc.nextLine());
					break; 
				}
				case 3: 
					userProfile.setAge(Menu.getIntInput("Enter Age")); 
					break;
				case 4: 
					ConnectUsers(userProfile.getUsername()); 
					break;
				case 5: 
					DisconnectUsers(userProfile.getUsername()); 
					break;
				case 6: 
					System.out.print("Find friends"); 
					break;
				case 7: break;
			}
		} while (choice != 7); // because there are only 5 choices
	}
	
	public void addProfile() {
		int choice;
		Menu addUserSubmenu = new Menu("New User Type", new String[] { "Adult", "Child", "Back to main menu" });

		addUserSubmenu.displayMenu();
		choice = addUserSubmenu.getValidOption();
		
		switch(choice) {
			case 0 : addAdultProfile(); break;
			case 1 : addChildProfile(); break; 
			case 2 : break;
		}
	}
	
	public void addAdultProfile() {
		System.out.print("\n\t ========= Create Adult Profile " + "====== ");
		System.out.print("\n\t === Enter username : ");
		String newUsername = sc.nextLine();
		newUsername = newUsername.trim();

		if (!userDatabase.existingUser(newUsername)) {
			userDatabase.addUser(new Adult(newUsername));
			System.out.print("\n\t *** User Profile with username '" + newUsername + "' successfully created.");
			editProfile(userDatabase.getProfile(newUsername));
		} else
			System.out.print("\n\t *** User Profile with username '" + newUsername + "' already exists.");
	}
	
	public void addChildProfile() {
		System.out.print("\n\t ========= Create Child Profile " + "====== ");
		System.out.print("\n\t === Enter username : ");
		String[] parentName = new String[2];
		String newUsername = sc.nextLine();
		newUsername = newUsername.trim();

		if (!userDatabase.existingUser(newUsername)) {
			for (int i=0; i<2; i++) {
				do {
					System.out.print("\n\t === Enter parent username " + (i+1)  + " : ");
					parentName[i] = sc.nextLine();
				} while ( userDatabase.existingUser(parentName[i]) );
			}
			
			int age = Menu.getIntInput("\nEnter age");
			
			userDatabase.addUser(new Child(newUsername, age, 
					((Adult) userDatabase.getProfile(parentName[0])), ((Adult) userDatabase.getProfile(parentName[1]))));
			System.out.print("\n\t *** User Profile with username '" + newUsername + "' successfully created.");
			editProfile(userDatabase.getProfile(newUsername));
		} else
			System.out.print("\n\t *** User Profile with username '" + newUsername + "' already exists.");
	}

	public void DeleteProfile() {
		System.out.print("\n\t ========= Delete Profile " + "====== ");
		System.out.print("\n\t === Enter username : ");
		String newUsername = sc.nextLine();
		if (userDatabase.existingUser(newUsername.trim())) {
			userDatabase.deleteUser(newUsername.trim());
			System.out.print("\n\t *** User Profile '" + newUsername.trim() + "' successfully deleted.");
		} else
			System.out.print("\n\t *** User Profile with username '" + newUsername.trim() + "' does not exist.");
	}

	public void ConnectUsers(String username) {
		System.out.print("\t === Enter friend username to add : ");
		String friendName = sc.nextLine();
		friendName = friendName.trim();
		
		if ( userDatabase.existingUser(friendName.trim()) ) {
			userDatabase.getProfile(username).addFriend(userDatabase.getProfile(friendName));
			relationships.add(new Relationship(username, friendName));
			relationships.add(new Relationship(username, friendName));
		} else System.out.print("\n\t *** User Profile with username '" + friendName + "' does not exist");
	}
	
	public void DisconnectUsers(String username) {
		System.out.print("\t === Enter friend username to delete: ");
		String friendName = sc.nextLine();
		friendName = friendName.trim();
		
		if ( userDatabase.existingUser(friendName) ) {
			userDatabase.getProfile(username).deleteFriend(userDatabase.getProfile(friendName));
			for (int i=0; i<relationships.size(); i++) {
				if (relationships.get(i).getUsernameOne() == username && relationships.get(i).getUsernameTwo() == friendName)
					relationships.remove(i);
				if (relationships.get(i).getUsernameOne() == friendName && relationships.get(i).getUsernameTwo() == username)
					relationships.remove(i);
				}
		} else System.out.print("\n\t *** User Profile with username '" + friendName + "' does not exist");
	}

	public static void main(String[] args) {

		Driver network = new Driver();
		String mainMenu[] = { "Show all users", "Search user", "Add new user", 
				"Delete a user", "Connect users", "Exit" };
		Menu menu = new Menu("Main Menu", mainMenu);
		int choice;
		
		do {
			menu.displayMenu();
			choice = menu.getValidOption();
			switch (choice) {
				case 0: network.displayAllUsers(); break;
				case 1: network.searchProfile(); break;
				case 2: network.addProfile(); break;
				case 3: network.DeleteProfile(); break;
				case 4: System.out.println("\n\t *** See you. You exited the application\n"); break;
			}
		} while (choice != (mainMenu.length-1));
	}

}
