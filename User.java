import java.util.ArrayList;

public abstract class User {
	
	private String username;
	private String status = new String();
	private String image = new String();
	private int age = 0;
	private ArrayList<User> friends;
	
	// constructors 
	public User(String username, int age) {
		this.username = username;
		this.age = age;
		this.friends = new ArrayList<User>();
	}
	
	// alternative constructor if only the of a new user profile is given
	public User(String username) {
		this.username = username;
		this.friends = new ArrayList<User>();
	}
	
	// getters to obtain user profile information
	public String getUsername() { return this.username; }
	public String getStatus() { return this.status; }
	public String getImage() { return this.image; }
	public int getAge() { return this.age; }
	public ArrayList<User> getFriends() { return this.friends; }
	
	// setters to allow changes to user profile information
	public void setUsername(String newUsername) { this.username = newUsername; }
	public void setImage(String newImage) { this.image = newImage; }
	public void setAge(int newAge) { this.age = newAge; };
	public void setStatus(String newStatus) { this.status = newStatus; }
	
	public void addToFriends(User friend) { this.friends.add(friend); } // this method allows us to add a friend to our list
	
	public void removeFromFriends(User friend) { 
		for (int i=0; i<friends.size(); i++) {
			if ( friends.get(i) == friend )
				friends.remove(i);
		}
	}
	
	// abstract methods
	// this method is to add a friend, it has conditions and uses addToFriend method to edit the friends arraylist
	public abstract boolean addFriend(User friend); 
	public abstract void printProfile();
	public abstract void editAge(int newAge);
	
	// method to delete a friend
	public boolean deleteFriend(User friend) {
		for (int i=0; i<getFriends().size(); i++) {
			if ( this.friends.get(i).getUsername() == friend.getUsername() ) {
				removeFromFriends(friend);
				friend.removeFromFriends(this);
				System.out.print("\n\t *** " + getUsername() + " and " + friend.getUsername() + " are not friends anymore");
				return true;
			}
		} 
		return false;
	}

}
