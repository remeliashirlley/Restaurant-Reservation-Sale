package restaurant.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import restaurant.model.Reservation;
import restaurant.model.Table;
/**
 * <p>Business logic pertaining to Reservation. It involves the create, read, update and delete functionalities.
 * <p>Inherits {@link AbstractController} 
 * @author Shirlley
 *
 */

public class ReservationController extends AbstractController<Reservation> {

	private Reservation reservation;
	private List<Table> tableList;

	private static final String RESERVATION_DIRECTORY = "src\\restaurant\\io\\reservation.io";
	private static final int PERIOD_EXPIRY = 60; // ADJUST THIS TO CHANGE THE PERIOD EXPIRY, IN MINUTES
	/**
	 * If data from file is not retrieved successfully, create new instance of {@link Order}.
	 */
	public ReservationController() {
		reservation = loadReadIOFile(RESERVATION_DIRECTORY);
		
		if (reservation == null) {
			reservation = new Reservation();
		}
		
		tableList = new ArrayList<>();
		Table table1 = new Table(1, 2);
		Table table2 = new Table(2, 4);
		Table table3 = new Table(3, 6);
		Table table4 = new Table(4, 8);
		Table table5 = new Table(5, 10);
		Table table6 = new Table(6, 4);
		
		tableList.add(table1);
		tableList.add(table2);
		tableList.add(table3);
		tableList.add(table4);
		tableList.add(table5);
		tableList.add(table6);
		
		
		for (Reservation reservationLoop : reservation.getreservationList()) {
			int tableNum = reservationLoop.getTable().getTableNumber();
			tableList.get(tableNum-1).addToReservationList(reservationLoop);
		}
	}
	/**
	 * Check if there is valid reversation booking
	 * @param id
	 * @return reservationLoop or null
	 */
	public Reservation checkReservationBooking(String id) {
		for (Reservation reservationLoop : reservation.getreservationList()) {
			if (id.equals(reservationLoop.getid()) == true) {
				return reservationLoop;
			}
		}
		return null;
	}
	/**
	 * Check is user input entered is between 2 - 10 paxs
	 * @param pax
	 * @return True or False
	 */
	public Boolean checkPaxInput(int pax) {
		if (pax <= 10 && pax >= 2) { 
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Get list of table
	 * @return List
	 */
	public List<Table> getTableList(){
		
		return tableList;
		
	}
	
	
	/**
	 * Add valid table reservation to table
	 * @param reservation
	 * @param table
	 */
	public void addReservationToTable(Reservation reservation, Table table) {
		table.getReservationList().add(reservation);
	}
	/**
	 * Remove table reservation from table
	 * @param reservation
	 * @param table
	 */
	public void removeTableReservation(Reservation reservation, Table table){
		table.getReservationList().remove(reservation);
	}
	
	/**
	 * Check if specified time, pax and reservation equals to the queried
	 * @param time
	 * @param tablePax
	 * @param reservation
	 * @return reserveTable
	 */
	public Table checkExistingAvailability(LocalDateTime time, int tablePax, Reservation reservation) {
		Table reserveTable = new Table(0,12);
		for(Table table : tableList)
		{
			if(table.getSeats()>=tablePax)
			{
				if(table.getReservationList().isEmpty())
				{
					if(reserveTable.getSeats()>table.getSeats())
					{
						reserveTable = table;
					}
				}
				else
				{
					for(Reservation resLoop : table.getReservationList())
					{
						long minutes = ChronoUnit.MINUTES.between(resLoop.gettime(), time);
						if(reservation.getid().equals(resLoop.getid()))
						{
							
							System.out.println("Check");
							if(reserveTable.getSeats()>table.getSeats())
							{
								
								
								reserveTable = table;
							}
						}
						else if (!(minutes < PERIOD_EXPIRY && minutes > -PERIOD_EXPIRY)) {
							if(reserveTable.getSeats()>table.getSeats())
							{
								reserveTable = table;
							}
						}
						
					}
				}
				
			}
			
		}
		
		if(reserveTable.getSeats()==12)
			return null;
		else return reserveTable;
	}
	/**
	 * Check for current table availablilty
	 * @param time
	 * @param tablePax
	 * @return reserveTable
	 */
	public Table checkAvailability(LocalDateTime time, int tablePax) {
		
		Table reserveTable = new Table(0,12);
		for(Table table : tableList)
		{
			if(table.getSeats()>=tablePax)
			{
				if(table.getReservationList().isEmpty())
				{
					if(reserveTable.getSeats()>table.getSeats())
					{
						reserveTable = table;
					}
				}
				else
				{
					for(Reservation resLoop : table.getReservationList())
					{
						long minutes = ChronoUnit.MINUTES.between(resLoop.gettime(), time);
						if (!(minutes < PERIOD_EXPIRY && minutes > -PERIOD_EXPIRY)) {
							if(reserveTable.getSeats()>table.getSeats())
							{
								reserveTable = table;
							}
						}
						
					}
				}
				
			}
			
		}
		
		if(reserveTable.getSeats()==12)
			return null;
		else return reserveTable;
		

	}
	/**
	 * Check if reservation made is more than the specified minutes. If true, deleted
	 * @return deletedReservations
	 */
	public List<Reservation> deleteReservationAfterPeriodExpiry() {
		List<Reservation> deletedReservations = new ArrayList<>();
		for (Reservation reservationLoop : reservation.getreservationList()) {
			long minutes = ChronoUnit.MINUTES.between(reservationLoop.gettime(), LocalDateTime.now());
			// DELETE RESERVATION IF MORE THAN XX MINUTES
			if (minutes >= PERIOD_EXPIRY) {
				deletedReservations.add(reservationLoop);
				reservationLoop.getTable().getReservationList().remove(reservationLoop);
			}
		}
		reservation.getreservationList().removeAll(deletedReservations);
		saveData();
		return deletedReservations;
	}
	/**
	 * Creation of Reservation Booking
	 * @param newReservation
	 */
	public void createReservationBooking(Reservation newReservation) {
		reservation.getreservationList().add(newReservation);
	}
	/**
	 * To change name through valid reservsation
	 * @param reservation
	 * @param newName
	 */
    public void changeReservationByName(Reservation reservation, String newName) {
		reservation.setname(newName);
	}
	/**
	 * To change number of pax through valid reservation
	 * @param reservation
	 * @param newPax
	 * @param newTable
	 */
	public void changeReservationByPax(Reservation reservation, int newPax, Table newTable) {
		reservation.getTable().getReservationList().remove(reservation);
		reservation.setpax(newPax);
		reservation.setTable(newTable);
		newTable.addToReservationList(reservation);
	}
	/**
	 * To change contact number through valid reservation
	 * @param reservation
	 * @param newcontactNo
	 */
	public void changeReservationByContactNo(Reservation reservation, int newcontactNo) {
		reservation.setcontactNo(newcontactNo);
        reservation.setid(String.format(reservation.getcontactNo() + "-" + reservation.gettime()));
	}
	/**
	 * To change time of reservation through valid reservation
	 * @param reservation
	 * @param newtime
	 * @param newTable
	 */
	public void changeReservationByTime(Reservation reservation, String newtime, Table newTable) {
		reservation.getTable().getReservationList().remove(reservation);
		reservation.settime(LocalDateTime.parse(newtime));
        reservation.setid(String.format(reservation.getcontactNo() + "-" + reservation.gettime()));
        newTable.addToReservationList(reservation);
	}

	/**
	 * To delete existing reservation through valid reservation
	 * @param removeReservation
	 */
    public void deleteReservation(Reservation removeReservation) {
		reservation.getreservationList().remove(removeReservation);
	}
	/**
	 * To check if reservation is valid
	 * @param table
	 * @return True or False
	 */
	public Boolean checkExistingReservation(Table table) {
		for (Reservation reservationLoop : reservation.getreservationList()) {
			long minutes = ChronoUnit.MINUTES.between(reservationLoop.gettime(), LocalDateTime.now());
			if (minutes <= PERIOD_EXPIRY && minutes >= 0) {
				if (table == reservationLoop.getTable()) {
					deleteReservation(reservationLoop);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Get system time in minutes
	 * @param time
	 * @return minutes
	 */
	public long getTimeToReservationMinutes(LocalDateTime time) {
		long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), time);
		return minutes;
	}
	/**
	 * Get System time in hours
	 * @param time
	 * @return hours
	 */
	public long getTimeToReservationHours(LocalDateTime time) {
		long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), time);
		return hours;
	}
	/**
	 * Get system time in day
	 * @param time
	 * @return days
	 */
	public long getTimeToReservationDays(LocalDateTime time) {
		long days = ChronoUnit.DAYS.between(LocalDateTime.now(), time);
		return days;
	}
	/**
	 * return conversion to minutes
	 * @param timeToReservationHours
	 * @param timeToReservationMinutes
	 * @return
	 */
	public long getMinutesBasedOnHours(long timeToReservationHours, long timeToReservationMinutes) {
		return timeToReservationMinutes - (timeToReservationHours * 60);
	}
	/**
	 * Sort reservation by time comparsion
	 * @param tableAvailability
	 */
	public void sortReservation(List<Reservation> tableAvailability) {
		tableAvailability.sort(Comparator.comparing(Reservation::gettime));
	}
	/**
	 * Save the adjusted data on the IO file.
	 */
	public void saveData() {
		writeSaveIOFile(RESERVATION_DIRECTORY, reservation);
	}
}