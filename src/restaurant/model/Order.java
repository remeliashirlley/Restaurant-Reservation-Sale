package restaurant.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> Model for Order object 
 * <p> Inherits {@link AbstractModel}
 * @author Hakiim
 *
 */
public class Order extends AbstractModel{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Table table;
	private String staff;
	private Boolean active;
	private Boolean hasMembership;
	private LocalDateTime time;
	private HashMap<Item,Integer> itemAndQuantity = new HashMap<Item,Integer>();
	/**
	 * Order constructor
	 * 
	 */
	public Order() {
		this.hasMembership = false;
		this.active=true;
	}
	/**
	 * Order constructor
	 * @param id
	 */
	public Order(int id) {
		this.id = id;
	}
	/**
	 * Get id for order
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Set id for order
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Get table for order
	 * @return table
	 */
	public Table getTable() {
		return table;
	}
	/**
	 * Set table for order
	 * @param table
	 */
	public void setTable(Table table) {
		this.table = table;
	}
	/**
	 * Get staff for order
	 * @return staff
	 */
	public String getStaff() {
		return staff;
	}
	/**
	 * Set staff for order
	 * @param staff
	 */
	public void setStaff(String staff) {
		this.staff = staff;
	}
	/**
	 * Set order's status
	 * @param active
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	/**
	 * Get order's status
	 * @param active
	 */
	public Boolean getActive() {
		return active;
	}
	/**
	 * Set time of order
	 * @param time
	 */
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	/**
	 * Get time of order
	 * @return time
	 */
	public LocalDateTime getTime() {
		return time;
	}
	/**
	 * Get item and quantity
	 * @return itemAndQuantity
	 */
	public Map<Item, Integer> getItems() {
		return itemAndQuantity;
	}
	//private Menu menu;
	 //list of items ordered
	/**
	 * Get membership status 
	 * @return hasMembership
	 */
	public Boolean getHasMembership() {
		return hasMembership;
	}
	/**
	 * Set membership status 
	 * 
	 */
	public void setHasMembership(Boolean hasMembership) {
		this.hasMembership = hasMembership;
	}
	

}
