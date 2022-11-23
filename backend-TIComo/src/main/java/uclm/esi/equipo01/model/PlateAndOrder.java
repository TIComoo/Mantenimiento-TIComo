package uclm.esi.equipo01.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import uclm.esi.equipo01.http.Manager;

/*********************************************************************
*
* Class Name: PlateAndOrder
* Class description: Relationship between plates and orders (NxM)
*
**********************************************************************/

@Document(collection = "PlateAndOrder")
public class PlateAndOrder {

	@Transient
	public static final int SEQUENCE_ID = Sequence.PLATEANDORDER.getValue();
	
	@Id
    private long id;
	
	private long plateID;
	private long orderID;
	
	private int quantity;

	public PlateAndOrder() {
		super();
	}

	public PlateAndOrder(long id, long plateID, long orderID, int quantity) {
		super();
		this.id = id;
		this.plateID = plateID;
		this.orderID = orderID;
		this.quantity = quantity;
	}
	
	public PlateAndOrder(long plateID, long orderID, int quantity) {
		super();
		this.setId(Manager.get().generateSequence(SEQUENCE_ID));
		this.plateID = plateID;
		this.orderID = orderID;
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public long getPlateID() {
		return plateID;
	}

	public long getOrderID() {
		return orderID;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setPlateID(long plateID) {
		this.plateID = plateID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
