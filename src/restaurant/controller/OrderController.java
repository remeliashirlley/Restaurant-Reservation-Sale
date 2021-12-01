package restaurant.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import restaurant.model.Item;
import restaurant.model.Order;
/**
 * <p>Business logic pertaining to {@link Order}. It involves the create, read, update and delete functionalities.
 * <p>Inherits {@link AbstractController} and passes {@link Order} as the object type for IO operations.
 * @author Hakiim, CheenHao
 *
 */
public class OrderController extends AbstractController<List<Order>>{
	/**
	 * The model type used for data manipulation 
	 */
	private List<Order> orderList;
	
	private static final String ORDER_DIRECTORY = "src\\restaurant\\io\\order.io";
	/**
	 * If data from file is not retrieved successfully, create new instance of {@link Order}.
	 */
	public OrderController() {
		orderList = loadReadIOFile(ORDER_DIRECTORY);
		if (orderList == null) {
			orderList = new ArrayList<>();
		}
	}
	/**
	 * Check if the order exists based on the passed table parameter.
	 * @param table
	 * @return True or False
	 */
	public int checkOrderExists(int tableNum) {
		//int index = 0;
		for (Order order : orderList) {
			if (order.getTable().getTableNumber() == tableNum) {
				if (order.getActive() == true) {
					return order.getId();
				}
			}
		}
		return -1;
	}
	/**
	 * Get order based on the passed id parameter.
	 * @param id
	 * @return order
	 */
	public Order getOrderById(int id) {
		for(Order order : orderList) {
			if (order.getId() == id) {
				return order;
			}
		}
		return null;
	}
	/**
	 * Get list of orders. 
	 * @return
	 */
	public List<Order> getOrders() {
		return orderList;
	}
	/**
	 * Get items in order based on passed order parameter. 
	 * @param order
	 * @return order items
	 */
	public Map<Item, Integer> getItemsInOrder(Order order) {
		return order.getItems();
	}
	/**
	 * Get total number of orders. 
	 * @return size of order list
	 */
	public int getTotalOrders() {
		return orderList.size();
	}
	/**
	 * Get total price of order based on passed order parameter
	 * @param order
	 * @return totalPrice
	 */
	public float getTotalPrice(Order order) {
		float totalPrice = 0;
		for (var orderVar : order.getItems().entrySet()) {
			totalPrice += orderVar.getKey().getPrice() * orderVar.getValue();
		}
		return totalPrice;
	}
	/**
	 * Get total service charge for order based on passed totalPrice and membershipPrice parameters.
	 * @param totalPrice
	 * @param membershipPrice
	 * @return calculated service charge
	 */
	public float getServiceCharge(float totalPrice, float membershipPrice) {
		return ((totalPrice - membershipPrice) / 100) * 10;
	}
	/**
	 * Get total GST price based on passed totalPrice, membershipPrice and serviceCharge parameters.
	 * @param totalPrice
	 * @param membershipPrice
	 * @param serviceCharge
	 * @return calculated GST price
	 */
	public float getGSTPrice(float totalPrice, float membershipPrice, float serviceCharge) {
		return (((totalPrice - membershipPrice) + serviceCharge) / 100) * 7;
	}
	/**
	 * Get total price for invoice based on passed totalPrice, gstPrice, membershipPrice and serviceCharge parameters.
	 * @param totalPrice
	 * @param gstPrice
	 * @param membershipPrice
	 * @param serviceCharge
	 * @return calculated total price
	 */
	public float getSubTotalPrice(float totalPrice, float gstPrice, float membershipPrice, float serviceCharge) {
		return totalPrice + gstPrice + serviceCharge - membershipPrice;
	}
	/**
	 * Get membership discount based on passed totalPrice and membershipDiscountPercentage parameters.
	 * @param totalPrice
	 * @param membershipDiscountPercentage
	 * @return calculated membership discount
	 */
	public float getMembershipDiscount(float totalPrice, int membershipDiscountPercentage) {
		return (totalPrice / 100) * membershipDiscountPercentage;
	}
	/**
	 * Get length of longest string of order based on passed order parameter.
	 * @param order
	 * @return length
	 */
	public int getLongestString(Order order) {
		int length = 0;
        for (var orderVar : order.getItems().entrySet()) {
            if (orderVar.getKey().getName().length() > length) {                   
				length = orderVar.getKey().getName().length();
            }
        }
		return length;
	}
	/**
	 * Get length of longest string for sales invoice based on passed itemList parameter.
	 * @param itemList
	 * @return length
	 */
	public int getLongestStringSalesInvoice(List<String> itemList) {
		int length = 0;
		for (String item : itemList) {
			if (item.split(",")[0].length() > length) {
				length = item.split(",")[0].length();
			}
		}
		return length;
	}
	/**
	 * Get padding length of string based on passed longestString and stringLength parameters.
	 * @param longestString
	 * @param stringLength
	 * @return length of padding
	 */
	public int getPaddingLength(int longestString, int stringLength) {
		return (longestString - stringLength) + 12;
	}
	/**
	 * Get membership padding based on passed membershipDiscountPercentage parameter.
	 * @param membershipDiscountPercentage
	 * @return length of padding
	 */
	public int getMembershipPadding(int membershipDiscountPercentage) {
		if (String.valueOf(membershipDiscountPercentage).length() == 1) {
			return 3;
		} else if (String.valueOf(membershipDiscountPercentage).length() == 2) { 
			return 3;
		} else {
			return 2;
		}
	}
	/**
	 * Add item to order based on passed order, item and qty parameters.
	 * @param order
	 * @param item
	 * @param qty
	 */
	public void addItemToOrder(Order order, Item item, int qty) {
		if(order.getItems().get(item) == null) order.getItems().put(item, qty);
		else order.getItems().put(item, order.getItems().get(order)+qty);
	}
	/**
	 * Remove item from order based on passed order and item parameters.
	 * @param order
	 * @param item
	 */
	public void removeItemFromOrder(Order order, Item item){
		order.getItems().remove(item);
	}
	/**
	 * Update item quantity in order based on passed order, item and qty parameters.
	 * @param order
	 * @param item
	 * @param qty
	 */
	public void updateItemQuantityInOrder(Order order, Item item, int qty)
	{
		if(qty > 0) order.getItems().replace(item, qty);
		else removeItemFromOrder(order, item);
	}
	/**
	 * Add order to order list based on passed order parameter.
	 * @param order
	 */
	public void addOrderToOrderList(Order order, Boolean exists, int orderIndex) {
		if (exists == false) {
			orderList.add(order);
		} else {
			orderList.set(orderIndex, order);
		}
	}
	/**
	 * Delete order based on passed order parameter.
	 * @param order
	 */
	public void deleteOrderById(Order order) {
		orderList.remove(order);
	}
	/**
	 * Append to order list based on passed order and orderIndex parameters.
	 * @param order
	 * @param orderIndex
	 */
	public void appendOrderList(Order order, int orderIndex) {
        orderList.set(orderIndex, order);
    }
	/**
	 * Get day of order and add the detail to the order.
	 * @return orders
	 */
	public List<Order> getOrdersDay() {
		List<Order> orders = new ArrayList<>();
		for (Order order : orderList) {
			if (order.getActive() == false) {
				// ORDER CLOSED
				// CHECK IF ORDER WAS CLOSED TODAY
				if (order.getTime().format(DateTimeFormatter.ofPattern("yyyyMMdd")).equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))) {
					orders.add(order);
				}
			}
		}
		return orders;
	}
	/**
	 * Get month of order and add the detail to the order.
	 * @return orders
	 */
	public List<Order> getOrdersMonth() {
		List<Order> orders = new ArrayList<>();
		for (Order order : orderList) {
			if (order.getActive() == false) {
				// ORDER CLOSED
				// CHECK IF ORDER WAS CLOSED THIS MONTH
				if (order.getTime().format(DateTimeFormatter.ofPattern("yyyyMM")).equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM")))) {
					orders.add(order);
				}
			}
		}
		return orders;
	}
	/**
	 * Tabulate the orders based on passed orderList parameter.
	 * @param orderList
	 * @return itemList
	 */
	public List<String> tabulateOrders(List<Order> orderList) {
		Boolean itemFound = false;
		List<String> itemList = new ArrayList<>();
		for (Order order : orderList) {
            for (var orderDetails : order.getItems().entrySet()) {
                itemFound = false;
                for (int i = 0; i < itemList.size(); i++) {
                    if (itemList.get(i).split(",")[0].equals(orderDetails.getKey().getName())) {
                        int tabulateQuantity = Integer.parseInt(itemList.get(i).split(",")[1]) + orderDetails.getValue();
                        float tabulatePrice = Float.parseFloat(itemList.get(i).split(",")[2]) + (orderDetails.getKey().getPrice() * orderDetails.getValue());
                        itemList.set(i, itemList.get(i).split(",")[0] + "," + tabulateQuantity + "," + tabulatePrice);
                        itemFound = true;
                        break;
                    }
                }
                if (itemFound == false) {
                    itemList.add(orderDetails.getKey().getName() + "," + orderDetails.getValue() + "," + (orderDetails.getKey().getPrice() * orderDetails.getValue())); // NAME, QUANTITY, PRICE
                }
            }
        }
		return itemList;
	}
	/**
	 * Tabulate total earnings based on passed itemList parameter
	 * @param itemList
	 * @return totalEarnings
	 */
	public float tabulateTotalEarnings(List<String> itemList) {
		float totalEarnings = 0;
		for (String items : itemList) {
			totalEarnings += Float.parseFloat(items.split(",")[2]); 
		}
		return totalEarnings;
	}
	/**
	 * Save the adjusted data on the IO file.
	 */
	public void saveData() {
		writeSaveIOFile(ORDER_DIRECTORY, orderList);
	}
}