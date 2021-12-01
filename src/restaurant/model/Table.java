package restaurant.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Model for Table object 
 * <p> Inherits {@link AbstractModel}
 * @author CheenHao
 *
 */

public class Table extends AbstractModel{
	private static final long serialVersionUID = 1L;
	private int seats;
	private List<Reservation> reservationList;
	private int tableNumber;

	/**
	 * Table constructor
	 * @param tableNumber
	 * @param seats
	 */
	public Table(int tableNumber, int seats){

		this.tableNumber = tableNumber;
		this.seats = seats;
		this.reservationList = new ArrayList<>();
	}

	
	/** 
	 * @return List<Reservation>
	 */
	//public boolean setisReserved(boolean isReserved){
	//	this.isReserved=isReserved;

	
	/**
	 * Get reservation list for Table
	 * @return reservationList
	 */	
	public List<Reservation> getReservationList() {
		return reservationList;
	}

	/**
	 * Set reservation list for Table
	 * @param reservationList
	 */
	public void setReservationList(List<Reservation> reservationList) {
		this.reservationList = reservationList;
	}
	
	/**
	 * Add a new reservation to list for Table
	 * @param reservation
	 */
	public void addToReservationList(Reservation reservation) {
		this.reservationList.add(reservation);
	}

	/**
	 * Get number of seat for Table
	 * @return seats
	 */
	public int getSeats() {
		return seats;
	}

	/**
	 * Set nunmber of seat for Table
	 * @param seats
	 */
	public void setSeats(int seats) {
		this.seats = seats;
	}

	/**
	 * Get table number for Table
	 * @return
	 */
	public int getTableNumber() {
		return tableNumber;
	}

	/**
	 * Set table number for Table
	 * @param tableNumber
	 */
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	
}
