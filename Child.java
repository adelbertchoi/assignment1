
public class Child extends User {

	private Adult parentOne;
	private Adult parentTwo;
	
	// constructor
	public Child(String username, int age, Adult parentOne, Adult parentTwo) {
		super(username, age);
		this.parentOne = parentOne;
		this.parentTwo = parentTwo;
		parentOne.setDepedent(this);
		parentTwo.setDepedent(this);
		parentOne.setPartner(parentTwo);
		parentTwo.setPartner(parentOne);
	}
	
	public Adult getParentOne() { return this.parentOne; }
	public Adult getParentTwo() { return this.parentTwo; }
	
	public void editAge(int newAge) {
		if (newAge < 2 || newAge > 17) {
			System.out.print("\n\t *** Age of child user must be between 3 and 16");
			return;
		}
		
		super.setAge(newAge);
	}
	
	// method to add a network for an adult profile
	public boolean addFriend(User friend) {
		
		if ( !(friend instanceof Child) ) {
			System.out.print("\n\t *** Child type users cannot be friends with Adults type users");
			return false;
		}	
		
		if ( Math.abs(friend.getAge() - this.getAge()) > 3 ) {
			System.out.print("\n\t *** Difference between ages of users is more than 3 years. User cannot add friend");
			return false;
		}
		
		if ( super.getFriends().contains(friend) ) {
			System.out.print("\n\t *** " + super.getUsername() + " and " + friend.getUsername() + " are already friends");
			return false;
		}
	
		super.addToFriends(friend);
		friend.addToFriends(this);
		System.out.print("\n\t *** " + getUsername() + " and " + friend.getUsername() + " are now friends");
		return true;
	}
	
	public void printProfile() {
		System.out.println("\n\t ========= Child User Profile ====== ");
		System.out.printf("\t\t %-10s :   %-15s", "Username", super.getUsername());
		System.out.printf("\n\t\t %-10s :   %-15s", "Age", super.getAge());
		
		if ( !(super.getImage().isEmpty()) )
			System.out.printf("\n\t\t %-10s :   %-15s", "Image", super.getImage());
		
		if ( !(super.getStatus().isEmpty()) )
			System.out.printf("\n\t\t %-10s :   %-15s", "Status", super.getStatus());
		
		if ( parentOne != null ) {
			System.out.printf("\n\t\t %-10s :   %-15s", "Parents", parentOne.getUsername());
			System.out.printf("\n\t\t %-10s     %-15s", "", parentTwo.getUsername());
		}
			
		if( !super.getFriends().isEmpty() ) {
			System.out.printf("\n\t\t %-10s : ", "Network");
			for(int i=0; i<super.getFriends().size(); i++)
				System.out.printf("\n\t\t   %-3d %-10s", (i+1), super.getFriends().get(i).getUsername());
		}			
	}
}
