package restaurant.view;

import java.util.Scanner;

import restaurant.controller.PromoController;
import restaurant.model.Item;
import restaurant.model.SetPackage;
/**
 * <p>Screen for user to manipulate with items in promo.
 * @author Muhaimin, Hakiim
 *
 */
public class PromoView {
	private Scanner scanner;
	private PromoController promoController;
	/**
	 * PromoView constructor
	 */
	public PromoView() {
		this.promoController = new PromoController();
		scanner = new Scanner(System.in);
		selectCRUDType();
	}
	/**
	 * Select the function related to the promotion by passing input by user into the switch case.
	 */
	private void selectCRUDType() {
		int choice = 0;
		
		while (choice != -1) {
			System.out.println("Select an option (no.):");
			System.out.println("1. Create promotion\n2. Update promotion\n3. Remove promotion\n4. Display promotion\n-- Press any other number to return to the Main Menu --");
			choice = scanner.nextInt();
			scanner.nextLine();
	
			switch (choice) {
			case 1:
				createPromo();
				break;
			case 2:
				updatePromo();
				break;
			case 3:
				removePromo();
				break;
			case 4:
				displayPromo();
				break;
			default:
				choice=-1;
				break;
			}
			promoController.saveData();
		}
	}
	/**
	 * Create a promotion based on the items on the menu selected by the user
	 */
	private void createPromo() {
		if(!promoController.getMenuList().isEmpty())
		{
			int selection = 1;
			SetPackage promotion = new SetPackage();

			System.out.println("Enter name of promo");
			String name = scanner.nextLine();
			
			if((SetPackage) promoController.getPromoByName(name) == null) {
				System.out.println("Enter description of " + name);
				String description = scanner.nextLine();
				System.out.println("Enter price of " + name);
				float price = scanner.nextFloat();
				scanner.nextLine();

				promotion.setName(name);
				promotion.setDescription(description);
				promotion.setPrice(price);

				while (selection == 1) {
					System.out.println("Enter name of item to add into promotion, " + name);
					String itemName = scanner.nextLine();
					Item promoItem = promoController.getItemByName(itemName);
					if (promoItem != null) {
						promoController.createAddPromoItem(promotion, promoItem);
						System.out.println("Press 1 to add more items");
						selection = scanner.nextInt();
						scanner.nextLine();
					} else {
						System.out.println("Menu item does not exist.");
					}
				}
				promoController.createPromoItem(promotion);
				} else {
					System.out.println("Promo item with such name already exists.");
				}
		} else System.out.println("Menu is empty.");
		
	}
	/**
	 * Update promotion and any items in it selected by the user.
	 */
	private void updatePromo() {
		System.out.println("Enter promo name to start updating");
		String promoName = scanner.nextLine();
		int loop = 1;

		SetPackage promotion = (SetPackage) promoController.getPromoByName(promoName);

		if (promotion != null) {

			System.out.println(promotion.getName() + " found");
			System.out.println("Select an option (no.):");
			System.out.println("\n1. Name\n2. Description\n3. Price\n4. Items in promotion");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				System.out.println("Enter new name");
				String newName = scanner.nextLine();
				if ((SetPackage) promoController.getPromoByName(newName) == null) {
					promoController.updateItemNameByName(promotion, newName);
					System.out.println("Name changed successfully");
				} else {
					System.out.println("Promo item with such name already exists.");
				}
				break;
			case 2:
				System.out.println("Enter new description");
				String newDescription = scanner.nextLine();
				promoController.updateItemDescriptionByName(promotion, newDescription);
				System.out.println("Description changed successfully");
				break;
			case 3:
				System.out.println("Enter new price");
				float newPrice = scanner.nextFloat();
				promoController.updateItemPriceByName(promotion, newPrice);
				System.out.println("Price changed successfully");
				break;
			case 4:
				System.out.println("Select an option (no.):");
				System.out.println("1. Add item\n2. Remove item");
				int selection = scanner.nextInt();
				scanner.nextLine();
				if (selection == 1) {
					while (loop == 1) {
						System.out.println("Enter name of item to add into promotion, " + promoName);
						String itemName = scanner.nextLine();
						Item promoItem = promoController.getItemByName(itemName);
						if (promoItem != null) {
							promoController.updateAddPromoItem(promotion, promoItem);
							System.out.println("Press 1 to add more items");
							loop = scanner.nextInt();
						scanner.nextLine();
						} else {
							System.out.println("Menu item does not exist.");
						}
					}
				} else if (selection == 2) {
					System.out.println("Enter name of item in promotion, " + promoName + ", to delete");
					String promoItemName = scanner.nextLine();
					Item promoItem = promoController.getItemByName(promoItemName);
					if (promoItem != null) {
						promoController.updateRemovePromoItem(promotion, promoItem);
						System.out.println(promoName + " successfully deleted.");
					} else {
						System.out.println("Menu item does not exist.");
					}
				}
				System.out.println("Item in promotion updated!");
				break;
			}
		}
	}
	/**
	 * Remove promotion from the list of promotions. 
	 */
	private void removePromo() {
		System.out.println("Enter name of item to delete");
		String name = scanner.nextLine();
		SetPackage promo = (SetPackage) promoController.getPromoByName(name);
		if(promo != null) {
			promoController.deletePromoByName(promo);
			System.out.println(name + " successfully deleted");
			} else {
				System.out.println("No such item.");
			}
	}
	/**
	 * Display the promotions from menu.
	 */
	private void displayPromo() {
		System.out.println("Menu:");
		promoController.getMenu().getPromotionList().forEach(item -> System.out.println(item));
	}
}
