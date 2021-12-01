package restaurant.model;

import java.util.ArrayList;
import java.util.List;
/**
 * <p> Model for SetPackage object 
 * <p> Inherits {@link Item}
 * @author Muhaimin
 *
 */
public class SetPackage extends Item {
	
	private static final long serialVersionUID = 1L;
	
	private List<Item> promotionalItemList;
	/**
	 * Set package in menu
	 */
	public SetPackage() {
		this.promotionalItemList = new ArrayList<>();
	}
	/**
	 * Get promotion from the list of promotions
	 * @return promotionalItemList
	 */
	public List<Item> getPromotionalItemList() {
		return promotionalItemList;
	}
	/**
	 * Set promotion from the list of promotions 
	 * @param items
	 */
	public void setPromotionalItemList(List<Item> items) {
		this.promotionalItemList = items;
	}
	/**
	 * Create a string to print out promotion information
	 */
	@Override
	public String toString() {
		return super.toString() + "\tItems:" + promotionalItemList.toString() ;
	}

}
