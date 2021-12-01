package restaurant.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import restaurant.controller.ReservationController;

/**
 * <p> Model for Reservation object 
 * <p> Inherits {@link AbstractModel}
 * @author Shirlley
 *
 */

public class Reservation extends AbstractModel{

	private static final long serialVersionUID = -1838205420865817317L;
	
	//private LocalDate date;
	private LocalDateTime time;
	private int pax;
	private Table table;
	private int contactNo;
	//private Customer customer;
	private String name;
	private String id;
	private List<Reservation> reservationList;

	static Scanner userinput=new Scanner(System.in);
	public static ReservationController ReservationController;
	/**
	 * constructor for Reservation
	 */
	public Reservation() {
		this.reservationList = new ArrayList<>();
	}
	
	/**
	 * Get name for Reservation
	 * @return name
	 */
	public String getname(){
		return name;
	}

	/**
	 * Set name for Reservation
	 * @param name
	 */
	public void setname(String name){
		this.name = name;
	}

	/**
	 * Get contact number for Reservation
	 * @return contactNo
	 */
	public int getcontactNo(){
		return contactNo;
	}

	/**
	 * Set contact number for Reservation
	 * @param contactNo
	 */
	public void setcontactNo(int contactNo){
		this.contactNo = contactNo;
	}

	/**
	 * Get number of pax
	 * @return pax
	 */
	public int getpax(){
		return pax;
	}

	/**
	 * Set number of pax
	 * @param pax
	 */
	public void setpax(int pax){
		this.pax = pax;
	}

	/**
	 * Get table number in Reservation
	 * @return
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Set table number in Reservation
	 * @param table
	 */
	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * Get local time in Reservation
	 * @return
	 */
	public LocalDateTime gettime(){
		return time;
	}

	/**
	 * Set local time in Reservation
	 * @param time
	 */
	public void settime(LocalDateTime time){
		this.time = time;
	}

	/**
	 * Get id for Reservation
	 * @return id
	 */
	public String getid(){
		return id;
	}

	/**
	 * Set id for Reservation
	 * @param id
	 */
	public void setid(String id){
		this.id = id;
	}

	/**
	 * Get reservation list in Reservation
	 * @return reservationList
	 */
	public List<Reservation> getreservationList() {
		return reservationList;
	}

	/**
	 * Set reservation list in Reservation
	 * @param reservations
	 */
	public void setreservationList(List<Reservation> reservations) {
		this.reservationList = reservations;
	}
}