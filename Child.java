
/*     
 * ====== Child.java
 * This class is a subclass of the User abstract class it implements the 
 * abstract method addFriends, printProfile, and editAge. This subclass 
 * has new additional instance variables parentOne and parentTwo. These 
 * variables will keep track of the parent users for child objects instantiated. 
 * 
 * */

public class Child extends User {

	// Child instance variables
	// parents are need to be given values in instantiation
	private Adult parentOne;
	private Adult parentTwo;
	
	// constructor
	public Child(String username, int age, Adult parentOne, Adult parentTwo) {
		super(username, age);
		this.parentOne = parentOne;
		this.parentTwo = parentTwo;
		parentOne.addDepedent(this); 
		parentTwo.addDepedent(this);
		parentOne.setPartner(parentTwo);
		parentTwo.setPartner(parentOne);
	}
	
	// getters - to obtain child class instance variables
	public Adult getParentOne() { return this.parentOne; }
	public Adult getParentTwo() { return this.parentTwo; }
	
	public void editAge(int newAge) {
		// condition for editing an adult user's age
		if (newAge > 2 || newAge < 17) {
			super.setAge(newAge);
		} else System.out.print("\n\t *** Age of child user must be between 3 and 16");
	}
	
	// Overridden method
	// method to add a network for an child profile
	public boolean addFriend(User friend) {
		// three conditions need to be met before a friend can be added to a child
		// if any one of the three is not met, friend will not be added to child list of friends
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

	// Overridden method
	// print the details of the child users profile - showing the instance variable values
	public void printProfile() {
		System.out.println("\n\t ========= Child User Profile ====== ");
		System.out.printf("\t\t %-10s :   %-15s", "Username", super.getUsername());
		
		if ( !super.getFirstName().isEmpty() )
			System.out.printf("\n\t\t %-10s :   %s %s", "Full Name", super.getFirstName(), super.getLastName());
		
		System.out.printf("\n\t\t %-10s :   %-15s", "Age", super.getAge());
		
		// the printing of certain details of the child user depends
		// on the presence of values of the instance variables
		
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
