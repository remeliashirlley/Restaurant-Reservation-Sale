package restaurant.model;
/**
 * <p> Model for Customter object 
 * @author CheenHao
 *
 */
public class Customer {
	
	private Order order;
	private Reservation reservation;
	private boolean hasMembership;

	protected static int noOfCustomers=0;
	/**
	 * Add number of cumstomer by 1
	 */
	public Customer() {
		noOfCustomers++;
	}

	

}
