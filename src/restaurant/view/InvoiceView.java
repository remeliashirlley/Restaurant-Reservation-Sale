package restaurant.view;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import restaurant.controller.OrderController;
import restaurant.controller.ReservationController;
import restaurant.model.Order;
/**
 * <p>Screen to display final invoice in details.
 * @author CheenHao
 *
 */
public class InvoiceView {

	private static final String RESTAURANT_NAME = "TEAM 4";
	private static final String RESTAURANT_ADDRESS = "TEAM 4@Nanyang Cres";
    private static final int membershipDiscountPercentage = 20; // ADJUST THIS TO CHANGE THE MEMBERSHIP DISCOUNT PERCENT; 20% = 20
    private static final int serviceChargePercentage = 10; // ADJUST THIS TO CHANGE THE SERVICE CHARGE PERCENT; 10% = 10

    private OrderController orderController;
    private ReservationController reservationController;
    private Scanner scanner;
    private Order order;

    /**
     * InvoiceView Constructor
     */
	public InvoiceView() {
        this.orderController = new OrderController();
        this.reservationController = new ReservationController();
		scanner = new Scanner(System.in);
		printOrderList();
	}
    
    /**
     * Check if order is empty
     * Get corresponding order id and table number base on order selected
     * Get calculate membership price if membership set
     * Check if there is existing reservation
     * Print invoice 
     */
    private void printOrderList() {
        int index = 0;
        int choice = 0;
        int membershipCard = 0;
        Boolean inputValidated = false;

        if(orderController.getOrders().isEmpty())
        {
        	System.out.println("There are no orders.");
        	return;
        }
        while (choice != -1 ) {
            System.out.println("\nPlease select the order:");
            for (Order order: orderController.getOrders()) {
                if (order.getActive() == true) {
                    System.out.println(++index + ". Order ID #" + order.getId() + " | Table Number: " + order.getTable().getTableNumber() + " | Created by: " + order.getStaff() + " | Closed: No");
                } else {
                    System.out.println(++index + ". Order ID #" + order.getId() + " | Table Number: " + order.getTable().getTableNumber() + " | Created by: " + order.getStaff() + " | Closed: Yes");
                }
            }
            System.out.println("-- Press any other number to return to the Main Menu --");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice <= 0 || choice > orderController.getTotalOrders()) {
                choice = -1;
            } 
            else if(!orderController.getOrderById(choice).getActive()){
            	order = orderController.getOrderById(choice);
            	DecimalFormat df = new DecimalFormat();
                df.setMinimumFractionDigits(2);
                df.setMaximumFractionDigits(2);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  

                // CALCULATE PRICES & LONGEST STRING LENGTH
                float getTotalPrice = orderController.getTotalPrice(order);
                float getMembershipPrice = 0;
                if (order.getHasMembership() && membershipDiscountPercentage > 0) {
                    getMembershipPrice = orderController.getMembershipDiscount(getTotalPrice, membershipDiscountPercentage);
                }
                float getServiceCharge = orderController.getServiceCharge(getTotalPrice, getMembershipPrice);
                float getGSTPrice = orderController.getGSTPrice(getTotalPrice, getMembershipPrice, getServiceCharge);
                float getSubTotalPrice = orderController.getSubTotalPrice(getTotalPrice, getGSTPrice, getMembershipPrice, getServiceCharge);
                int getLongestString = orderController.getLongestString(order);
            
                // CHECK IF TABLE NUMBER HAS AN EXISTING RESERVATION
                Boolean reservationExists = reservationController.checkExistingReservation(order.getTable());
                reservationController.saveData();

                // START PRINTING
                System.out.println("\nClosing order and printing sales invoice...");
                if (reservationExists) {
                    System.out.println("Reservation for this table found! Reservation will be removed.");
                }
                System.out.println("\n" + RESTAURANT_NAME + "\n" + RESTAURANT_ADDRESS + "\nOrder: #" + orderController.getOrderById(choice).getId() + "   |   Table No: " + orderController.getOrderById(choice).getTable().getTableNumber() + "   |   Server: " + orderController.getOrderById(choice).getStaff() + "\n");
                for (var order : orderController.getOrderById(choice).getItems().entrySet()) {
                    int getPaddingLength = orderController.getPaddingLength(getLongestString, order.getKey().getName().length());
                    System.out.println(order.getValue() + "\t" + order.getKey().getName() + String.format("%" + getPaddingLength + "s", df.format(order.getKey().getPrice() * order.getValue())));
                }
                System.out.println("\n    TOTAL: " + df.format(getTotalPrice));
                if (order.getHasMembership() && membershipDiscountPercentage > 0) {
                    int membershipPadding = orderController.getMembershipPadding(membershipDiscountPercentage);
                    System.out.println(String.format("%" + membershipPadding + "s", membershipDiscountPercentage) + "%MEMBR: - " + df.format(getMembershipPrice));
                }
                System.out.println("    7%GST: " + df.format(getGSTPrice));
                System.out.println("   10%SVC: " + df.format(getServiceCharge));
                System.out.println("SUB-TOTAL: " + df.format(getSubTotalPrice) + "\n");
                System.out.println("Thank you for dining with us!");
                System.out.println(dtf.format(order.getTime()) + "\n");
                orderController.appendOrderList(order, (choice - 1));
		        orderController.saveData();
                choice = -1;
            }else {
                // CHECK IF CUSTOMER HAS A MEMBERSHIP CARD
                System.out.println("\nDoes the customer have a membership card?\n1. Yes\n2. No");
                while(inputValidated == false) {
                    membershipCard = scanner.nextInt();
                    scanner.nextLine();
                    if (membershipCard == 1 || membershipCard == 2) {
                        inputValidated = true;
                    } else {
                        System.out.println("Invalid input. Please try again!");
                    }
                }

                // INITIALIZE
                order = orderController.getOrderById(choice);
                
                if(membershipCard==1) order.setHasMembership(true);
                else if(membershipCard==2) order.setHasMembership(false);
                
                DecimalFormat df = new DecimalFormat();
                df.setMinimumFractionDigits(2);
                df.setMaximumFractionDigits(2);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  

                // CALCULATE PRICES & LONGEST STRING LENGTH
                float getTotalPrice = orderController.getTotalPrice(order);
                float getMembershipPrice = 0;
                if (membershipCard == 1 && membershipDiscountPercentage > 0) {
                    getMembershipPrice = orderController.getMembershipDiscount(getTotalPrice, membershipDiscountPercentage);
                }
                float getServiceCharge = orderController.getServiceCharge(getTotalPrice, getMembershipPrice);
                float getGSTPrice = orderController.getGSTPrice(getTotalPrice, getMembershipPrice, getServiceCharge);
                float getSubTotalPrice = orderController.getSubTotalPrice(getTotalPrice, getGSTPrice, getMembershipPrice, getServiceCharge);
                int getLongestString = orderController.getLongestString(order);
            
                // CHECK IF TABLE NUMBER HAS AN EXISTING RESERVATION
                Boolean reservationExists = reservationController.checkExistingReservation(order.getTable());
                reservationController.saveData();

                // START PRINTING
                System.out.println("\nClosing order and printing sales invoice...");
                if (reservationExists) {
                    System.out.println("Reservation for this table found! Reservation will be removed.");
                }
                System.out.println("\n" + RESTAURANT_NAME + "\n" + RESTAURANT_ADDRESS + "\nOrder: #" + orderController.getOrderById(choice).getId() + "   |   Table No: " + orderController.getOrderById(choice).getTable().getTableNumber() + "   |   Server: " + orderController.getOrderById(choice).getStaff() + "\n");
                for (var order : orderController.getOrderById(choice).getItems().entrySet()) {
                    int getPaddingLength = orderController.getPaddingLength(getLongestString, order.getKey().getName().length());
                    System.out.println(order.getValue() + "\t" + order.getKey().getName() + String.format("%" + getPaddingLength + "s", df.format(order.getKey().getPrice() * order.getValue())));
                }
                System.out.println("\n    TOTAL: " + df.format(getTotalPrice));
                if (membershipCard == 1 && membershipDiscountPercentage > 0) {
                    int membershipPadding = orderController.getMembershipPadding(membershipDiscountPercentage);
                    System.out.println(String.format("%" + membershipPadding + "s", membershipDiscountPercentage) + "%MEMBR: - " + df.format(getMembershipPrice));
                }
                System.out.println("    7%GST: " + df.format(getGSTPrice));
                System.out.println("   10%SVC: " + df.format(getServiceCharge));
                System.out.println("SUB-TOTAL: " + df.format(getSubTotalPrice) + "\n");
                System.out.println("Thank you for dining with us!");
                System.out.println(dtf.format(LocalDateTime.now()) + "\n");
                order.setActive(false);
                order.setTime(LocalDateTime.now());
                orderController.appendOrderList(order, (choice - 1));
		        orderController.saveData();
		        System.out.println("Invoice has been printed, table no." + order.getTable().getTableNumber() + " has been released.\n");
                choice = -1;
            }
        }
    }
}