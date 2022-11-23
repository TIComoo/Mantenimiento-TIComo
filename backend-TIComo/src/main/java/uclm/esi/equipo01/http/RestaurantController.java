package uclm.esi.equipo01.http;

import java.util.List;
import java.util.Map;

import com.github.openjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uclm.esi.equipo01.model.Plate;
import uclm.esi.equipo01.model.Restaurant;
import uclm.esi.equipo01.service.RestaurantService;

/*********************************************************************
*
* Class Name: RestaurantController
* Class description: Connect between frontend .js classes and backend service classes
*
**********************************************************************/

@CrossOrigin(origins = {"https://ticomo01.web.app", "http://localhost:3000"})
@RestController
@RequestMapping("restaurant")
public class RestaurantController {

	private static RestaurantService restaurantService;
	
	
	/*********************************************************************
	*
	* - Method name: setRestaurantService
	* - Description of the Method: Initialize the restaurantService
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: 
	* 		• RestaurantService restaurantService: Class global variable
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Autowired
	public void setRestaurantService(RestaurantService restaurantService) {
		RestaurantController.restaurantService = restaurantService;
	}
	
	/*********************************************************************
	*
	* - Method name: addRestaurant
	* - Description of the Method: Call the service to add a new restaurant
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: New restaurant data
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/addRestaurant")
	public ResponseEntity<String> addRestaurant(@RequestBody Map<String, Object> info){
		JSONObject jso = new JSONObject(info);
		return restaurantService.addRestaurant(jso);
	}
	
	/*********************************************************************
	*
	* - Method name: modifyRestaurant
	* - Description of the Method: Call the service to modify a restaurant by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: Restaurant modified data
	* 		• long id: ID of the restaurant to modify
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/modifyRestaurant/{id}")
	public ResponseEntity<String> modifyRestaurant(@RequestBody Map<String, Object> info, @PathVariable long id){
		JSONObject jso = new JSONObject(info);
		return restaurantService.modifyRestaurant(jso, id);		
	}
	
	/*********************************************************************
	*
	* - Method name: deleteRestaurant
	* - Description of the Method: Call the service to delete a restaurant by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the restaurant to delete
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/deleteRestaurant/{id}")
	public ResponseEntity<String> deleteRestaurant(@PathVariable long id){		
		return restaurantService.deleteRestaurant(id);		
	}
	
	/*********************************************************************
	*
	* - Method name: showRestaurant
	* - Description of the Method: Call the service to show a restaurant by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the restaurant to look for
	* - Return value: Restaurant
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showRestaurant/{id}")
	public Restaurant showRestaurant(@PathVariable long id){
		return restaurantService.showRestaurant(id);	
	}
	
	/*********************************************************************
	*
	* - Method name: showAllRestaurants
	* - Description of the Method: Call the service to show all restaurants
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: List <Restaurant>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showAllRestaurants")
	public List<Restaurant> showAllRestaurants(){		
		return restaurantService.showAllRestaurants();
	}
	
	/*********************************************************************
	*
	* - Method name: showPlate
	* - Description of the Method: Call the service to show a plate by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the plate to look for
	* - Return value: Plate
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showPlate/{id}")
	public Plate showPlate(@PathVariable long id){		
		return restaurantService.showPlate(id);	
	}
	
	/*********************************************************************
	*
	* - Method name: addPlate	
	* - Description of the Method: Call the service to add a new plate
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: New plate data
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/addPlate")
	public ResponseEntity<String> addPlate(@RequestBody Map<String, Object> info){

		JSONObject jso = new JSONObject(info);
		return restaurantService.addPlate(jso);	
	}
	
	/*********************************************************************
	*
	* - Method name: deletePlate
	* - Description of the Method: Call the service to delete a plate by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the plate to delete
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/deletePlate/{id}")
	public ResponseEntity<String> deletePlate(@PathVariable long id){		
		return restaurantService.deletePlate(id);		
	}

	/*********************************************************************
	*
	* - Method name: modifyPlate
	* - Description of the Method: Call the service to modify a plate by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: Plate modified data
	* 		• long id: ID of the plate to modify
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/modifyPlate/{id}")
	public ResponseEntity<String> modifyPlate(@RequestBody Map<String, Object> info, @PathVariable long id) {
		JSONObject jso = new JSONObject(info);
		return restaurantService.modifyPlate(jso, id);	
	}
	
	/*********************************************************************
	*
	* - Method name: showAllPlatesFromRestaurant
	* - Description of the Method: Call the service to show all plate from a restaurant
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the restaurant of the plates being searched
	* - Return value: List <Plate>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showAllPlatesFromRestaurant/{id}")
	public List<Plate> showAllPlatesFromRestaurant(@PathVariable long id){
		return restaurantService.showAllPlatesFromRestaurant(id);
	}
	

}