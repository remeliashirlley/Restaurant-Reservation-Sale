package restaurant.controller;

import java.util.ArrayList;
import java.util.List;

import restaurant.model.Item;
import restaurant.model.Menu;
import restaurant.model.SetPackage;

/**
 * <p>Business logic pertaining to {@link Menu}. It involves the create, read, update and delete functionalities.
 * <p>Inherits {@link AbstractController} and passes {@link Menu} as the object type for IO operations.
 * 
 * @author Muhaimin
 *
 */
public class MenuController extends AbstractController<Menu> {
	/**
	 * The model type used for data manipulation 
	 */
	private Menu menu;

	private static final String MENU_DIRECTORY = "src\\restaurant\\io\\menu.io";
	/**
	 * If data from file is not retrieved successfully, create new instance of {@link Menu}
	 */
	public MenuController() {
		menu = loadReadIOFile(MENU_DIRECTORY);
		
		if (menu == null) {
			menu = new Menu();
		}
	}
	/**
	 * Function to get menu
	 * @return menu
	 */
	public Menu getMenu() {
		return menu;
	}
	/**
	 * Function to get item by {@code itemName}. Loops item list in {@link Menu} and 
	 * checks each item name with the parameter {@code itemName} and returns that item if matched,
	 * not case sensitive and null if no matches 
	 * 
	 * @param itemName
	 * @return item 
	 */
	public Item getItemByName(String itemName) {
		for (Item item : menu.getFoodItemList()) {
			if (itemName.equalsIgnoreCase(item.getName()) == true) {
				return item;
			}
		}
		return null;
	}
	/**
	 * Get menu list
	 * @return food item list
	 */
	public List<Item> getMenuList()
	{
		return menu.getFoodItemList();
	}
	/**
	 * Add an item on the item list.
	 * @param item
	 */
	public void createMenuItem(Item item) {
		menu.getFoodItemList().add(item);
	}
	/**
	 * Update the item name on passed item parameter. 
	 * @param item
	 * @param newName
	 */
	public void updateItemNameByName(Item item, String newName) {
		item.setName(newName);
	}
	/**
	 * Update description of item on passed item parameter.
	 * @param item
	 * @param newDescription
	 */
	public void updateItemDescriptionByName(Item item, String newDescription) {
		item.setDescription(newDescription);
	}
	/**
	 * Update price of item on passed item parameter.
	 * @param item
	 * @param newPrice
	 */
	public void updateItemPriceByName(Item item, float newPrice) {
		item.setPrice(newPrice);
	}
	/**
	 * Delete item on passed item parameter.
	 * @param item
	 */
	public void deleteItemByName(Item item) {
		menu.getFoodItemList().remove(item);
		
		List<Item> lisPromo = new ArrayList<Item>();// System.out.println(promoController.getItemByName(selectedItem.getName()));
		
		for (Item promo : menu.getPromotionList()) {
			List<Item> lis = ((SetPackage) promo).getPromotionalItemList();

			for (Item itemInPromo : lis) {
				if (itemInPromo.getName().equals(item.getName())) {
					lisPromo.add(promo);
					break;
				}
			}
		}
		
		for (Item promoToDelete : lisPromo) menu.getPromotionList().remove(promoToDelete);
		
	}
	/**
	 * Save the adjusted data on the IO file.
	 */
	public void saveData() {
		writeSaveIOFile(MENU_DIRECTORY, menu);
	}
}
