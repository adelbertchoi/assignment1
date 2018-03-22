
/*
 * ====== User.java
 * This class is a stores all the relevant information for for a certain 
 * user. These include username, age, status, image, and most importantly 
 * a list of friends. Since, there can be two types of users. This class 
 * is abstract hence cannot be instantiated. However, this class is useful 
 * for a number of instance variables and methods in this class can be 
 * inherited in subclasses. 
 * 
 * */

import java.util.ArrayList;

public abstract class User {
	
	private String username;
	private String firstname = new String();
	private String lastname = new String();
	private String status = new String();
	private String image = new String();
	private int age = 0;
	private ArrayList<User> friends;
	
	// User profile constructor - this is mostly used for instantiating child users 
	public User(String username, int age) {
		this.username = username;
		this.age = age;
		this.friends = new ArrayList<User>();
	}
	
	// alternative constructor 
	// User profile constructor - this is mostly used for instantiating adult users
	public User(String username) {
		this.username = username;
		this.friends = new ArrayList<User>();
	}
	
	// getters - to obtain user class instance variables 
	// to obtain user profile information
	public String getUsername() { return this.username; }
	public String getFirstName() { return this.firstname; }
	public String getLastName() { return this.lastname; }
	public String getStatus() { return this.status; }
	public String getImage() { return this.image; }
	public int getAge() { return this.age; }
	public ArrayList<User> getFriends() { return this.friends; }
	
	// setters - to allow changes to user class instance variables
	public void setUsername(String newUsername) { this.username = newUsername; }
	public void setFirstname(String newFirstname) { this.firstname = newFirstname; }
	public void setLastname(String newLastname) { this.lastname = newLastname; }
	public void setImage(String newImage) { this.image = newImage; }
	public void setAge(int newAge) { this.age = newAge; };
	public void setStatus(String newStatus) { this.status = newStatus; }
	
	// can be considered a setter
	// this method adds a friend to a user's friend list
	// this method is required to make changes to friends instance variables
	// otherwise, modifying friends would be impossible in subclass methods
	public void addToFriends(User friend) { this.friends.add(friend); } 
	
	// modifier 
	// this method removes a friend from the user's friend list
	// this method is required to make changes to friends instance variables
	// otherwise, modifying friends would be impossible in subclass methods
	public void removeFromFriends(User friend) { 
		for (int i=0; i<friends.size(); i++) {
			if ( friends.get(i) == friend )
				friends.remove(i);
		}
	}
	
	// abstract methods
	// since adult and child user types have different conditions when adding friends
	public abstract boolean addFriend(User friend); 	 
	
	// since adult and child user types have instance variables to be printed 
	public abstract void printProfile();
	
	// since adult and child user types have different conditions when setting user age
	// this method makes use of setAge() method to set the user's age
	public abstract void editAge(int newAge);
	
	// method to delete a friend
	// this method returns true if friend was successfully deleted from friends list
	// otherwise false
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
