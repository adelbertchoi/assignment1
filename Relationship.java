
public class Relationship {
	
	public String usernameOne;
	public String usernameTwo;
	// public char type;
	
	public Relationship(String usernameOne, String usernameTwo) {
		this.usernameOne = usernameOne;
		this.usernameTwo = usernameTwo;
	}
	
	public String getUsernameOne() { return this.usernameOne; }
	public String getUsernameTwo() { return this.usernameTwo; }
	
	public void printRelationship() {
		System.out.println(this.usernameOne + " --> " + this.usernameTwo);
	}
	
}
