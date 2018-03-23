      
/*    
 * ====== Driver.java
 * This class is the main program where the built classes interact. 
 * This class makes use of Profiles, User, Adult, and Child classes. 
 * Driver.java uses these methods in these classes to manage the social
 * network. In particular, this class allows, viewing, adding and 
 * deleting of user profiles, as well as connecting and disconnecting 
 * user profiles.    
 * 
 * */


import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

	private Scanner sc = new Scanner(System.in);
	private Profiles userDatabase = new Profiles();
	private Connections connections = new Connections();

	// Driver constructor to create objects for the program
	public Driver() {
		// add adult profiles
		userDatabase.addUser(new Adult("Alice"));
		userDatabase.addUser(new Adult("Bob"));
		userDatabase.addUser(new Adult("Cathy"));
		userDatabase.addUser(new Adult("Don"));
		userDatabase.addUser(new Adult("John"));
		userDatabase.addUser(new Adult("Jane"));
		
		// create relationships between adult profiles
		userDatabase.getProfile("Alice").addFriend(userDatabase.getProfile("Bob"));
		connections.addRelationship("Alice", "Bob");
		
		userDatabase.getProfile("Alice").addFriend(userDatabase.getProfile("Don"));		
		connections.addRelationship("Alice", "Don");
		
		// add child profiles
		// also create relationships between the profiles
		userDatabase.addUser(new Child("Catherine", 14, ((Adult) userDatabase.getProfile("Bob")),
				((Adult) userDatabase.getProfile("Alice"))));
		connections.addRelationship("Catherine", "Alice");
		connections.addRelationship("Catherine", "Bob");
		
		userDatabase.addUser(new Child("Mich", 15, ((Adult) userDatabase.getProfile("Don")),
				((Adult) userDatabase.getProfile("Cathy"))));
		connections.addRelationship("Mich", "Don");
		connections.addRelationship("Mich", "Cathy");		
	}
	
	// this method displays all the usernames of created user profiles
	// this method also indicates the type of user associated to each username.
	// it displays the usernames in columns of three
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
		
		// print out a legend or key for better user understanding when in use
		System.out.print("\n\n\t *** User no. : ( User type ) Username");
		System.out.print("\n\t *** ( A ) Adult Profile");
		System.out.print("\n\t *** ( C ) Child Profile");
		System.out.println();
	}

	
	// this method searches and displays the user profile information desired
	public void searchProfile() {
		System.out.print("\n\t ========= Search Profile " + "====== ");
		System.out.print("\n\t === Enter username : ");
		String searchName = sc.nextLine();
		searchName = searchName.trim(); // trim leading and trailing whitespaces
		
		// stop the function when username entered does not exist 
		if ( !userDatabase.existingUser(searchName) ) {
			System.out.print("\n\t *** User Profile with username '" + searchName + "' does not exist");
			return;
		}
			
		// this part onward only runs if username entered is found
		System.out.print("\n\t *** User Profile '" + searchName + "' found");
		userDatabase.getProfile(searchName).printProfile();

		// ask user if they would like to edit profile
		// if yes enter edit profile submenu
		System.out.println();
		if (Menu.YesOrNoOption("Edit Profile?") == 1)
			editProfile(userDatabase.getProfile(searchName));		
	}
	
	// this method obtains the sub menu options when the program enters a edit user profile
	// adjusts the edit profile submenu options accordingly
	// e.g., change 'edit status' to 'add status' if user does not have a status present in the profile
	public String[] getEditProfileOptions(User userProfile) {
		String[] options =  { "Show Profile", "Edit Name", "Edit Status", "Edit Image", "Edit Age", "Add a network", 
				"Delete a network", "Find a friend from a current friend", "Delete this user", "Back to main menu"};
		
		if ( userProfile.getFirstName().isEmpty() )
			options[1] = "Add Name";
		
		if ( userProfile.getStatus().isEmpty() )
			options[2] = "Add Status";
		
		if ( userProfile.getImage().isEmpty() )
			options[3] = "Add Image";
		
		if ( userProfile.getAge() == 0 )
			options[4] = "Add Age";
		
		return options;
	}
	
	// this method will lead to a submenu allowing to edit a certain user's profile
	// method containing switch function to choose the type of action that will be executed for a certain profile
	public void editProfile(User user) {
		int choice;
		
		do {
			Menu editSubmenu = new Menu("Edit " + user.getUsername() + "'s Profile", getEditProfileOptions(user));
			editSubmenu.displayMenu();
			choice = editSubmenu.getValidOption();
			
			switch (choice) {
				case 0: { 
					System.out.print("\n\t *** Showing " + user.getUsername() + "'s user profile.");
					user.printProfile(); 
					System.out.println();  
					break;
				}
				// update name of a user
				case 1: editUserStringInput(user, "name"); break; 		
				// update status of a user
				case 2: editUserStringInput(user, "status"); break;			
				// update image of a user
				case 3: editUserStringInput(user, "image"); break;			
				// update age of a user
				case 4: user.editAge(Menu.getIntInput("Enter Age")); break;	
				// connect a user with a friend
				case 5: connectUsers(user.getUsername()); break;				
				// disconnect a user with a friend
				case 6: disconnectUsers(user.getUsername()); break;			
				// find an intermediate friend from user's friends to a new friend
				case 7: friendOfFriend(user.getUsername()); break;     	    
				// delete current user permanently
				case 8: {
					userDatabase.deleteUser(user.getUsername());				
					choice = 9; // exit editing submenu 												
					break;	
				} 
				// exit submenu
				case 9: break;												
			}
		} while (choice != 9); // because there are only 9 choices
		System.out.println("\n\t *** All Changes made were saved.");  
	}
	
	// A method created to allowing editing of a user's profile 
	// handles editing the name, status, and image of a user
	public void editUserStringInput(User user, String type) {
		if (type == "name") {
			System.out.print("\t === Enter First Name : ");
			user.setFirstname(sc.nextLine().trim());
			System.out.print("\t === Enter Last Name : ");
			user.setLastname(sc.nextLine().trim());
		}
		
		if (type == "status") {
			System.out.print("\t === Enter Status : ");
			user.setStatus(sc.nextLine());
		}
		
		if (type == "image") {
			System.out.print("\t === Enter Image : ");
			String newImage =  sc.nextLine();
			if (newImage.toLowerCase().contains(".png") || newImage.toLowerCase().contains(".jpeg"))
				user.setImage(newImage);
			else 
				System.out.print("\n\t *** filename must contain .png or .jpeg extension. Image not changed.");
		}
		
	}
	
	// method displaying a submenu when creating a new profile using the 
	// chosen type of user profile to create calls addUserProfile to 
	// implement appropriate procedures obtaining values for the instantiation 
	// of a new object
	public void addProfile() {
		int choice;
		Menu addUserSubmenu = new Menu("New User Type", new String[] { "Adult", "Child", "Back to main menu" });

		addUserSubmenu.displayMenu();
		choice = addUserSubmenu.getValidOption();
		
		switch(choice) {
			case 0 : addUserProfile("Adult"); break;
			case 1 : addUserProfile("Child"); break; 
			case 2 : break;
		}
	}
	
	// this method adds a new user depending on the type of user profile to be added
	public void addUserProfile(String profileType) {
		System.out.print("\n\t ========= Creating " + profileType + " user ====== ");
		System.out.print("\n\t === Enter username : ");
		String username = sc.nextLine();
		username = username.trim(); // remove leading and trailing whitespaces
		
		if ( userDatabase.existingUser(username) ) {
			System.out.print("\n\t *** User Profile with username '" + username + "' already exists.");
			return;
		}
		
		// creating an adult profile just needs a username
		if (profileType == "Adult")
			userDatabase.addUser(new Adult(username));
		else
			addChildProfile(username);
		// creating an child profile needs more than just a username
		// call addChildProfile method to implement additional procedures
		// before instantiating a child user profile
			
		System.out.print("\n\t *** User Profile with username '" + username + "' successfully created.");
		// after user profile is created, allow further editing of user profile
		editProfile(userDatabase.getProfile(username));
	}
	
	// this method obtains input from keyboard to instantiate the a child user profile
	// when creating a child profile we ensure that everything is correct. This is the only part of 
	// the program where the user is required to input values until they are correct.
	public void addChildProfile(String username) {
		String[] parentName = new String[2];
		int age;

		do { // enter age of a child user
			age = Menu.getIntInput("Enter age");
			if (age < 3 || age > 17)
				System.out.println("\t *** Invalid Input. Age of child user must be between 3 and 16.");
		} while (age < 3 || age > 17);

		parentName[0] = getParentsInput(0); // enter the parents of the user, utilises getParentsInput
			
		// if the first parent name given has a corresponding partner, automatically, set the partner.
		if ( ((Adult) userDatabase.getProfile(parentName[0])).getPartner() != null) {
			parentName[1] = ((Adult) userDatabase.getProfile(parentName[0])).getPartner().getUsername();
			System.out.print("\n\t *** Don't have too enter second parent name. Current partner of " + parentName[1] + " retrieved.");
		} else {
			do {
				parentName[1] = getParentsInput(1);
			} while (!validateParentTwoInput(parentName[0], parentName[1]));
		}
		
		// instantiate child user using given inputs
		userDatabase.addUser(new Child(username, age, ((Adult) userDatabase.getProfile(parentName[0])), 
				((Adult) userDatabase.getProfile(parentName[1]))));
		
		connections.addRelationship(username, parentName[0]); // add relationships between child and parents 
		connections.addRelationship(username, parentName[1]);
		
		// if there is not current relationship between the parents, need to create a relationship for them
		if ( !connections.existingRelationship(parentName[0], parentName[1]) )
			connections.addRelationship(parentName[0], parentName[1]);
	}
	
	// this method gets input from keyboard of a child users parents
	// before an adult user profile can be assigned to a parent, the profile must satisfy 3 criteria
	public String getParentsInput(int num) { // num is the input number / or the current parent number to be provided
		int criteria; // criteria tally
		int showErr; // this is used to ensure that only 1 type of error message is shown
		String username;
			
			do {
				criteria = 0;
				showErr = 0;
						
				System.out.print("\t === Enter parent username " + (num + 1) + " : ");
				username = sc.nextLine();
				
				// first condition: check if username entered exists in current user profiles list
				if ( !userDatabase.existingUser(username)) {
					System.out.print("\t *** Username entered does not exist. Enter again.\n");
					showErr=1;
				} else criteria++;					
				
				// second condition: if username provided is of a child type user. this type of profile cant be a parent 
				if ( userDatabase.getProfile(username) instanceof Child && showErr == 0)
					System.out.print("\t *** Username entered is a child profile. Enter again.\n");
				else criteria++;
				
			} while ( criteria != 2 );
			
		return username;
	}

	// certain additional conditions the second parent name is being give from the keyboard
	// this method validates these. if conditions are not met cannot progress.
	public boolean validateParentTwoInput(String parentOne, String parentTwo) {
		if (parentTwo.equals(parentOne)) {
			System.out.print("\t *** Invalid Input. Enter another username.\n");
			return false;
		}
		
		if (((Adult) userDatabase.getProfile(parentTwo)).getPartner() != null) {
			System.out.print("\n\t *** Invalid Input. Username entered already has another partner\n");
			return false;
		}
			
		return true;
	}
	 
	// method to permanently delete a user from the current list of profiles
	public void deleteProfile() {
		System.out.print("\n\t ========= Delete Profile " + "====== ");
		System.out.print("\n\t === Enter username : ");
		String username = sc.nextLine();
		username = username.trim(); 
		
		if (	 !userDatabase.existingUser(username) ) {
			System.out.print("\n\t *** User Profile with username '" + username + "' does not exist.");
			return;
		}
		
		if ( userDatabase.deleteUser(username) )
			connections.deleteAllUserRelationships(username);
			// if user profile is successfully deleted, must also delete all relationships that includes him
	}
	
	// method to find whether a user can be friends with another user through a mutual friend 
	public void friendOfFriend(String username) {
		System.out.print("\t === Enter username : ");
		String friendName =  sc.nextLine();
		friendName = friendName.trim();
		
		// method will stop if the friend search is not in the current social network
		if ( !userDatabase.existingUser(friendName) ) {
			System.out.print("\n\t *** user profile with username " + friendName + " does not exist. Cannot find direct friends.");
			return;
		}
		
		// method will stop here if friend entered is in the list of friends of the user
		// they are direct friends, and an intermediate friend is not needed for them to be friends
		if ( userDatabase.hasFriend(userDatabase.getProfile(username), userDatabase.getProfile(friendName)) ) {
			System.out.print("\n\t *** " + username + " and " + friendName + " are direct friends.");
			return;
		}
		
		ArrayList<Relationship> relationshipFlow = connections.findFriendOfFriend(username, friendName);
		
		// if an intermediate friend is found, this section will run
		if ( relationshipFlow != null ) {
			System.out.print("\n\t *** " + relationshipFlow.get(0).getUsernameTwo() + " can introduce " + username + " to " + friendName);
			System.out.print("\n\t *** " + username + " --> " + relationshipFlow.get(0).getUsernameTwo() + " --> " + friendName);
			return; // finish the method when this section is ran
		}
		
		System.out.print("\n\t *** " + username + " has no friends that can introduce him to " + friendName);
	}
	
	// method to connect to friends
	public void connectUsers(String username) {
		System.out.print("\t === Enter friend username to add : ");
		String friendName = sc.nextLine();
		friendName = friendName.trim();
		
		if ( userDatabase.existingUser(friendName) ) {
			if (userDatabase.getProfile(username).addFriend(userDatabase.getProfile(friendName))) {
				connections.addRelationship(username, friendName); // add relationships between users
				System.out.print("\n\t *** " + username + " and " + friendName + " are now friends");
			}
		} else System.out.print("\n\t *** User Profile with username '" + friendName + "' does not exist");
	}
	
	// method to disconnect to friends
	public void disconnectUsers(String username) {
		System.out.print("\t === Enter friend username to delete: ");
		String friendName = sc.nextLine();
		friendName = friendName.trim();
		
		if ( userDatabase.existingUser(friendName) ) {
			User user = userDatabase.getProfile(username);
			user.deleteFriend(userDatabase.getProfile(friendName));
			connections.deleteRelationship(username, friendName); // delete the relationships between users
		} else System.out.print("\n\t *** User Profile with username '" + friendName + "' does not exist");
	}

	// method where the social network program will run
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
