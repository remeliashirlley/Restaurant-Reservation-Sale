package restaurant.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import restaurant.controller.MenuController;
import restaurant.controller.OrderController;
import restaurant.controller.PromoController;
import restaurant.controller.ReservationController;
import restaurant.model.Dessert;
import restaurant.model.Drink;
import restaurant.model.Item;
import restaurant.model.MainCourse;
import restaurant.model.Order;
import restaurant.model.SetPackage;
import restaurant.model.Staff;
/**
 * <p>Screen for user to manipulate with details in order.
 * @author Hakiim
 *
 */
public class OrderView {

	private OrderController orderController;
	private MenuController menuController;
	private PromoController promoController;
	private ReservationController reservationController;
	private Scanner scanner;
	private Staff staff;
	private Order order;
	String name;
	int tableNum;

	private static final String RESTAURANT_NAME = "TEAM 4";
	private static final String RESTAURANT_ADDRESS = "TEAM 4@Nanyang Cres";
	/**
	 * OrderView constructor
	 */
	public OrderView() {
		this.orderController = new OrderController();
		this.menuController = new MenuController();
		this.promoController = new PromoController();
		this.reservationController = new ReservationController();
		scanner = new Scanner(System.in);
		selectCDType();
	}
	/**
	 * Select the function related to the order by passing input form user into the switch case.
	 */
	private void selectCDType() {

		int choice = 0;

		while (choice != -1) {
			System.out.println("Select an option (no.)");
			System.out.println("1.Create Order\n2.Display Orders\n--Press any other number to return to the Main Menu--");
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				createOrder();
				break;
			case 2:
				displayOrder();
				break;
			default:
				choice = -1;
				break;
			}

			orderController.saveData();
		}
	}
	/**
	 * Display order details.
	 */
	private void displayOrder() {
		// System.out.println(RESTAURANT_NAME+"\n"+RESTAURANT_ADDRESS);
		
		if(!orderController.getOrders().isEmpty())
		{
			System.out.println("Enter order id");
			int orderId = scanner.nextInt();
	
			if (orderController.getOrderById(orderId) == null) {
				System.out.println("No such order by id: #" + orderId);
			} else {
				System.out.println(orderController.getOrderById(orderId).getItems());
			}
		}
		else {
			System.out.println("There are no orders.");
		}
	}
	/**
	 * Create a new order 
	 */
	private void createOrder() {

		if(!menuController.getMenuList().isEmpty())
		{
			int choice = 0;
			Boolean exists = false;
			System.out.println("Staff name:");
			name = scanner.next();
			scanner.nextLine();
			System.out.println("Table number: ");
			tableNum = scanner.nextInt();
			scanner.nextLine();
			if(!(tableNum>reservationController.getTableList().size()))
			{
				int orderIndex = orderController.checkOrderExists(tableNum);
				if (orderIndex == -1) {
					order = new Order(orderController.getTotalOrders() + 1);
					order.setId(orderController.getTotalOrders() + 1);
					order.setStaff(name);
					order.setTable(reservationController.getTableList().get(tableNum-1));
					order.setActive(true);
					System.out.println("\nCreating new order with ID #" + (orderController.getTotalOrders() + 1) + "\n");
				} else {
					exists = true;
					order = orderController.getOrderById(orderIndex);
					System.out.println("\nPrevious order with ID #" + (orderIndex) + " found!\n");
				}

				while (choice != -1) {
					System.out.println("Select an option (no.)");
					System.out.println("1.Add Items\n2.Update Order\n--Press any other number to return to the previous menu--");
					choice = scanner.nextInt();
					scanner.nextLine();

					switch (choice) {
					case 1:
						addItemToOrder();
						break;
					case 2:
						if(!orderController.getItemsInOrder(order).isEmpty()) updateOrder();
						else System.out.println("You have no items added to your current order!");
						break;
					default:
						choice = -1;
						break;
					}
				}

				if(!order.getItems().isEmpty()) {
					orderController.addOrderToOrderList(order, exists, orderIndex-1);
					orderController.saveData();
				}else System.out.println("Order Cancelled.");
			}else System.out.println("Invalid Table Number.");
		}else System.out.println("Menu is empty.");
			
		
	}
	/**
	 * Update the order
	 */
	private void updateOrder() {
		int choice = 0;


		while (choice != -1 && !orderController.getItemsInOrder(order).isEmpty()) {
			System.out.println("Select an option (no.)");
			System.out.println("1.Change item quantity\n2.Remove item from order\n--Press any other number to return to the previous menu--");
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1:
					changeItemQuantity();
					break;
				case 2:
					removeItemFromOrder();
					break;
				default:
					choice = -1;
					break;
			}

		}

	}
	/**
	 * Change quantity of the item in order
	 */
	private void changeItemQuantity(){
		Item selectedItem = null;
		int index = 0;
		int qty = 0;

		List<Item> lisItems = new ArrayList();
		for(Item item : orderController.getItemsInOrder(order).keySet()){
			lisItems.add(item);
		}
		
		System.out.println("Select an option (no.):");
		int i = 0;
		System.out.println("Index\tItem\tQuantity");
		for(Map.Entry<Item,Integer> entry: orderController.getItemsInOrder(order).entrySet()) {
			System.out.println((++i)+"\t"+entry.getKey().getName()+"\t"+entry.getValue());
		}
		

		System.out.println("Enter the index of the item to change");
		index = scanner.nextInt();
		scanner.nextLine();

		if(index>0 && index <=lisItems.size()) selectedItem = lisItems.get(index-1);

		if(selectedItem !=null && orderController.getItemsInOrder(order).containsKey(selectedItem))
		{
			System.out.println("Enter the new quantity");
			qty = scanner.nextInt();
			scanner.nextLine();

			orderController.updateItemQuantityInOrder(order, selectedItem, qty);
		} else System.out.println("No such item");
	}
	/**
	 * Remove item from order
	 */
	private void removeItemFromOrder(){
		Item selectedItem = null;
		int index = 0;

		List<Item> lisItems = new ArrayList();
		for(Item item : orderController.getItemsInOrder(order).keySet()){
			lisItems.add(item);
		}

		System.out.println("Select an option (no.):");
		int i = 0;
		System.out.println("Index\tItem\tQuantity");
		for(Map.Entry<Item,Integer> entry: orderController.getItemsInOrder(order).entrySet()) {
			System.out.println((++i)+"\t"+entry.getKey().getName()+"\t"+entry.getValue());
		}

		System.out.println("Enter the index of the item to remove");
		index = scanner.nextInt();
		scanner.nextLine();

		if(index>0 && index <=lisItems.size()) selectedItem = lisItems.get(index-1);

		if(selectedItem !=null && orderController.getItemsInOrder(order).containsKey(selectedItem))
		{
			orderController.removeItemFromOrder(order, selectedItem);
		}else System.out.println("No such item");
	}

	/**
	 * Make adjustments to selected items in order
	 * @param listOfItems
	 */
	private void navigateMenu(List<Item> listOfItems) {
		if(listOfItems.isEmpty())
		{
			System.out.println("No Items in category.");
			return;
		}
		List<Item> lisOptions = new ArrayList();
		int index = 0;
		for (Item item : listOfItems)
			System.out.println(++index + ". " + item.getName());

		System.out.println("Input choice:");
		int choice = scanner.nextInt();
		scanner.nextLine();
		Item selectedItem = null;

		if (choice <= listOfItems.size() && choice > 0) {
			selectedItem = listOfItems.get(choice - 1);
			lisOptions.add(selectedItem);
		}

		if (selectedItem != null) {
			List<Item> lisPromo = promoController.getMenu().getPromotionList();// System.out.println(promoController.getItemByName(selectedItem.getName()));
			for (Item promo : lisPromo) {
				List<Item> lis = ((SetPackage) promo).getPromotionalItemList();

				for (Item item : lis) {
					if (item.getName().equals(selectedItem.getName())) {
						lisOptions.add(promo);
						break;
					}
				}
			}

			int promoIndex = 0;
			for (Item promo : lisOptions)
				System.out.println(++promoIndex + ". " + promo);

			System.out.println("Input choice:");
			choice = scanner.nextInt();

			if(choice>0 && choice<= listOfItems.size()) {
				selectedItem = lisOptions.get(choice - 1);

				System.out.println("Input quantity:");
				int qty = scanner.nextInt();

				if (qty > 0) orderController.addItemToOrder(order, selectedItem, qty);
				else System.out.println("Your item was not added, please use a quantity higher than 0");
			}
			else System.out.println("No such option");
		}

	}
	/**
	 * Add item to order 
	 */
	private void addItemToOrder() {
		List<Item> lis = menuController.getMenu().getFoodItemList();
		List<Item> lisMC = new ArrayList();
		List<Item> lisDrinks = new ArrayList();
		List<Item> lisDesserts = new ArrayList();

		for (Item item : lis) {
			if (item instanceof MainCourse)
				lisMC.add(item);
			else if (item instanceof Drink)
				lisDrinks.add(item);
			else if (item instanceof Dessert)
				lisDesserts.add(item);
		}

		int choice = 0;

		while (choice != -1) {
			System.out.println("Select an option (no.)");
			System.out.println(
					"1.Main Course\n2.Drinks\n3.Dessert\n--Press any other number to return to the previous menu--");
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				navigateMenu(lisMC);
				break;
			case 2:
				navigateMenu(lisDrinks);
				break;
			case 3:
				navigateMenu(lisDesserts);
				break;
			default:
				choice = -1;
				break;
			}

		}

	}
}
