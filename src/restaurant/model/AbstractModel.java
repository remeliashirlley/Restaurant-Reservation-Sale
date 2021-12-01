package restaurant.model;

import java.io.Serializable;
/**
 * <p> Model for AbstractModel object 
 * <p> Implements interface {@link Serializable}
 * @author Muhaimin
 *
 */
public abstract class AbstractModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Create a string to print out object information
	 */
	@Override
	public String toString() {
		return getClass() + "[" + super.toString() + "]";
	}
	
	

}
