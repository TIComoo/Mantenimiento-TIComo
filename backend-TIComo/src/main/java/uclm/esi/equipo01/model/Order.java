package uclm.esi.equipo01.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import uclm.esi.equipo01.http.Manager;

/*********************************************************************
*
* Class Name: Order
* Class description: Dedicated to the Orders management methods and data
*
**********************************************************************/

@Document(collection = "Orders")
public class Order {

	@Transient
	public static final int SEQUENCE_ID = Sequence.ORDER.getValue();
	
	@Id
    private long id;
	
	private long clientID;
	private Long riderID;
	private long restaurantID;
	private State state;
	private double price;
	private LocalDateTime releaseDate;
	
	public Order() {
		super();
	}
	
	/*********************************************************************
	*
	* - Method name: Order
	* - Description of the Method: Order class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Long clientID: Client id
	* 		• Long riderID: Rider id
	* 		• Long restaurantID: Restaurant id
	*		• ArrayList<Plate> plates: list of plates from the order
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public Order(long clientID, long restaurantID) {
		super();
		this.setId(Manager.get().generateSequence(SEQUENCE_ID));
		this.clientID = clientID;
		this.riderID = null;
		this.restaurantID = restaurantID;
		this.state = State.NEW; // It can be "New", "OnTheWay", "Delivered"
		this.releaseDate = LocalDateTime.now();
	}
	
	/*********************************************************************
	*
	* - Method name: Order
	* - Description of the Method: Order class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Long id: Order id
	* 		• Long clientID: Client id
	* 		• Long riderID: Rider id
	* 		• Long restaurantID: Restaurant id
	*		• ArrayList<Plate> plates: list of plates from the order
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public Order(long id, long clientID, long restaurantID) {
		super();
		this.id = id;
		this.clientID = clientID;
		this.riderID = null;
		this.restaurantID = restaurantID;
		this.state = State.NEW;
	}
	
	public Order(long id, long clientID, long restaurantID, long riderID, State state, double price, LocalDateTime releaseDate) {
		super();
		this.id = id;
		this.clientID = clientID;
		this.riderID = riderID;
		this.restaurantID = restaurantID;
		this.price = price;
		this.releaseDate = releaseDate;
		this.state = state;
	}
	
	public static int getSequenceId() {
		return SEQUENCE_ID;
	}

	public long getId() {
		return id;
	}

	public long getClientID() {
		return clientID;
	}

	public Long getRiderID() {
		return riderID;
	}

	public long getRestaurantID() {
		return restaurantID;
	}
	
	public State getState() {
		return state;
	}
	
	public double getPrice() {
		return price;
	}
	
	public LocalDateTime getReleaseDate() {
		return releaseDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setClientID(long clientID) {
		this.clientID = clientID;
	}

	public void setRestaurantID(long restaurantID) {
		this.restaurantID = restaurantID;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setReleaseDate(LocalDateTime releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setRiderID(Long riderID) {
		this.riderID = riderID;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", clientID=" + clientID + ", riderID=" + riderID + ", restaurantID=" + restaurantID
				+ ", state=" + state + ", price=" + price + ", releaseDate=" + releaseDate + "]";
	}

}
