
/*       
 * ====== Profiles.java
 * This class is stores users instantiated. Both adult and child users 
 * are added and managed in this class. This social network assumes that 
 * the username entered for each user can uniquely identify a certain 
 * instantiated user. 
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
	
	// this is the overall function to delete a user from the list of user profiles
	// depending on the type of user it calls the right method to check for 
	// conditions before a user is fully deleted from the list of profiles
	public boolean deleteUser(String username) { 
		// if no user with username exist cannot delete anything
		if ( !existingUser(username) )
			return true;
			
		if ( getProfile(username) instanceof Adult )
			return deleteAdultUser(username);
		else
			return deleteChildUser(username);	
	}
	
	
	// method to deleting an adult type user permanently from the list of profiles 
	// certain conditions need to be met before an adult user can be deleted 
	public boolean deleteAdultUser(String username) {
		Adult user = ((Adult) getProfile(username));

		// if adult user has a partner and dependent account cannot delete the user
		// cause relationships will be broken
		// adult user must not have a dependent to delete their profile
		// other the dependent user profile must be deleted to delete adult profile
		if ((user.getPartner() != null) && (!user.getDependents().isEmpty())) {
			System.out.print("\n\t *** Cannot delete '" + username + "' since user has a dependent");
			return false;
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
				return true;
			}
		}
		return false;
	}		
	
	// method to deleting a child type user permanently from the list of profiles 
	// this method will always return true, since a child profile can always be deleted
	// however, making the method return true will be beneficial for better implementation 
	// in the Driver class
	public boolean deleteChildUser(String username) {
		Child user = ((Child) getProfile(username));

		// set the dependents of parents to null
		// since this child user profile will no longer exist
		user.getParentOne().removeDepedent(user); 
		user.getParentTwo().removeDepedent(user);

		// delete the child user profile from the list of user profiles
		for (int i = 0; i < userProfiles.size(); i++) {
			if (userProfiles.get(i).getUsername().equals(username)) {
				deleteUserfromFriends(userProfiles.get(i));
				userProfiles.remove(i);
				System.out.print("\n\t *** User Profile '" + username + "' successfully deleted.");
			}
		}
		return true;
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
