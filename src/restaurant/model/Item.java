package restaurant.model;


import java.text.DecimalFormat;
/**
 * <p> Model for Item object 
 * <p> Inherits {@link AbstractModel}
 * @author Muhaimin
 *
 */
public abstract class Item extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private float price;
	/**
	 * Get name of item
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set name of item
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Get description of item
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set description of item 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Get price of item
	 * @return
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * Set price of item
	 * @param price
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	/**
	 * Create a string to print out item information
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + this.name + ", " + this.description + ", $" + new DecimalFormat("0.00").format(price) + "]";
	}

}
