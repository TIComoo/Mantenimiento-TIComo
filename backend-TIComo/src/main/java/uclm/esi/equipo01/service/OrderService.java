package uclm.esi.equipo01.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.openjson.JSONObject;

import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.model.Order;
import uclm.esi.equipo01.model.OrderRate;
import uclm.esi.equipo01.model.PlateAndOrder;
import uclm.esi.equipo01.model.Restaurant;
import uclm.esi.equipo01.model.Rider;
import uclm.esi.equipo01.model.Sequence;
import uclm.esi.equipo01.model.State;

/*********************************************************************
*
* Class Name: OrderService
* Class description: Dedicated to the management of Order information
*
**********************************************************************/
@Service
public class OrderService {

	/*********************************************************************
	*
	* - Method name: makeOrder
	* - Description of the Method: add a new Order to the database and add the plates from the order
	* to the database in PlateAndOrder
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New restaurant data
	* 		• long clientID: client that is making the order
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> makeOrder(JSONObject jso, long clientID) {
		long restaurantID = Long.parseLong(jso.getString("restaurantID"));
		double totalPrice = 0;
		JSONObject plates = new JSONObject(jso.getString("cart"));
		Iterator<String> keys = plates.keys();
		
		Order order = new Order(clientID, restaurantID);
		
		while(keys.hasNext()) {
			String key = keys.next();
			JSONObject aux = new JSONObject(plates.get(key));
			double price = Double.parseDouble(aux.getString("price"));
			int quantity = Integer.parseInt(aux.getString("quantity"));
			totalPrice += price*quantity;
			
			PlateAndOrder plateAndOrder = new PlateAndOrder(Long.parseLong(key), order.getId(), quantity);
			
			Manager.get().getPlateAndOrderRepository().save(plateAndOrder);
		}
		
		order.setPrice(totalPrice);
		Manager.get().getOrderRepository().save(order);
		return new ResponseEntity<>("Pedido añadido correctamente", HttpStatus.OK);
	}

	/*********************************************************************
	*
	* - Method name: deleteOrder
	* - Description of the Method: delete a order in the database by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long idOrder: id order to delete
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> deleteOrder(long idOrder) {
		if (!Manager.get().getOrderRepository().existsById(idOrder)) {
			return new ResponseEntity<>("Plato no encontrado", HttpStatus.BAD_REQUEST);
		}
		Manager.get().getOrderRepository().deleteById(idOrder);
		return new ResponseEntity<>("Plato eliminado correctamente", HttpStatus.OK);
	}

	/*********************************************************************
	*
	* - Method name: rateOrder
	* - Description of the Method: rate a order and save in the DB
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: info order
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> rateOrder(JSONObject jso) {
		JSONObject jsoOrder = (JSONObject) jso.get("order");
		long orderID = jsoOrder.getLong("id");
		long clientID = jsoOrder.getLong("clientID");
		long riderID = jsoOrder.getLong("riderID");
		long restaurantID = jsoOrder.getLong("restaurantID");
		String strState = jsoOrder.getString("state");
		State state;
		switch(strState){
		case "NEW":
			state = State.NEW;
			break;
		case "ONTHEWAY":
			state = State.ONTHEWAY;
			break;
		default:
			state = State.DELIVERED;
			break;
		}
		double price = jsoOrder.getDouble("price");
		LocalDateTime releaseDate = LocalDateTime.parse(jsoOrder.getString("releaseDate"));		
		Order order = new Order(orderID, clientID, restaurantID, riderID, state, price, releaseDate);
		int rateRestaurant = Integer.parseInt(jso.getString("rateRestaurant"));
		int rateRider = Integer.parseInt(jso.getString("rateRider"));
		String description = jso.getString("description");	
		OrderRate orderRate = new OrderRate(order, rateRestaurant, rateRider, description);	
		Manager.get().getOrderRateRepository().save(orderRate);
		Optional<Restaurant> optRestaurat = Manager.get().getRestaurantRepository().findById(restaurantID);
		if(optRestaurat.isPresent())
			saveAverageRateRestaurant(optRestaurat.get());
		Optional<Rider> optRider = Manager.get().getRiderRepository().findById(riderID);
		if(optRider.isPresent())
			saveAverageRateRider(optRider.get());
		return new ResponseEntity<>("Pedido valorado correctamente", HttpStatus.OK);
	}
	
	/*********************************************************************
	*
	* - Method name: saveAverageRateRestaurant
	* - Description of the Method: Save average rate restaurant
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Restaurant restaurant
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public void saveAverageRateRestaurant(Restaurant restaurant) {
		List<OrderRate> orderRates = Manager.get().getOrderRateRepository().findByRestaurantID(restaurant.getId());
		List<Integer> rates = new ArrayList<>();
		for (OrderRate orderRate : orderRates) {
			rates.add(orderRate.getRateRestaurant());
		}
		restaurant.setAverageRate(calculateAverageRate(rates));
		Manager.get().getRestaurantRepository().save(restaurant);
	}
	
	/*********************************************************************
	*
	* - Method name: saveAverageRateRider
	* - Description of the Method: Save average rider restaurant
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Rider rider
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public void saveAverageRateRider(Rider rider) {
		List<OrderRate> orderRates = Manager.get().getOrderRateRepository().findByRiderID(rider.getId());
		List<Integer> rates = new ArrayList<>();
		for (OrderRate orderRate : orderRates) {
			rates.add(orderRate.getRateRestaurant());
		}
		rider.setAverageRate(calculateAverageRate(rates));
		Manager.get().getRiderRepository().save(rider);
	}


	public List<Order> showAllOrders() {
		return Manager.get().getOrderRepository().findAll();
	}

	/*********************************************************************
	*
	* - Method name: showAllOrdersByRestaurant
	* - Description of the Method: Find all orders from a restaurant in the repository. 
	* This is usefull for the admin to see the orders of a restaurant
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: List <Restaurant>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public List<Order> showAllOrdersByRestaurant(long restaurantID) {
		return Manager.get().getOrderRepository().findByRestaurantID(restaurantID);
	}
	
	/*********************************************************************
	*
	* - Method name: showAllOrdersByClient
	* - Description of the Method: Find all orders from a client in the repository. 
	* This is usefull for the admin to see the orders of a restaurant
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: List <Order>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/	
	public List<Order> showAllOrdersByClient(long clientID) {
		return Manager.get().getOrderRepository().findByClientID(clientID);
	}
	
	/*********************************************************************
	*
	* - Method name: showOrder
	* - Description of the Method: Find order in the repository by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the order to look for
	* - Return value: Order
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public static Order showOrder(long id) {
		Optional<Order> order= Manager.get().getOrderRepository().findById(id);
		if (!order.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido no encontrados");
		return order.get();
	}
	/*********************************************************************
	*
	* - Method name: showAllOrdersByRider
	* - Description of the Method: Find all orders from a Rider in the repository. 
	* This is usefull for the admin to see the orders of a Rider
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: List <Order>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/	
	
	public List<Order> showAllOrdersByRider(long riderID) {
		return Manager.get().getOrderRepository().findByRiderID(riderID);
	}
	
	public List<PlateAndOrder> showPlatesByOrder(long orderID) {
		return Manager.get().getPlateAndOrderRepository().findPlateAndOrderByOrderID(orderID);
	}

	/*********************************************************************
	*
	* - Method name: modifyOrder
	* - Description of the Method: Modify an orders 
	* The only thing that is going to be modified is the rider and the state
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: List <Restaurant>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public ResponseEntity<String> modifyOrder(JSONObject jso, long id) {
		Optional<Order> optOrder = Manager.get().getOrderRepository().findById(id);	
		if(!optOrder.isPresent()) {
			return new ResponseEntity<>("Pedido no encontrado", HttpStatus.BAD_REQUEST);
		}
		long riderID = Long.parseLong(jso.getString("riderID"));
		String state = jso.getString("state");
		
		Order order = optOrder.get();
		order.setRiderID(riderID);
		
		switch(state){
		case "NEW":
			order.setState(State.NEW);
			break;
		case "ONTHEWAY":
			order.setState(State.ONTHEWAY);
			break;
		default:
			order.setState(State.DELIVERED);
			break;
		}
		
		Manager.get().getOrderRepository().save(order);
		return new ResponseEntity<>("Pedido modificado correctamente", HttpStatus.OK);
	}
	
	/*********************************************************************
	*
	* - Method name: calculateAverageRate
	* - Description of the Method: Obtain average rate
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• List<Integer> rates: list of rates
	* - Return value: double
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public double calculateAverageRate(List<Integer> rates) {
		double sum = 0;
		if(rates.isEmpty()) {
			return 0;
		}else {
		    for (int i=0; i<rates.size(); i++) {
		    	sum += rates.get(i);
		    }	    
		}    
	    return (sum/rates.size());
	}
	
	/*********************************************************************
	*
	* - Method name: setSimultaneousRiderOrders
	* - Description of the Method: Call the service to set the maximum number of simultaneous orders
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New number of simultaneous orders
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> setSimultaneousRiderOrders(JSONObject jso) {
		int simultaneousRiderOrders = Integer.parseInt(jso.getString("simultaneousRiderOrders"));	
		if (simultaneousRiderOrders < 0) {		
			return new ResponseEntity<>( "Número de pedidos no válido", HttpStatus.BAD_REQUEST);
		}
		Manager.get().setSequence(Sequence.SIMULTANEOUS_NUMBER_OF_ORDERS.getValue(), simultaneousRiderOrders);
		return new ResponseEntity<>("Número de pedidos simultaneos actualizado correctamente", HttpStatus.OK);
	}

	public OrderRate showOrderRate(long id) {
		Optional<OrderRate> orderRates = Manager.get().getOrderRateRepository().findById(id);
		if (!orderRates.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valoración no encontrada");
		return orderRates.get();
	}


}
