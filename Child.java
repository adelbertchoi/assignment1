
public class Child extends User {

	private Adult[] parent = new Adult[2];
	
	// constructors
	public Child(String username, String status, int age, Adult parentOne, Adult parentTwo) {
		super(username, status, age);
		this.parent[0] = parentOne;
		this.parent[1] = parentTwo;
		parentOne.setDepedent(this, parentTwo);
		parentTwo.setDepedent(this, parentOne);
	}

	public Child(String username, int age, Adult parentOne, Adult parentTwo) {
		super(username, age);
		this.parent[0] = parentOne;
		this.parent[1] = parentTwo;
		parentOne.setDepedent(this, parentTwo);
		parentTwo.setDepedent(this, parentOne);
	}
	
	public Adult[] getParents() { return this.parent; }
	
	// method to add a network for an adult profile
	public boolean addFriend(User friend) {
		
		if ( !(friend instanceof Child) ) {
			System.out.print("\n\t *** Dependents cannot be friends with Adults");
			return false;
		}	
		
		if ( Math.abs(friend.getAge() - this.getAge()) > 3 ) {
			System.out.print("\n\t *** Dependents can be friends with Adults");
			return false;
		}
		
		for (int i=0; i<super.getFriends().size(); i++) {
			if ( super.getFriends().get(i).getUsername() == friend.getUsername() ) {
				System.out.print("\n\t *** " + super.getUsername() + " and " + friend.getUsername() + " are already friends");
				return false;
			}
		}
	
		super.addToFriends(friend);
		friend.addToFriends(this);
		return true;
	}
	
	public void printProfile() {
		System.out.println("\n\t ========= Profile ====== ");
		System.out.printf("\t\t %-10s :   %-15s", "Username", super.getUsername());
		System.out.printf("\n\t\t %-10s :   %-15s", "Age", super.getAge());
		
		if ( !(super.getStatus().isEmpty()) )
			System.out.printf("\n\t\t %-10s :   %-15s", "Status", super.getStatus());
		
		if ( parent[0] != null ) {
			System.out.printf("\n\t\t %-10s :   %-15s", "Parents", parent[0].getUsername());
			System.out.printf("\n\t\t %-10s     %-15s", "", parent[1].getUsername());
		}
			
		if( !super.getFriends().isEmpty() ) {
			System.out.printf("\n\t\t %-10s : ", "Network");
			for(int i=0; i<super.getFriends().size(); i++)
				System.out.printf("\n\t\t   %-3d %-10s", (i+1), super.getFriends().get(i).getUsername());
		}			
		// System.out.println("\n\t ======================================= ");
	}
}