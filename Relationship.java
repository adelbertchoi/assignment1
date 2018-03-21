
public class Relationship {
	
	public String usernameOne;
	public String usernameTwo;
	public String type; //  either Fr - friend, Pr - parent, Pa - Partner
	
	public Relationship(String usernameOne, String usernameTwo, String type) {
		this.usernameOne = usernameOne;
		this.usernameTwo = usernameTwo;
		this.type = type;
	}
	
	public Relationship(String usernameOne, String usernameTwo) {
		this.usernameOne = usernameOne;
		this.usernameTwo = usernameTwo;
		this.type = "Fr";
	}
	
	public String getUsernameOne() { return this.usernameOne; }
	public String getUsernameTwo() { return this.usernameTwo; }
	public String getRelationshipType() { return this.type; }
	
	public void setToParent() { this.type = "Pr"; }
	public void setToPartner() { this.type = "Pa"; }
	
	public String printRelationship() { return this.usernameOne + " --> " + this.usernameTwo; }
	
}
