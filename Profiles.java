
/*
 * ====== Profiles.java
 * 
 * This class is stores users instantiated. Both adult and  
 * child users are added and managed in this class.  
 * This social network assumes that the username entered 
 * for each user can uniquely identify a certain instantiated
 * user. 
 * 
 * */

import java.util.ArrayList;

public class Profiles {
	
	// instance variable for Profiles class
	// this list keeps track of all types of profiles created
	ArrayList<User> userProfiles;
	
	// constructor for a complete list of profiles
	public Profiles() {
		this.userProfiles = new ArrayList<User>();
	}
	
	// method to return the entire list of created users
	public ArrayList<User> getAllProfiles() {
		return this.userProfiles;
	}
	
	// method to check whether a certain user is currently in the list or not
	public boolean existingUser(String username) {
		for (int i=0; i<userProfiles.size(); i++) {
			if ( userProfiles.get(i).getUsername().equals(username) )
				return true;
		} 
		return false;
	}
	
	// method to check if a certain user has a certain friend
	public boolean hasFriend(User user, User friend) {
		for (int i=0; i<user.getFriends().size(); i++) {
			if ( user.getFriends().get(i) == friend )
				return true;
		}
		return false;
	}
	
	// method to add a new user to the current profiles list
	public void addUser(User newUser) {
		// condition to check if the user added currently exists
		// don't add the user if he/she currently exist
		if ( !existingUser(newUser.getUsername()) ) {
			userProfiles.add(newUser);
		}
	}
	
	// method to delete a certain user from the list of friends of other users
	// only works, if they the user if present in the list of friends of other users
	public void deleteUserfromFriends(User user) {
		for (int i=0; i<user.getFriends().size(); i++)
			user.getFriends().get(i).deleteFriend(user);		
	}
	
	
	// method to deleting an adult type user permanently from the list of profiles 
	// certain conditions need to be met before an adult user can be deleted 
	public void deleteAdultUser(String username) {
		// if no user with username exist cannot delete anything
		if ( !existingUser(username) )
			return;
			
		Adult user = ((Adult) getProfile(username));

		// if adult user has a partner and dependent account cannot delete the user
		// cause relationships will be broken
		// adult user must not have a dependent to delete their profile
		// other the dependent user profile must be deleted to delete adult profile
		if ((user.getPartner() != null) && (user.getDependent() != null)) {
			System.out.print("\n\t *** Cannot delete '" + username + "' since user has a dependent");
			return;
		}
		
		// if user profile has a partner adjust partners instance variables accordingly
		// before deleting the adult user
		if (user.getPartner() != null )
			user.getPartner().setPartner(null);

		// delete the adult user profile from the list of user profiles
		for (int i = 0; i < userProfiles.size(); i++) {
			if (userProfiles.get(i).getUsername().equals(username)) {
				deleteUserfromFriends(userProfiles.get(i));				
				userProfiles.remove(i);
				System.out.print("\n\t *** User Profile '" + username + "' successfully deleted.");
			}
		}
	}		
	
	// method to deleting a child type user permanently from the list of profiles 
	// certain conditions need to be met before an adult user can be deleted 	
	public void deleteChildUser(String username) {
		// if no user with username exist cannot delete anything
		if (!existingUser(username))
			return;

		Child user = ((Child) getProfile(username));

		// set the dependents of parents to null
		// since this child user profile will no longer exist
		user.getParentOne().setDepedent(null); 
		user.getParentTwo().setDepedent(null);

		// delete the child user profile from the list of user profiles
		for (int i = 0; i < userProfiles.size(); i++) {
			if (userProfiles.get(i).getUsername().equals(username)) {
				this.deleteUserfromFriends(userProfiles.get(i));
				userProfiles.remove(i);
				System.out.print("\n\t *** User Profile '" + username + "' successfully deleted.");
			}
		}
	}
	
	// method to return a certain user 
	public User getProfile(String username) {
		if ( existingUser(username) ) {
			for (int i=0; i<userProfiles.size(); i++ ) {
				if ( userProfiles.get(i).getUsername().equals(username) ) 
					return userProfiles.get(i);
			}
		}
		return null;
	}
	
}