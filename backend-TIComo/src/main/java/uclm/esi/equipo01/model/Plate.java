package uclm.esi.equipo01.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.Base64;

import uclm.esi.equipo01.http.Manager;

/*********************************************************************
*
* Class Name: Plate
* Class description: Dedicated to the management plate methods and data
*
**********************************************************************/

@Document(collection = "Plates")
public class Plate {
	
	@Transient
	public static final int SEQUENCE_ID = Sequence.PLATE.getValue();
	
	@Id
    private long id;
	
	private String name;
	private String photo;
	private String description;
	private double cost;
	private boolean veganFriendly;
	private long restaurantID;
	
	public Plate() {
		super();
	}
	
	/*********************************************************************
	*
	* - Method name: Plate
	* - Description of the Method: Client class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Long id: plate id
	* 		• String name: plate name
	* 		• String photo: plate photo
	* 		• String description: plate description
	* 		• double cost: plate cost
	* 		• boolean veganFriendly: if the plate is veganFriendly
	* 		• long restaurantID: plate restaurant id
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public Plate(long id, String name, String photo, String description, double cost, boolean veganFriendly, long restaurantID) {
		super();
		this.id = id;
		this.name = name;
		this.photo = photo;
		this.description = description;
		this.cost = cost;
		this.veganFriendly = veganFriendly;
		this.restaurantID = restaurantID;
	}
	
	/*********************************************************************
	*
	* - Method name: Plate
	* - Description of the Method: Client class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String name: plate name
	* 		• String photo: plate photo
	* 		• String description: plate description
	* 		• double cost: plate cost
	* 		• boolean veganFriendly: if the plate is veganFriendly
	* 		• long restaurantID: plate restaurant id
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public Plate(String name, String photo, String description, double cost, boolean veganFriendly, long restaurantID) {
		super();
		this.setId(Manager.get().generateSequence(SEQUENCE_ID));
		this.name = name;
		this.photo = photo;
		this.description = description;
		this.cost = cost;
		this.veganFriendly = veganFriendly;
		this.restaurantID = restaurantID;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		byte[] b64 = Base64.getEncoder().encode(photo);
		this.photo = new String(b64);	
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setVeganFriendly(boolean veganFriendly) {
		this.veganFriendly = veganFriendly;
	}

	public void setRestaurantID(long restaurantID) {
		this.restaurantID = restaurantID;
	}

	public String getDescription() {
		return description;
	}

	public double getCost() {
		return cost;
	}

	public boolean isVeganFriendly() {
		return veganFriendly;
	}

	public long getRestaurantID() {
		return restaurantID;
	}

	@Override
	public String toString() {
		return "Plate [id=" + id + ", name=" + name + ", photo=" + photo + ", description=" + description + ", cost="
				+ cost + ", veganFriendly=" + veganFriendly + ", restaurantID=" + restaurantID + "]";
	}
	
}