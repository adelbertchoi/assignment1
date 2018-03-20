
public class Adult extends User {
	
	private Child dependent; // extend this to arraylist
	private Adult partner;
	private boolean hasChild;
	// private boolean hasPartner;
	
	// constructors
	public Adult(String username) { super(username); }
	
	public Child getDependent() { return this.dependent; }
	public Adult getPartner() { return this.partner; }
	
	public void setDepedent(Child dependent, Adult partner) {
		if ( !hasChild ) {
			this.dependent = dependent;
			this.setPartner(partner);
			hasChild = true;
		}
	}
	
	public void setPartner(Adult partner) {
		this.partner = partner;
		// hasPartner = true;
	}
	
	// method to add a network for an adult profile
	@Override
	public boolean addFriend(User friend) {
		
		if ( !(friend instanceof Adult) ) {
			System.out.print("\n\t *** Adults type users cannot network with Children type users");
			return false;
		}
		
		for (int i=0; i<super.getFriends().size(); i++) {
			if (super.getFriends().get(i).getUsername() == friend.getUsername()) {
				System.out.print("\n\t *** " + super.getUsername() + " and " + friend.getUsername() + " are already friends");
				return false; 
			}
		}
		
		super.addToFriends(friend);
		friend.addToFriends(this);
		System.out.print("\n\t ***" + getUsername() + " and " + friend.getUsername() + " are now friends");
		return true;
	}
	
	public void printProfile() {
		System.out.println("\n\t ========= Adult User Profile ====== ");
		System.out.printf("\t\t %-10s :   %-15s", "Username", super.getUsername());
		
		if ( super.getAge() != 0 )
			System.out.printf("\n\t\t %-10s :   %-15s", "Age", super.getAge());
		
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
