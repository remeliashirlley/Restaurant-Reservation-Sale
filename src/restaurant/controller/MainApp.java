package restaurant.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import restaurant.model.Reservation;
import restaurant.view.*;

/**
 * <p>Main menu controller of the application
 * @author Muhaimin, Hakiim, CheenHao, Shirlley
 *
 */

public class MainApp {
	/**
	 * Main constructor  
	 * @param args
	 */

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int input = 0;

		// SCHEDULE TIMER TO RUN EVERY MINUTE
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				List<Reservation> deletedReservations = new ReservationController().deleteReservationAfterPeriodExpiry();
				if (!deletedReservations.isEmpty()) {
					System.out.println("\n");
					for (Reservation reservation : deletedReservations) {
						System.out.println("Reservation ID, " + reservation.getid() + ", removed as period elapsed more than set time.");
					}
					System.out.println("\n");
					System.out.println("Please select an option: \n1. Create/Update/Remove menu item \n2. Create/Update/Remove promotion"
						+ "\n3. Create/View order\n4. Add/Remove reservation booking\n5. Check table availability\n"
						+ "6. Print order invoice\n7. Print sale revenue report by period");
				}
			}
		};
		timer.schedule(task, 0, 60*1000);
		
		while(input!=-1) {
			System.out.println("Please select an option: \n1. Create/Display/Update/Remove menu item \n2. Create/Display/Update/Remove promotion"
							+ "\n3. Create/View order\n4. Add/Display/Remove reservation booking\n5. Check table availability\n"
							+ "6. Print order invoice\n7. Print sale revenue report by period");
			
			 input = sc.nextInt();
	
			switch (input) {
				case 1: 
					new MenuView();
					break;
				
				case 2: 
					new PromoView();
					break;
				
				case 3: 
					new OrderView();
					break;
				
				case 4: 
					new ReservationView();
					break;
				
				case 5: 
					new TableView();
					break;
				
				case 6: 
					new InvoiceView();
					break;
				
				case 7: 
					new SalesView();
					break;
				
				default:
					input=-1;
					break;
				
			}

		
		}
		sc.close();
		timer.cancel();
	}

}
