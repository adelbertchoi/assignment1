
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

	private Scanner sc = new Scanner(System.in);
	private Profiles userDatabase = new Profiles();
	private Connections connections = new Connections();
	private ArrayList<Relationship> relationshipFlow;

	public Driver() {
		userDatabase.addUser(new Adult("Alice"));
		userDatabase.addUser(new Adult("Bob"));
		userDatabase.addUser(new Adult("Cathy"));
		userDatabase.addUser(new Adult("Don"));
		userDatabase.addUser(new Adult("John"));
		userDatabase.addUser(new Adult("Jane"));
		
		userDatabase.addUser(new Child("Catherine", 14, ((Adult) userDatabase.getProfile("Bob")),
				((Adult) userDatabase.getProfile("Alice"))));
		connections.addRelationship("Catherine", "Bob", "Pa");
		connections.addRelationship("Catherine", "Alice", "Pa");
		connections.addRelationship("Alice", "Bob", "Pr");
		
		userDatabase.addUser(new Child("Mich", 15, ((Adult) userDatabase.getProfile("Don")),
				((Adult) userDatabase.getProfile("Cathy"))));
		connections.addRelationship("Mich", "Don", "Pa");
		connections.addRelationship("Mich", "Cathy", "Pa");
		connections.addRelationship("Cathy", "Don", "Pr");

		if (userDatabase.getProfile("Alice").addFriend(userDatabase.getProfile("Bob")))
			connections.addRelationship("Alice", "Bob");

		if (userDatabase.getProfile("Alice").addFriend(userDatabase.getProfile("Don")))
			connections.addRelationship("Alice", "Don");
		
//		for(int i=0; i<connections.getRelationships().size(); i++)
//			System.out.println(connections.getRelationships().get(i).printRelationship());

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
		
		if ( userDatabase.existingUser(searchName) ) { 
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
				"Delete a network", "Find a friend form network", "Back to main menu"};
		
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
					System.out.print("\n\t *** Showing " + userProfile.getUsername() + "'s user profile.");
					userProfile.printProfile(); 
					System.out.println();  
					break;
				}
				case 1: {
					System.out.print("\t === Enter Status : ");
					userProfile.setStatus(sc.nextLine());
					break; 
				}
				case 2: {
					System.out.print("\t === Enter Image : ");
					String newImage =  sc.nextLine();
					if (newImage.toLowerCase().contains(".png") || newImage.toLowerCase().contains(".jpeg"))
						userProfile.setImage(newImage );
					else 
						System.out.print("\n\t *** filename must contain .png or .jpeg extension. Image not changed.");
					break; 
				}
				case 3: userProfile.editAge(Menu.getIntInput("Enter Age")); break;
				case 4: connectUsers(userProfile.getUsername()); break;
				case 5: disconnectUsers(userProfile.getUsername()); break;
				case 6: directFriend(userProfile.getUsername()); break;
				case 7: break;
			}
		} while (choice != 7); // because there are only 7 choices
		System.out.println("\n\t *** All Changes made were saved.");  
	}
	
	public void directFriend(String username) {
		System.out.print("\t === Enter username : ");
		String friendName =  sc.nextLine();
		relationshipFlow = new ArrayList<Relationship>(); 
		
		if ( userDatabase.hasFriend(userDatabase.getProfile(username), userDatabase.getProfile(friendName)) ) {
			System.out.print("\n\t *** " + username + " and " + friendName + " are direct friends.");
			return;
		}
			
		relationshipFlow = connections.isfriendOfFriend(username, friendName.trim());
		if (relationshipFlow == null)
			System.out.println("\n\t *** No one from " + username + "'s network is a friend of " + friendName);
		else {			
			System.out.print("\n\t *** " + relationshipFlow.get(0).getUsernameTwo() + " can introduce " + username + " to " + friendName);
			System.out.print("\n\t *** " + (relationshipFlow.get(0).printRelationship()) + " " + (relationshipFlow.get(1).printRelationship()));
		}
	}
	
	public void addProfile() {
		int choice;
		Menu addUserSubmenu = new Menu("New User Type", new String[] { "Adult", "Child", "Back to main menu" });

		addUserSubmenu.displayMenu();
		choice = addUserSubmenu.getValidOption();
		
		switch(choice) {
			case 0 : addProfile("Adult"); break;
			case 1 : addProfile("Child"); break; 
			case 2 : break;
		}
	}
	
	public void addProfile(String profileType) {
		System.out.print("\n\t ========= " + profileType + " ====== ");
		System.out.print("\n\t === Enter username : ");
		String username = sc.nextLine();
		username = username.trim();
		
		if (!userDatabase.existingUser(username)) {
			if (profileType == "Adult") {
				// addAdultProfile(username);
				userDatabase.addUser(new Adult(username));
			} else addChildProfile(username);
			
			System.out.print("\n\t *** User Profile with username '" + username + "' successfully created.");
			editProfile(userDatabase.getProfile(username));
		} else
			System.out.print("\n\t *** User Profile with username '" + username + "' already exists.");
	}
	
	public void addChildProfile(String username) {
		String[] parentName = new String[2];
		int age;

		do {
			age = Menu.getIntInput("Enter age");
			if (age < 3 || age > 17)
				System.out.println("\t *** Invalid Input. Age of child user must be between 3 and 16.");
		} while (age < 3 || age > 17);

		for (int i = 0; i < 2; i++) {
			parentName[i] = getParentsInput(i);
		}

		userDatabase.addUser(new Child(username, age, ((Adult) userDatabase.getProfile(parentName[0])),
				((Adult) userDatabase.getProfile(parentName[1]))));
		
		if ( userDatabase.hasFriend(userDatabase.getProfile(parentName[0]), userDatabase.getProfile(parentName[1])) ) {
 			connections.changeRelationship(parentName[0], parentName[1], "Pr");
 		} else connections.addRelationship(parentName[0], parentName[1], "Pr");
		
		connections.addRelationship(username, parentName[0], "Pr");
		connections.addRelationship(username, parentName[1], "Pr");
	}
	
	public String getParentsInput(int num) { // num is the input number
		int criteria;
		int showErr; 
		String username;
			
			do {
				criteria = 0;
				showErr = 0;
						
				System.out.print("\t === Enter parent username " + (num + 1) + " : ");
				username = sc.nextLine();
				
				if ( !userDatabase.existingUser(username)) {
					System.out.print("\t *** Username entered does not exist. Enter again.\n");
					showErr=1;
				} else criteria++;
				
				if ( userDatabase.getProfile(username) instanceof Adult ) {
					if ( ((Adult) userDatabase.getProfile(username)).getDependent() != null && showErr == 0 ) {
						System.out.print("\t *** Username entered already has a dependent. Enter again.\n");
						showErr=1;
					} else criteria++;
				} else criteria++;					
				
				if ( userDatabase.getProfile(username) instanceof Child && showErr == 0)
					System.out.print("\t *** Username entered is a child profile. Enter again.\n");
				else criteria++;
				
			} while ( criteria != 3 );
			
		return username;
	}

	public void deleteProfile() {
		System.out.print("\n\t ========= Delete Profile " + "====== ");
		System.out.print("\n\t === Enter username : ");
		String username = sc.nextLine();
		username = username.trim(); 
		
		if (	 !userDatabase.existingUser(username) ) {
			System.out.print("\n\t *** User Profile with username '" + username + "' does not exist.");
			return;
		}
		
		if ( userDatabase.getProfile(username) instanceof Adult )
			userDatabase.deleteAdultUser(username);
		else 
			userDatabase.deleteChildUser(username); 
		
		for (int i=0; i<connections.getRelationships().size(); i++)
			connections.getRelationships().remove(i);
	}

	public void connectUsers(String username) {
		System.out.print("\t === Enter friend username to add : ");
		String friendName = sc.nextLine();
		friendName = friendName.trim();
		
		if ( userDatabase.existingUser(friendName.trim()) ) {
			userDatabase.getProfile(username).addFriend(userDatabase.getProfile(friendName));
			connections.addRelationship(username, friendName);
		} else System.out.print("\n\t *** User Profile with username '" + friendName + "' does not exist");
	}
	
	public void disconnectUsers(String username) {
		System.out.print("\t === Enter friend username to delete: ");
		String friendName = sc.nextLine();
		friendName = friendName.trim();
		
		if ( userDatabase.existingUser(friendName) ) {
			User user = userDatabase.getProfile(username);
			
			if ( user.deleteFriend(userDatabase.getProfile(friendName)) )
				connections.deleteRelationship(username, friendName);
		
		} else System.out.print("\n\t *** User Profile with username '" + friendName + "' does not exist");
	}

	public void runSocialNetwork() {
		String mainMenu[] = { "Show all users", "Search user", "Add new user", "Delete a user", "Exit" };
		Menu menu = new Menu("MiniNet Main Menu", mainMenu);
		int choice;
		
		do {
			menu.displayMenu();
			choice = menu.getValidOption();
			switch (choice) {
				case 0: displayAllUsers(); break;
				case 1: searchProfile(); break;
				case 2: addProfile(); break;
				case 3: deleteProfile(); break;
				case 4: System.out.println("\n\t *** See you. You exited the application\n"); break;
			}
		} while (choice != (mainMenu.length-1));
	}
		
}
