package restaurant.model;
/**
 * <p> Model for Staff object 
 * <p> Inherits {@link AbstractModel}
 * @author CheenHao
 *
 */

public class Staff extends AbstractModel{

	private static final long serialVersionUID = 1L;
	private String name;
	private boolean gender;
	private String employeeId;
	private String title;

	/**
	 * Get name for Staff
	 * @return name
	 */	
	public String getName() {
		return name;
	}

	/**
	 * Set name for Staff
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get gender for Staff
	 * @return gender
	 */
	public boolean isGender() {
		return gender;
	}

	/**
	 * Set gender for Staff
	 * @param gender
	 */
	public void setGender(boolean gender) {
		this.gender = gender;
	}

	/**
	 * Get employee ID for Staff
	 * @return employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * Set employee ID for Staff
	 * @param employeeId
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * Get title for Staff
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title for Staff
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
}
