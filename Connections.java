
import java.util.ArrayList;

public class Connections {
	
	ArrayList<Relationship> relationships; 
	
	public Connections() {
		this.relationships = new ArrayList<Relationship>();
	}
	
	public ArrayList<Relationship> getRelationships() {
		return this.relationships;
	}
	
	public boolean hasRelationships(String username) {
		for (int i=0; i<relationships.size(); i++) {
			if ( relationships.get(i).getUsernameOne().equals(username) )
				return true;
		}
		return false;
	}
	
	public ArrayList<Relationship> getUserRelationships(String username) {
		ArrayList<Relationship> userRelationships = new ArrayList<Relationship>();
		
			for (int i=0; i<relationships.size(); i++) {
				if (relationships.get(i).usernameOne.equals(username))
					userRelationships.add(relationships.get(i));
			}
		
		return userRelationships;
	}
	
	public boolean existingRelationship(String usernameOne, String usernameTwo) {
		for (int i=0; i<relationships.size(); i++) {
			if (relationships.get(i).getUsernameOne().equals(usernameOne) && relationships.get(i).getUsernameTwo().equals(usernameTwo))
				return true;
		}
		return false;
	}
	
	public Relationship getRelationship(String usernameOne, String usernameTwo) {
		for (int i=0; i<relationships.size(); i++) {
			if (relationships.get(i).getUsernameOne().equals(usernameOne) && relationships.get(i).getUsernameTwo().equals(usernameTwo))
				return relationships.get(i);
		}
		return null;
	}
	
	
	public void addRelationship(String usernameOne, String usernameTwo) {
		relationships.add(new Relationship(usernameOne, usernameTwo));
		relationships.add(new Relationship(usernameTwo, usernameOne));
	}
	
	// method overload
	public void addRelationship(String usernameOne, String usernameTwo, String type) {
		relationships.add(new Relationship(usernameOne, usernameTwo, type));
		relationships.add(new Relationship(usernameTwo, usernameOne, type));
	}
	
	public void deleteRelationship(String usernameOne, String usernameTwo) {
		for (int i = 0; i < relationships.size(); i++) {
			if (relationships.get(i).getUsernameOne() == usernameOne && relationships.get(i).getUsernameTwo() == usernameTwo)
				relationships.remove(i);
			if (relationships.get(i).getUsernameOne() == usernameTwo && relationships.get(i).getUsernameTwo() == usernameOne)
				relationships.remove(i);
		}
	}
	
	public void changeRelationship(String usernameOne, String usernameTwo, String type) {		
		if (type == "Pr") {
			getRelationship(usernameOne, usernameTwo).setToParent();
			getRelationship(usernameTwo, usernameOne).setToParent();
		} 
		
		if (type == "Pa") {
			getRelationship(usernameOne, usernameTwo).setToPartner();
			getRelationship(usernameTwo, usernameOne).setToPartner();
		} 
	}

	public ArrayList<Relationship> isfriendOfFriend(String username, String friend) {
		ArrayList<Relationship> relationshipFlow = new ArrayList<Relationship>();
		ArrayList<Relationship> userRelationships = getUserRelationships(username);
		ArrayList<Relationship> friendRelationships;
		
		for ( int i=0; i<userRelationships.size(); i++ ) {
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
