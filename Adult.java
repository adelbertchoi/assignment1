
/*        
 * ====== Adult.java
 * This class is a subclass of the User abstract class it implements the 
 * abstract method addFriends, printProfile, and editAge. This subclass 
 * has new additional instance variables dependent and partner. These 
 * variables will keep track of the partner and child of this object.These 
 * instance variables are only used when specified
 * 
 * */

import java.util.ArrayList;

public class Adult extends User {
	
	// Adult instance variables
	// dependent and partner are set to null initially
	private ArrayList<Child> dependents = new ArrayList<Child>(); 
	private Adult partner = null;
	
	// Adult class constructor
	public Adult(String username) { super(username); }
	
	
	// getters - to obtain adult class instance variables
	public ArrayList<Child> getDependents() { return this.dependents; }
	public Adult getPartner() { return this.partner; }
	
	// this method sets the dependent of this adult user
	public void addDepedent(Child dependent) {
		dependents.add(dependent);
	}
	
	// this method removes a dependent of this adult user
	public void removeDepedent(Child dependent) {
		for (int i=0; i<dependents.size(); i++) 
			if (dependents.get(i).getUsername().equals(dependent.getUsername()))
				dependents.remove(i);
	}
	
	// this method sets the partner of the adult user
	// Used in situations when a dependent is being set
	public void setPartner(Adult partner) {
		// set partner
		this.partner = partner; 
		
		// is partner parameter passed is null no need to continue the function
		if ( partner == null ) 
			return;
		
		// if partner parameter passed is already amongst the friends of user
		// no need to continue the function
		if ( super.getFriends().contains(partner) )
			return;
		
		// add partner parameter to friends list, also to partner friends lists
		super.addToFriends(partner);
		partner.addToFriends(this);
	}
	
	public void editAge(int newAge) {
		// condition for editing an adult user's age
		if (newAge > 16) 
			super.setAge(newAge);
		else System.out.print("\n\t *** Age of an adult user must be at least 17. Age not changed");
	}
	
	// Overridden method
	// method to add a network for an adult profile
	public boolean addFriend(User friend) {
		// two conditions need to be met before a friend can be added to a child
		// if any one of the two is not met, friend will not be added to child list of friends
		if ( !(friend instanceof Adult) ) {
			System.out.print("\n\t *** Adults type users cannot network with Children type users");
			return false;
		}
		
		if ( super.getFriends().contains(friend) ) {
			System.out.print("\n\t *** " + super.getUsername() + " and " + friend.getUsername() + " are already friends");
			return false;
		}
		 
		super.addToFriends(friend);
		friend.addToFriends(this);
		return true;
	}
	
	// overridden method
	// function to delete a friend in adult user's friends list
	public boolean deleteFriend(User friend) {
		// you can only delete friends who are not your partner, in instances where you have a child
		// Overall, will allow a friend to be deleted if they don't have a dependent together
		// this is because the dependent depends on both parent users
		if ( !dependents.isEmpty() && (partner.getUsername().equals(friend.getUsername())) ) {
			System.out.print("\n\t *** Cannot delete friend since " + getUsername() + " and " + friend.getUsername() + " are partners of a dependent");
			return false;
		}
		
		// this section finds the friend to delete and deletes them from the 
		// user's list of friends
		for (int i=0; i< super.getFriends().size(); i++) {
			if ( super.getFriends().contains(friend) ) {
				super.removeFromFriends(friend);
				friend.removeFromFriends(this);
				System.out.print("\n\t *** " + getUsername() + " and " + friend.getUsername() + " are not friends anymore");
				
				// also if friend that will be deleted is the user's partner.
				// need to set the partner of the user to null, since the friend will be deleted
				if ( partner != null ) {
					if ( partner.getUsername().equals(friend.getUsername()) ) {
						this.setPartner(null);
						((Adult) friend).setPartner(null);
					}
				}
				
			return true;
			}
		}
		
		System.out.print("\n\t *** " + getUsername() + " and " + friend.getUsername() + " are not friends in the first place");
		return false;
	}
	
	// overridden method
	// print the details of the adult users profile - showing the instance variable values
	public void printProfile() {
		System.out.println("\n\t ========= Adult User Profile ====== ");
		System.out.printf("\t\t %-10s :   %-15s", "Username", super.getUsername());
		
		// printing of certain details of the adult user depends
		// on the presence of values of the instance variables
	
		if ( !super.getFirstName().isEmpty() )
			System.out.printf("\n\t\t %-10s :   %s %s", "Full Name", super.getFirstName(), super.getLastName());
		
		if ( super.getAge() != 0 )
			System.out.printf("\n\t\t %-10s :   %-15s", "Age", super.getAge());
		
		if ( !(super.getImage().isEmpty()) )
			System.out.printf("\n\t\t %-10s :   %-15s", "Image", super.getImage());
		
		if ( !(super.getStatus().isEmpty()) )
			System.out.printf("\n\t\t %-10s :   %-15s", "Status", super.getStatus());
		
		if ( partner != null ) 
			System.out.printf("\n\t\t %-10s :   %-15s", "Partner", partner.getUsername());
		
		if ( !dependents.isEmpty() ) {
			System.out.printf("\n\t\t %-10s : ", "Dependents");
			for(int i=0; i<dependents.size(); i++)
			System.out.printf("\n\t\t   %-3d %-10s", (i+1), dependents.get(i).getUsername());
		}		
		
		if( !super.getFriends().isEmpty() ) {
			System.out.printf("\n\t\t %-10s : ", "Network");
			for(int i=0; i<super.getFriends().size(); i++)
				System.out.printf("\n\t\t   %-3d %-10s", (i+1), super.getFriends().get(i).getUsername());
		}			
	}
	
}
