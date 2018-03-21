
public class Adult extends User {
	
	// initial values
	private Child dependent = null; 
	private Adult partner = null;
	
	// constructors
	public Adult(String username) { super(username); }
	
	public Child getDependent() { return this.dependent; }
	public Adult getPartner() { return this.partner; }
	
	public void setDepedent(Child dependent) { this.dependent = dependent; }
	public void setPartner(Adult partner) { this.partner = partner; }
	
	public void editAge(int newAge) {
		if (newAge > 16) 
			super.setAge(newAge);
		else System.out.print("\n\t *** Age of an adult user must be at least 17. Age not changed");
	}
	
	// method to add a network for an adult profile
	@Override
	public boolean addFriend(User friend) {
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
		System.out.print("\n\t *** " + getUsername() + " and " + friend.getUsername() + " are now friends");
		return true;
	}
	
	public boolean deleteFriend(User friend) {
		// you can only delete friends who are not your partner, in instances where you have a child
		if ( (getDependent() != null) && (partner.getUsername().equals(friend.getUsername())) ) {
			System.out.print("\n\t *** Cannot delete friend since " + getUsername() + " and " + friend.getUsername() + " are partners with a dependent");
			return false;
		}
		
		for (int i=0; i< super.getFriends().size(); i++) {
			if ( super.getFriends().contains(friend) ) {
				super.removeFromFriends(friend);
				friend.removeFromFriends(this);
				System.out.print("\n\t *** " + getUsername() + " and " + friend.getUsername() + " are not friends anymore");
				
				if ( partner.getUsername().equals(friend.getUsername()) ) {
					this.setPartner(null);
					((Adult) friend).setPartner(null);
				}
				
				return true;
			}
		}
		System.out.print("\n\t *** " + getUsername() + " and " + friend.getUsername() + " are not friends in the first place");
		return false;
	}
	
	public void printProfile() {
		System.out.println("\n\t ========= Adult User Profile ====== ");
		System.out.printf("\t\t %-10s :   %-15s", "Username", super.getUsername());
		
		if ( super.getAge() != 0 )
			System.out.printf("\n\t\t %-10s :   %-15s", "Age", super.getAge());
		
		if ( !(super.getImage().isEmpty()) )
			System.out.printf("\n\t\t %-10s :   %-15s", "Image", super.getImage());
		
		if ( !(super.getStatus().isEmpty()) )
			System.out.printf("\n\t\t %-10s :   %-15s", "Status", super.getStatus());
		
		if ( partner != null ) 
			System.out.printf("\n\t\t %-10s :   %-15s", "Partner", partner.getUsername());
		
		if ( dependent != null ) 
			System.out.printf("\n\t\t %-10s :   %-15s", "Dependent", dependent.getUsername());
		
		if( !super.getFriends().isEmpty() ) {
			System.out.printf("\n\t\t %-10s : ", "Network");
			for(int i=0; i<super.getFriends().size(); i++)
				System.out.printf("\n\t\t   %-3d %-10s", (i+1), super.getFriends().get(i).getUsername());
		}			
	}
	
}
