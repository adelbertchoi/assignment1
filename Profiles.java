
import java.util.ArrayList;

public class Profiles {

	ArrayList<User> userProfiles;
	
	// constructor for a complete list of profiles
	public Profiles() {
		this.userProfiles = new ArrayList<User>();
	}
	
	// return the entire arraylist of users
	public ArrayList<User> getAllProfiles() {
		return this.userProfiles;
	}
	
	public boolean existingUser(String username) {
		for (int i=0; i<userProfiles.size(); i++) {
			if ( userProfiles.get(i).getUsername().equals(username) )
				return true;
		} 
		return false;
	}
	
	public boolean hasFriend(User user, User friend) {
		for (int i=0; i<user.getFriends().size(); i++) {
			if ( user.getFriends().get(i) == friend )
				return true;
		}
		return false;
	}
	
	public void addUser(User newUser) {
		if ( !existingUser(newUser.getUsername()) ) {
			userProfiles.add(newUser);
		}
	}
	
	public void deleteUserfromFriends(User user) {
		for (int i=0; i<user.getFriends().size(); i++)
			user.getFriends().get(i).deleteFriend(user);
	}
	
	public void deleteAdultUser(String username) {
		if ( existingUser(username) ) {
			
			Adult user = ((Adult) getProfile(username));
			
			if ( (user.getPartner() != null) && (user.getDependent() != null) ) {
				System.out.print("\n\t *** Cannot delete '" + username + "' since user has a dependent");
				return;
			}
			
			if ( user.getPartner() != null )
				user.getPartner().setPartner(null);
			
			for (int i=0; i<userProfiles.size(); i++ ) {
				if ( userProfiles.get(i).getUsername().equals(username) ) {
					this.deleteUserfromFriends(userProfiles.get(i));
					userProfiles.remove(i);
					System.out.print("\n\t *** User Profile '" + username + "' successfully deleted.");
				}
			}
		}
	}		
		
		
	public void deleteChildUser(String username) {
		if (existingUser(username)) {

			Child user = ((Child) getProfile(username));

			user.getParentOne().setDepedent(null); 
			user.getParentTwo().setDepedent(null); 

			for (int i = 0; i < userProfiles.size(); i++) {
				if (userProfiles.get(i).getUsername().equals(username)) {
					this.deleteUserfromFriends(userProfiles.get(i));
					userProfiles.remove(i);
					System.out.print("\n\t *** User Profile '" + username + "' successfully deleted.");
				}
			}
		}
	}
	
	
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
