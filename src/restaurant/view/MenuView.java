package restaurant.view;

import java.util.Scanner;

import restaurant.controller.MenuController;
import restaurant.model.Dessert;
import restaurant.model.Drink;
import restaurant.model.Item;
import restaurant.model.MainCourse;
/**
 * <p>Screen for user to manipulate with items in menu.
 * @author Muhaimin
 *
 */
public class MenuView {

	private Scanner scanner;
	private MenuController menuController;
	/**
	 * MenuView constructor
	 */
	public MenuView() {
		this.menuController = new MenuController();
		scanner = new Scanner(System.in);
		selectCRUDType();
	}
	/**
	 * Select the function related to the menu by passing input form user into the switch case.
	 */
	private void selectCRUDType() {
		int choice = 0;
		while(choice != -1) {
			System.out.println("Select an option:");
			System.out.println("1. Create menu item\n2. Update menu item\n3. Remove menu item\n4. Display menu items\n-- Press any other number to return to the Main Menu --");
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				createMenuItem();
				break;
			case 2:
				updateMenuItem();
				break;
			case 3:
				removeMenuItem();
				break;
			case 4:
				displayMenuItems();
				break;
			default:
				choice = -1;
				break;
			}
			menuController.saveData();
		}
	}
	/**
	 * User selects the item type.
	 */
	private int selectItemType() {
		System.out.println("Select (no.) desired category by index no.");
		System.out.println("1. Main Course\n2. Drinks\n3. Desserts\n-- Press any other number to return to the previous menu --");
		int choice = scanner.nextInt();
		scanner.nextLine();
		return choice;
	}
	/**
	 * Create a menu item selected by the user.
	 */
	private void createMenuItem() {
		int choice = selectItemType();
		Item item;
		String selection = "";

		while (choice == 1 || choice == 2 || choice == 3) {

			if (choice == 1) {
				selection = "Main Course";
				item = new MainCourse();
			} else if (choice == 2) {
				selection = "Drink";
				item = new Drink();
			} else {
				selection = "Dessert";
				item = new Dessert();
			}

			System.out.println("Enter name of " + selection);
			String name = scanner.nextLine();
			
			if (menuController.getItemByName(name) == null) {
				System.out.println("Enter description of " + name);
				String description = scanner.nextLine();
				System.out.println("Enter price of " + name);
				float price = scanner.nextFloat();

				item.setName(name);
				item.setDescription(description);
				item.setPrice(price);
				menuController.createMenuItem(item);
			} else {
				System.out.println("Item with such name already exists.");
			}
			choice = selectItemType();
		}
	}
	/**
	 * Update menu in it selected by the user.
	 */
	private void updateMenuItem() {
		System.out.println("Enter name of item to start updating");
		String name = scanner.nextLine();
		Item item = menuController.getItemByName(name);

		if (item != null) {

			System.out.println(item.getName() + " found");
			System.out.println("Select (no.) desired detail to update");
			System.out.println("\n1. Name\n2. Description\n3. Price\n-- Press any other number to return to the previous menu --");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				System.out.println("Enter new name");
				String newName = scanner.nextLine();
				if (menuController.getItemByName(newName) == null) {
					menuController.updateItemNameByName(item, newName);
					System.out.println("Name changed successfully.");
				} else {
					System.out.println("Item with such name already exists.");
				}
				break;
			case 2:
				System.out.println("Enter new description");
				String newDescription = scanner.nextLine();
				menuController.updateItemDescriptionByName(item, newDescription);
				System.out.println("Description changed successfully.");
				break;
			case 3:
				System.out.println("Enter new price");
				float newPrice = scanner.nextFloat();
				menuController.updateItemPriceByName(item, newPrice);
				System.out.println("Price changed successfully.");
				break;
			}
		} else {
			System.out.println("No such item.");
		}
	}
	/**
	 * Remove menu item from list of items in menu. 
	 */
	private void removeMenuItem() {
		if(!menuController.getMenuList().isEmpty())
		{
			System.out.println("Enter name of item to delete");
			String name = scanner.nextLine();
			Item item = menuController.getItemByName(name);
		
			if (item != null) {
			menuController.deleteItemByName(item);
			System.out.println(item.getName() + " successfully deleted.");
			} else {
				System.out.println("No such item.");
			}
		}
		else System.out.println("Menu is empty.");
	}
	/**
	 * Display menu items.
	 */
	private void displayMenuItems() {
		System.out.println("Menu:");
		menuController.getMenu().getFoodItemList().forEach(foodItem -> System.out.println(foodItem));
		//menuController.getMenu().getPromotionList().forEach(promotions -> System.out.println(promotions));
	}
}
