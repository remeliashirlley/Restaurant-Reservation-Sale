package restaurant.view;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import restaurant.controller.OrderController;
import restaurant.model.Order;

/**
 * <p>Screen to display final Sales.
 * @author CheenHao
 *
 */

public class SalesView {
    
    DecimalFormat df;
	private Scanner scanner;
    private OrderController orderController;
    /**
     * SalesView Constructor
     */
    public SalesView() {
        this.orderController = new OrderController();
        scanner = new Scanner(System.in);
        df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        selectCDType();
    }
    /**
	 * Select the type of sales ouput by passing input from user into the switch case.
	 */
    private void selectCDType() {

		int choice = 0;

		while (choice != -1) {
			System.out.println("\nGenerate revenue report for:\n1. Today\n2. Current Month\n--Press any other number to return to the Main Menu--");
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				printDay();
				break;
			case 2:
				printMonth();
				break;
			default:
				choice = -1;
				break;
			}

			orderController.saveData();
		}
	}
    /**
     * Display Total Sales in current day
     */
    private void printDay() {
        List<Order> orderList = orderController.getOrdersDay();
        List<String> itemList = orderController.tabulateOrders(orderList);
        int getLongestString = orderController.getLongestStringSalesInvoice(itemList);
        if (itemList.size() == 0) {
            System.out.println("\nNo orders found.\n");
        } else {
            System.out.println("\nSales Revenue Report (" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + ")\n");
            for (String item : itemList) {
                int getPaddingLength = orderController.getPaddingLength(getLongestString, item.split(",")[0].length());
                System.out.println(item.split(",")[1] + "\t" + item.split(",")[0] + String.format("%" + getPaddingLength + "s", df.format(Float.parseFloat(item.split(",")[2]))));
            }
            float totalEarnings = orderController.tabulateTotalEarnings(itemList);
            System.out.println("\nTotal Earnings: " + df.format(totalEarnings));
        }
    }
    /**
     * Display Total Sales in current month
     */
    private void printMonth() {
        List<Order> orderList = orderController.getOrdersMonth();
        List<String> itemList = orderController.tabulateOrders(orderList);
        int getLongestString = orderController.getLongestStringSalesInvoice(itemList);
        if (itemList.size() == 0) {
            System.out.println("\nNo orders found.\n");
        } else {
            System.out.println("\nSales Revenue Report (" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM yyyy")) + ")\n");
            for (String item : itemList) {
                int getPaddingLength = orderController.getPaddingLength(getLongestString, item.split(",")[0].length());
                System.out.println(item.split(",")[1] + "\t" + item.split(",")[0] + String.format("%" + getPaddingLength + "s", df.format(Float.parseFloat(item.split(",")[2]))));
            }
            float totalEarnings = orderController.tabulateTotalEarnings(itemList);
            System.out.println("\nTotal Earnings: " + df.format(totalEarnings));
        }
    }
}