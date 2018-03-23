       
/*          
 * ====== Relationship.java
 * This class is stores an existing relationship between certain users 
 * This would make finding relationships between users much more efficient
 * rather than having to go through each of their friends list instance
 * variables.
 * 
 * All users that have a certain relationship can create a relationship
 * instance. These include.
 *  
 * Parent to Child
 * Adult friend to Adult friend
 * Child friend to Child friend
 * Parther to Partner
 * 
 * A limitation of this class is that it does not keep track of the type
 * of relationship between users. a user that is not part of any relationship
 * instance does not have any networks.
 * 
 * */

public class Relationship {
	
	// instance variables for relationship class
	public String usernameOne;
	public String usernameTwo;
	
	
	// constructor for the Relationship class
	// obviously two usernames are needed to instantiate this class
	public Relationship(String usernameOne, String usernameTwo) {
		this.usernameOne = usernameOne;
		this.usernameTwo = usernameTwo;
	}
	
	// getters - to obtain the usernames of the users part of a relationship
	public String getUsernameOne() { return this.usernameOne; }
	public String getUsernameTwo() { return this.usernameTwo; }
	
	// print - to print out the relationship direction of the users
	public String printRelationship() { return this.usernameOne + " --> " + this.usernameTwo; }
	
}
