package uclm.esi.equipo01.http;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.openjson.JSONObject;

import uclm.esi.equipo01.model.Order;
import uclm.esi.equipo01.model.OrderRate;
import uclm.esi.equipo01.model.PlateAndOrder;
import uclm.esi.equipo01.service.OrderService;

/*********************************************************************
*
* Class Name: OrderController
* Class description: Connect between frontend .js classes and backend service classes
*
**********************************************************************/

@CrossOrigin(origins = {"https://ticomo01.web.app", "http://localhost:3000"})
@RestController
@RequestMapping("order")
public class OrderController {

	private static OrderService orderService;
	
	/*********************************************************************
	*
	* - Method name: setOrderService
	* - Description of the Method: Initialize the orderService
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: 
	* 		• OrderService orderService: Class global variable
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Autowired
	public void setOrderService(OrderService orderService) {
		OrderController.orderService = orderService;
	}
	
	/*********************************************************************
	*
	* - Method name: makeOrder
	* - Description of the Method: Call the service to make a order
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: Order info
	* 		• long idClient: id client
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/makeOrder/{id}")
	public ResponseEntity<String> makeOrder(@RequestBody Map<String, Object> info, @PathVariable long id) {
		JSONObject jso = new JSONObject(info);
		return orderService.makeOrder(jso, id);		
	}

	/*********************************************************************
	*
	* - Method name: deleteOrder
	* - Description of the Method: Call the service to delete a order
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long idOrder: id order
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/deleteOrder/{id}")
	public ResponseEntity<String> deleteOrder(@PathVariable long id) {
		return orderService.deleteOrder(id);
	}
	
	/*********************************************************************
	*
	* - Method name: modifyOrder
	* - Description of the Method: Call the service to modify an order. 
	* The only thing that is going to be modified is the rider and the state
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long idOrder: id order
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/modifyOrder/{id}")
	public ResponseEntity<String> modifyOrder(@RequestBody Map<String, Object> info, @PathVariable long id) {
		JSONObject jso = new JSONObject(info);
		return orderService.modifyOrder(jso, id);
	}
	
	/*********************************************************************
	*
	* - Method name: rateOrder
	* - Description of the Method: Call the service to modify a Rider by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: Info containing the rate
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/rateOrder")
	public ResponseEntity<String> rateOrder(@RequestBody Map<String, Object> info) {
		JSONObject jso = new JSONObject(info);
		return orderService.rateOrder(jso);		
	}
	
	/*********************************************************************
	*
	* - Method name: showRiderRate
	* - Description of the Method: Call the service to check if there is a rate
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the rider
	* - Return value: double
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showOrderRate/{id}")
	public OrderRate showOrderRate(@PathVariable long id) {
		return orderService.showOrderRate(id);		
	}
	
	/*********************************************************************
	*
	* - Method name: setSimultaneousRiderOrders
	* - Description of the Method: Call the service to set the maximum number of simultaneous orders
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: Info containing the number of simultaneous orders
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/setSimultaneousRiderOrders")
	public ResponseEntity<String> setSimultaneousRiderOrders(@RequestBody Map<String, Object> info) {
		JSONObject jso = new JSONObject(info);
		return orderService.setSimultaneousRiderOrders(jso);		
	}
	
	/*********************************************************************
	*
	* - Method name: showAllOrdersByRestaurant
	* - Description of the Method: Call the service to receive a list with all orders from a restaurant
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: Info containing the rate
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showAllOrders")
	public List<Order> showAllOrders() {
		return orderService.showAllOrders();		
	}
	
	/*********************************************************************
	*
	* - Method name: showAllOrdersByRestaurant
	* - Description of the Method: Call the service to receive a list with all orders from a restaurant
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: Info containing the rate
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showAllOrdersByRestaurant/{id}")
	public List<Order> showAllOrdersByRestaurant(@PathVariable long id) {
		return orderService.showAllOrdersByRestaurant(id);		
	}
	/*********************************************************************
	*
	* - Method name: showAllOrdersByClient
	* - Description of the Method: Call the service to receive a list with all orders from a client
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: Info containing the rate
	* - Return value: ResponseEntity<Order>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showAllOrdersByClient/{id}")
	public List<Order> showAllOrdersByClient(@PathVariable long id ){
		return orderService.showAllOrdersByClient(id);	
	}
	/*********************************************************************
	*
	* - Method name: showAllOrdersByRider
	* - Description of the Method: Call the service to receive a list with all orders from a rider
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: Info containing the rate
	* - Return value: ResponseEntity<Order>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showAllOrdersByRider/{id}")
	public List<Order>showAllOrdersByRider(@PathVariable long id ){
		return orderService.showAllOrdersByRider(id);	
	}
	
	@GetMapping("/showPlatesByOrder/{id}")
	public List<PlateAndOrder>showPlatesByOrder(@PathVariable long id ){
		return orderService.showPlatesByOrder(id);	
	}
	/*********************************************************************
	*
	* - Method name: showOrder
	* - Description of the Method: Call the service to show an order  by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the order to look for
	* - Return value: Order
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showOrder/{id}")
	public Order showOrder(@PathVariable long id){		
		return OrderService.showOrder(id);	
	}
	
}

