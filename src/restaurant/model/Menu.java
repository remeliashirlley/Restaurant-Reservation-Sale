package restaurant.model;


import java.util.ArrayList;
import java.util.List;
/**
 * <p> Model for menu object 
 * <p> Inherits {@link AbstractModel}
 * @author Muhaimin
 *
 */
public class Menu extends AbstractModel {
	
	private static final long serialVersionUID = 1L;
	
	private List<Item> foodItemList;
	private List<Item> promotionList;
	/**
	 * Menu constructor
	 */
	public Menu() {
		this.foodItemList = new ArrayList<>();
		this.promotionList = new ArrayList<>();
	}
	/**
	 * Get list of promotions 
	 * @return promotionList
	 */
	public List<Item> getPromotionList() {
		return promotionList;
	}
	/**
	 * Set list of promotions based on passed promos parameter.
	 * @param promos
	 */
	public void setPromotionList(List<Item> promos) {
		this.promotionList = promos;
	}
	/**
	 * Get list of food in menu 
	 * @return foodItemList
	 */
	public List<Item> getFoodItemList() {
		return foodItemList;
	}
	/**
	 * Set list of food in menu
	 * @param items
	 */
	public void setFoodItemList(List<Item> items) {
		this.foodItemList = items;
	}
	
}
