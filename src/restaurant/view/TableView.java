package restaurant.view;

import java.util.ArrayList;
import java.util.List;

import restaurant.controller.ReservationController;
import restaurant.model.Reservation;
import restaurant.model.Table;
/**
 * <p>Screen for user to see output of table's status.
 * @author CheenHao
 *
 */
public class TableView {

    private ReservationController reservationController;
    /**
     * TableView constructor
     */
    public TableView() {
        this.reservationController = new ReservationController();
        printTableList();
    }
    /**
     * Dislay Table number and capacity
     */
    private void printTableList() {
    	for(Table table : reservationController.getTableList()){
	    		System.out.println("\nTable "+table.getTableNumber()+" | Seating Capacity: "+table.getSeats());
	    		printTableReservations(table);
    		}
    }
    
    
    /**
     * Display reservation base on table avaliablilty in order of date and time
     * Method overloading
     * @param table
     */
    public void printTableReservations(Table table) {
    	if (table.getReservationList().isEmpty()) {
            System.out.println("No Reservations");
        } else {
            // SORT RESERVATIONS BY DATE & TIME
            reservationController.sortReservation(table.getReservationList());
            for (Reservation reservation : table.getReservationList()) {
                long timeToReservationMinutes = reservationController.getTimeToReservationMinutes(reservation.gettime());
                long timeToReservationHours = reservationController.getTimeToReservationHours(reservation.gettime());
                long timeToReservationDays = reservationController.getTimeToReservationDays(reservation.gettime());
                if (timeToReservationDays > 0) {
                    System.out.println("Reservation in " + timeToReservationDays + " days [" + reservation.getid() + "]");
                } else if (timeToReservationHours > 0) {
                    long minutesBasedOnHours = reservationController.getMinutesBasedOnHours(timeToReservationHours, timeToReservationMinutes);
                    System.out.println("Reservation in " + timeToReservationHours + "h " + minutesBasedOnHours + "m [" + reservation.getid() + "]");
                } else if (timeToReservationMinutes > 0) {
                    System.out.println("Reservation in " + timeToReservationMinutes + "m [" + reservation.getid() + "]");
                } else {
                    System.out.println("Reservation has started [" + reservation.getid() + "]");
                }
            }
        }
    }
    /**
     * Display reservation base on table avaliablilty in order of date and time
     * @param tableAvailability
     */
    public void printTableReservations(List<Reservation> tableAvailability) {
        if (tableAvailability.isEmpty()) {
            System.out.println("No Reservations");
        } else {
            // SORT RESERVATIONS BY DATE & TIME
            reservationController.sortReservation(tableAvailability);
            for (Reservation reservation : tableAvailability) {
                long timeToReservationMinutes = reservationController.getTimeToReservationMinutes(reservation.gettime());
                long timeToReservationHours = reservationController.getTimeToReservationHours(reservation.gettime());
                long timeToReservationDays = reservationController.getTimeToReservationDays(reservation.gettime());
                if (timeToReservationDays > 0) {
                    System.out.println("Reservation in " + timeToReservationDays + " days [" + reservation.getid() + "]");
                } else if (timeToReservationHours > 0) {
                    long minutesBasedOnHours = reservationController.getMinutesBasedOnHours(timeToReservationHours, timeToReservationMinutes);
                    System.out.println("Reservation in " + timeToReservationHours + "h " + minutesBasedOnHours + "m [" + reservation.getid() + "]");
                } else if (timeToReservationMinutes > 0) {
                    System.out.println("Reservation in " + timeToReservationMinutes + "m [" + reservation.getid() + "]");
                } else {
                    System.out.println("Reservation has started [" + reservation.getid() + "]");
                }
            }
        }
    }
}
