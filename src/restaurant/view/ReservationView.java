package restaurant.view;

import java.time.LocalDateTime;
import java.util.Scanner;
import restaurant.controller.ReservationController;
import restaurant.model.Reservation;
import restaurant.model.Table;

/**
 * <p>Screen for user to manipulate with items in reservation.
 * @author CheenHao, Shirlley
 *
 */

public class ReservationView {

	private Scanner scanner;
	private ReservationController ReservationController;
	/**
	 * ReservationView Constructor
	 */
	public ReservationView() {
		this.ReservationController = new ReservationController();
		scanner = new Scanner(System.in);
		selectCRUDType();
	}
	/**
	 * Select the function related to the reservation by passing input by user into the switch case.
	 */
	private void selectCRUDType() {
		int choice = 0;
		while(choice != -1) {
			System.out.println("1. Create reservation booking\n2. Check reservation booking\n3. Change/Update reservation booking\n4. Remove reservation booking\n-- Press any other number to return to the Main Menu --");
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				createReservationBooking();
				break;
			case 2:
				checkReservationBooking();
				break;
			case 3:
				changeReservationBooking();
				break;
			case 4:
				removeReservationBooking();
				break;
			default:
				choice=-1;
				break;
			}
			ReservationController.saveData();
		}
	}
	/**
	 * Creates reservation base on item selected by user.
	 */
	private void createReservationBooking() {
		Reservation reservation = new Reservation();
        LocalDateTime time = LocalDateTime.now(); //2021-11-05T19:04:51.553665
        String id;
        String name = "";
		String tempdatetime = "";
        int contactNo = 0;
        int pax = 0;
		int tablePax = 0;
		//DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-mm-dd", Locale.ENGLISH);

		// GET USER DATE, TIME AND PAX
		System.out.println("Enter reservation date in yyyy-mm-ddThh:mm format:");
		if (scanner.hasNextLine()) {
			tempdatetime = scanner.nextLine();
			time = LocalDateTime.parse(tempdatetime);
		}
		System.out.println("Enter number of pax (maximum of 10 and minimum of 2):");
		if (scanner.hasNextInt()) {
			pax = scanner.nextInt();
			tablePax = pax;
			scanner.nextLine();  // Consume newline left-over
		}
		// CHECK USER DATE, TIME AND PAX
		if (ReservationController.checkPaxInput(pax)) {
			Table availTable = ReservationController.checkAvailability(time, tablePax);
			if (availTable != null) {
				//int allocatedTable = ReservationController.checkAllocatedTable(availTable);
				System.out.println("Enter customer's name:");
				if (scanner.hasNextLine()) {
					name = scanner.nextLine();	
				}
				System.out.println("Enter customer's contact number:");
				if (scanner.hasNextInt()) {
					contactNo = scanner.nextInt();	
				}
				id = String.format(contactNo + "-" + time);
				if (ReservationController.checkReservationBooking(id) == null) {
					reservation.setname(name);
					reservation.setcontactNo(contactNo);
					reservation.setpax(pax);
					//reservation.setTablePax(availTable);
					reservation.setTable(availTable);
					reservation.settime(time);
					reservation.setid(id);
					ReservationController.createReservationBooking(reservation);
					ReservationController.addReservationToTable(reservation, availTable);
					System.out.println("\nReservation has been successfully made.");
					System.out.println("Allocated Table Number: " + availTable.getTableNumber() + " (Seating capacity: " + availTable.getSeats() + ")\n");
				} else {
					System.out.println("Reservation already exists.");
				}
			} else {
				System.out.println("Selected reservation timing and table is fully booked.");
			}
		} else {
			System.out.println("You've entered an invalid value. Please try again.");
		}
	}
	/**
	 * Check if there is valid reservation booking in the reservation list.
	 */
	private void checkReservationBooking() {
        LocalDateTime time = LocalDateTime.now(); //2021-11-05T19:04:51.553665
        String id;
		String tempdatetime = "";
		int contactNo = 0;

		System.out.println("Enter reservation date in yyyy-mm-ddThh:mm format:");
		if (scanner.hasNextLine()) {
			tempdatetime=scanner.nextLine();
		}
		System.out.println("Enter customer's contact number:");
		if (scanner.hasNextInt()) {
			contactNo = scanner.nextInt();
		}

		time = LocalDateTime.parse(tempdatetime);
        id = String.format(contactNo + "-" + time);
		Reservation reservation = ReservationController.checkReservationBooking(id);

		if (reservation == null) {
			System.out.println("Reservation does not exist.");
		} else {
			System.out.println("\nReservation found!");
			System.out.println("Name: " + reservation.getname());
			System.out.println("Contact No: " + reservation.getcontactNo());
			System.out.println("Date: " + reservation.gettime());
			System.out.println("Pax: " + reservation.getpax());
			System.out.println("Allocated Table Number: " + reservation.getTable().getTableNumber() + " (Seating capacity: " + reservation.getTable().getSeats() + ")\n");
		}
	}
	/**
	 * Change reservation booking info base on option selected by user.
	 */
	private void changeReservationBooking() {
		LocalDateTime time = LocalDateTime.now(); //2021-11-05T19:04:51.553665
        int contactNo = 0;
        String id;
		String tempdatetime = "";

		System.out.println("Enter reservation date in yyyy-mm-ddThh:mm format:");
		if (scanner.hasNextLine())
			tempdatetime=scanner.nextLine();
		System.out.println("Enter customer contact number:");
		if (scanner.hasNextInt())
			contactNo=scanner.nextInt();
		time = LocalDateTime.parse(tempdatetime);
        id = String.format(contactNo + "-" + time);

		Reservation reservation = ReservationController.checkReservationBooking(id);

		if (reservation != null) {

			System.out.println(reservation.getid() + " found");
			System.out.println("Update\n1. Name\n2. Pax\n3. Contact number\n4. Time\n-- Press any other number to return to the previous Menu --");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				System.out.println("Enter new name:");
				String newName = scanner.nextLine();
				ReservationController.changeReservationByName(reservation, newName);
				System.out.println("Name changed successfully.");
				break;
			case 2:
				System.out.println("Enter new number of pax (maximum of 10 and minimum of 2):");
				int newPax = scanner.nextInt();
				if (ReservationController.checkPaxInput(newPax)) {
					Table availTable = ReservationController.checkExistingAvailability(time, newPax, reservation);
					if (availTable != null) {
						//int allocatedTable = ReservationController.checkAllocatedTable(availTable);
						ReservationController.changeReservationByPax(reservation, newPax, availTable);
						System.out.println("\nNumber of pax changed successfully.");
						System.out.println("Allocated Table Number: " + reservation.getTable().getTableNumber() + " (Seating capacity: " + availTable.getSeats() + ")\n");
					} else {
						System.out.println("Selected reservation date and table is fully booked.");
					}
				} else {
					System.out.println("You entered an invalid value. Please try again.");
				}
				break;
			case 3:
				System.out.println("Enter new contact number:");
				int newContactNo = scanner.nextInt();
				id = String.format(newContactNo + "-" + time);
				if (ReservationController.checkReservationBooking(id) == null) {
					ReservationController.changeReservationByContactNo(reservation, newContactNo);
					System.out.println("Contact number changed successfully.");
					} 
				else {
					System.out.println("Reservation exists.");
				}
				break;
			case 4:
				System.out.println("Enter new time in yyyy-mm-ddThh:mm format:");
				String newTime = scanner.nextLine();
				LocalDateTime newTimeLocal = LocalDateTime.parse(newTime);
				Table availTable = ReservationController.checkAvailability(newTimeLocal, reservation.getpax());
				if (availTable != null) {
					ReservationController.changeReservationByTime(reservation, newTime, availTable);
					System.out.println("Time changed successfully.");
				} else {
					System.out.println("Selected reservation date and table is fully booked.");
				}
				break;
			}
		} else {
			System.out.println("No such reservation");	
		}	
	}
	/**
	 * Remove reservation from list of reservation.
	 */
	private void removeReservationBooking() {
		LocalDateTime time = LocalDateTime.now(); //2021-11-05T19:04:51.553665
		int contactNo = 0;
        String id;
		String tempdatetime = "";

		System.out.println("Enter reservation date in yyyy-mm-ddThh:mm format:");
		if (scanner.hasNextLine())
			tempdatetime=scanner.nextLine();
		System.out.println("Enter customer's contact number:");
		if (scanner.hasNextInt())
			contactNo=scanner.nextInt();
		time = LocalDateTime.parse(tempdatetime);
        id = String.format(contactNo + "-" + time);

		Reservation reservation = ReservationController.checkReservationBooking(id);

		if (reservation != null) {
		ReservationController.deleteReservation(reservation);
		System.out.println(reservation.getid() + " successfully deleted.");
		} else {
			System.out.println("No such reservation.");
		}
	}
}