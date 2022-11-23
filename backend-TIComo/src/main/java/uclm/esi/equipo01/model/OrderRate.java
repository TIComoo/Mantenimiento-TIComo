package uclm.esi.equipo01.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "OrdersRates")
public class OrderRate {
	
	@Id
    private long orderID;
	
	private long restaurantID;
	private long riderID;
	
	private int rateRestaurant;
	private int rateRider;
	private String description;
	
	public OrderRate() {
		super();
	}

	public OrderRate(Order order, int rateRestaurant, int rateRider, String description) {
		this.orderID = order.getId();
		this.restaurantID = order.getRestaurantID();
		this.riderID = order.getRiderID();
		this.rateRestaurant = rateRestaurant;
		this.rateRider = rateRider;
		this.description = description;
	}

	public long getOrderID() {
		return orderID;
	}
	
	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}
	
	public long getRestaurantID() {
		return restaurantID;
	}
	
	public void setRestaurantID(long restaurantID) {
		this.restaurantID = restaurantID;
	}
	
	public long getRiderID() {
		return riderID;
	}
	
	public void setRiderID(long riderID) {
		this.riderID = riderID;
	}
	
	public int getRateRestaurant() {
		return rateRestaurant;
	}

	public void setRateRestaurant(int rateRestaurant) {
		this.rateRestaurant = rateRestaurant;
	}

	public int getRateRider() {
		return rateRider;
	}

	public void setRateRider(int rateRider) {
		this.rateRider = rateRider;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
