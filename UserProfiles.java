
import java.util.ArrayList;

public class UserProfiles {

	ArrayList<User> userProfiles = new ArrayList<User>();
	
	// constructor for a complete list of profiles
	public UserProfiles() {
		this.userProfiles = new ArrayList<User>();
	}
	
	// return the entire arraylist of users
	public ArrayList<User> getAllProfiles() {
		return this.userProfiles;
	}
	
	public boolean existingUser(String username) {
		for (int i=0; i<userProfiles.size(); i++ ) {
			if ( userProfiles.get(i).getUsername().equals(username) )
				return true;
		}
		return false;
	}
	
	public void addUser(User newUser) {
		if ( !existingUser(newUser.getUsername()) ) {
			userProfiles.add(newUser);
			System.out.println("New User Profile : " + newUser.getUsername() + " successfully added");
		} // else System.out.println("Username : " + newUser.getUsername() + " already exists");
	}
	
	public void deleteUserfromFriends(User user) {
		for (int i=0; i<user.getFriends().size(); i++)
			user.getFriends().get(i).deleteFriend(user);
	}
	
	public void deleteUser(String username) {
		if ( existingUser(username) ) {
			for (int i=0; i<userProfiles.size(); i++ ) {
				if ( userProfiles.get(i).getUsername().equals(username) ) {
					this.deleteUserfromFriends(userProfiles.get(i));
					userProfiles.remove(i);
				}
			}
		} else System.out.println("No such user profile currently in system");
	}
	
	public User getProfile(String username) {
		if ( existingUser(username) ) {
			for (int i=0; i<userProfiles.size(); i++ ) {
				if ( userProfiles.get(i).getUsername().equals(username) ) 
					return userProfiles.get(i);
			}
		}
		System.out.println(username + " does not exist");
		return null;
	}
	
}
