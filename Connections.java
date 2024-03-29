       
/*     
 * ====== Connections.java
 * This class is manages all created and existing relationships between 
 * users. Similar to the Profiles class, Connections class also has methods
 * to add, delete, and search for relationships present between users.
 * 
 * */

import java.util.ArrayList;

public class Connections {
	
	// instance variables
	// this class is a database for all created relationships
	ArrayList<Relationship> relationships; 
	
	// constructor
	public Connections() {
		this.relationships = new ArrayList<Relationship>();
	}
	
	// this method returns all existing relationships created for the class
	public ArrayList<Relationship> getRelationships() {
		return this.relationships;
	}
	
	// this method retrieves all existing relationships of a certain user
	// this method is helpful when looking for a friend of a friend 
	public ArrayList<Relationship> getUserRelationships(String username) {
		ArrayList<Relationship> userRelationships = new ArrayList<Relationship>();
		
		for (int i = 0; i < relationships.size(); i++) {
			if ( relationships.get(i).usernameOne.equals(username) )
				userRelationships.add(relationships.get(i));
		}
		
		return userRelationships;
	}
	
	// this method checks whether a certain relationship exists
	public boolean existingRelationship(String usernameOne, String usernameTwo) {
		for (int i=0; i<relationships.size(); i++) {
			
			// given two usernames
			// the vice versa relationship does not need to be checked i.e., A -> B, and B -> A, 
			// since the program assumes that if one of them is searched the other relationship is 
			// also present. This is possible since Driver class manages what relationships gets 
			// added
			if ( relationships.get(i).getUsernameOne().equals(usernameOne) && relationships.get(i).getUsernameTwo().equals(usernameTwo) )
				return true;
		}
		return false;
	}
	
	// a method to return a certain relationship
	public Relationship getRelationship(String usernameOne, String usernameTwo) {
		for (int i=0; i<relationships.size(); i++) {
			if ( relationships.get(i).getUsernameOne().equals(usernameOne) && relationships.get(i).getUsernameTwo().equals(usernameTwo) )
				return relationships.get(i);
		}
		return null;
	}
	
	// a method to add a relationship.
	public void addRelationship(String usernameOne, String usernameTwo) {
		// given two usernames it is needed to created relationship instances
		// vice versa relationship needs to created as well
		// this will make searching for friends of friends easier.
		// also, it makes more sense to have a relationship go both ways 
		relationships.add(new Relationship(usernameOne, usernameTwo));
		relationships.add(new Relationship(usernameTwo, usernameOne));
	}
	
	// a method to completely delete a relationship
	// similar to addRelationhips, the vice versa relationship should also be removed
	public void deleteRelationship(String usernameOne, String usernameTwo) {
		for (int i = 0; i < relationships.size(); i++) {
			if (relationships.get(i).getUsernameOne().equals(usernameOne) && relationships.get(i).getUsernameTwo().equals(usernameTwo))
				relationships.remove(i);
			if (relationships.get(i).getUsernameOne().equals(usernameTwo) && relationships.get(i).getUsernameTwo().equals(usernameOne))
				relationships.remove(i);
		}
	}
	
	// this method removes all existing relationships of a certain user
	// this method is used when the program is permanently deleting a certain user.
	public void deleteAllUserRelationships(String username) {
		for (int i = 0; i < relationships.size(); i++)
			if ( relationships.get(i).getUsernameOne().equals(username) || relationships.get(i).getUsernameTwo().equals(username) )
				relationships.remove(i);
	}
	
	// this method is used to find an intermediate friend between two friends
	// provided a username and a friend of interest, find a friend that is common between the two
	public ArrayList<Relationship> findFriendOfFriend(String username, String friend) {
		// this is where the flow of relationships is stored
		ArrayList<Relationship> relationshipFlow = new ArrayList<Relationship>();
		// this stores all the relationships of a user
		ArrayList<Relationship> userRelationships = getUserRelationships(username);
		// this stores all the relationships of a certain friend of a user 
		ArrayList<Relationship> friendRelationships;
		
		// outer loop go through all of the relationships of a user
		for ( int i=0; i<userRelationships.size(); i++ ) {
			
			// second loop goes through all the relationships of a certain friend
			// finding if the friend of interest is present in a user's friend
			friendRelationships = getUserRelationships(userRelationships.get(i).getUsernameTwo());
			for( int j=0; j<friendRelationships.size(); j++) {
				if ( friendRelationships.get(j).getUsernameTwo().equals(friend) ) {
					relationshipFlow.add(userRelationships.get(i));
					relationshipFlow.add(friendRelationships.get(j));
					return relationshipFlow;
				}
			}
		}
		return null; 
	}
		
}
