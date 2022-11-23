package uclm.esi.equipo01.service;

import java.util.List;
import java.util.Optional;

import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.model.Plate;
import uclm.esi.equipo01.model.Restaurant;

/*********************************************************************
*
* Class Name: RestaurantService
* Class description: Dedicated to the management of restaurant and plate information
*
**********************************************************************/

@Service
public class RestaurantService {
	
	private static final String RESTAURANTNOTFOUND = "Restaurante no encontrado";
	private static final String COSTNOTVALID = "Coste no válido";
	private static final String ERRORCOST = "errorCost";
	private static final String PLATENOTFOUND = "Plato no encontrado";
	
	private ValidatorService validatorService;
	
	@Autowired
	public RestaurantService (ValidatorService validatorService) {
		this.validatorService = validatorService;
	}
	
	/*********************************************************************
	*
	* - Method name: addRestaurant
	* - Description of the Method: check the new restaurant data so that they are correct and save it in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New restaurant data
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> addRestaurant(JSONObject jso) {
		
		String name = jso.getString("name");
		String commercialRegister = jso.getString("commercialRegister");
		String cif = jso.getString("cif");
		String address = jso.getString("address");
		String phone = jso.getString("phone");
		String email = jso.getString("email");
		String category = jso.getString("category");
		
		JSONObject response = new JSONObject();
		
		if (!validatorService.isValidEmail(email))
			response.put("errorEmail", "Email no válido");
				
		if (!validatorService.isValidPhone(phone))
			response.put("errorPhone", "Teléfono no válido");
		
		if (!validatorService.isValidCIF(cif))
			response.put("errorCIF", "CIF no válido");
		
		if(response.length() != 0)
			return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
		
		Restaurant restaurant = new Restaurant(name,commercialRegister,cif,address,phone,email,category);	
		Manager.get().getRestaurantRepository().save(restaurant);
		return new ResponseEntity<>("Restaurante añadido correctamente", HttpStatus.OK);
	}
	
	/*********************************************************************
	*
	* - Method name: modifyRestaurant
	* - Description of the Method: check the modify restaurant data so that they are correct and save it in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New restaurant data
	* 		• long ID: ID of the restaurant to modify
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> modifyRestaurant(JSONObject jso, long id) {
		Optional<Restaurant> optRestaurant = Manager.get().getRestaurantRepository().findById(id);	
		if(!optRestaurant.isPresent()) {
			return new ResponseEntity<>(RESTAURANTNOTFOUND, HttpStatus.BAD_REQUEST);
		}
	
		String name = jso.getString("name");
		String commercialRegister = jso.getString("commercialRegister");
		String cif = jso.getString("cif");
		String address = jso.getString("address");
		String phone = jso.getString("phone");
		String email = jso.getString("email");
		String category = jso.getString("category");
		
		JSONObject response = new JSONObject();
		
		if (!validatorService.isValidEmail(email))
			response.put("errorEmail", "Email no válido");
				
		if (!validatorService.isValidPhone(phone))
			response.put("errorPhone", "Teléfono no válido");
		
		if (!validatorService.isValidCIF(cif))
			response.put("errorCIF", "CIF no válido");
		
		if(response.length() != 0)
			return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
		
		Restaurant restaurant = optRestaurant.get();
		restaurant.setName(name);
		restaurant.setCommercialRegister(commercialRegister);
		restaurant.setCif(cif);
		restaurant.setAddress(address);
		restaurant.setPhone(phone);
		restaurant.setEmail(email);
		restaurant.setCategory(category);
		
		Manager.get().getRestaurantRepository().save(restaurant);
		return new ResponseEntity<>("Restaurante añadido correctamente", HttpStatus.OK);	
	}
	
	/*********************************************************************
	*
	* - Method name: deleteRestaurant
	* - Description of the Method: Look for the restaurant by id and if it exits it is deleted
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long ID: ID of the restaurant to delete
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> deleteRestaurant(long id) {
		if (!Manager.get().getRestaurantRepository().existsById(id)) {
			return new ResponseEntity<>(RESTAURANTNOTFOUND, HttpStatus.BAD_REQUEST);
		}
		List<Plate> plates = Manager.get().getPlateRepository().findPlateByRestaurantID(id);
		for (Plate plate : plates) {
			Manager.get().getPlateRepository().deleteById(plate.getId());
		}
		Manager.get().getRestaurantRepository().deleteById(id);
		return new ResponseEntity<>("Restaurante eliminado correctamente", HttpStatus.OK);
	}
	
	/*********************************************************************
	*
	* - Method name: showRestaurant
	* - Description of the Method: Find restaurant in the repository by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the restaurant to look for
	* - Return value: Restaurant
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public Restaurant showRestaurant(long id) {
		Optional<Restaurant> restaurant = Manager.get().getRestaurantRepository().findById(id);
		if (!restaurant.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, RESTAURANTNOTFOUND);
		return restaurant.get();
	}
	
	/*********************************************************************
	*
	* - Method name: showAllRestaurant
	* - Description of the Method: Find all restaurant in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: List <Restaurant>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public List<Restaurant> showAllRestaurants() {
		return Manager.get().getRestaurantRepository().findAll();
	}
	
	/*********************************************************************
	*
	* - Method name: addPlate
	* - Description of the Method: check the new plate data so that they are correct and save it in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New plate data
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> addPlate(JSONObject jso) {
		
		String name = jso.getString("name");
		String photo = jso.getString("photo");
		String description = jso.getString("description");
		double cost = 0;
		JSONObject response = new JSONObject();
		
		try {
			cost = Double.parseDouble(jso.getString("cost"));
		}catch(java.lang.NumberFormatException exception) {
			response.put(ERRORCOST, COSTNOTVALID);
		}
		if(photo.isEmpty()){
			response.put("errorImage", "Imagen no válida");
		}
		boolean veganFriendly = Boolean.parseBoolean(jso.getString("veganFriendly"));
		long restaurantID = Long.parseLong(jso.getString("restaurantID"));
		
		List<Plate> plates = Manager.get().getPlateRepository().findPlateByRestaurantID(restaurantID);
		
		if (validatorService.isRepeatedPlate(plates, name)) {
			response.put("errorName", "Nombre no válido");
		}

		if (cost < 0) {
			response.put(ERRORCOST, COSTNOTVALID);
		}
		
		if(response.length() != 0)
			return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
		
		Plate plate = new Plate(name, photo, description, cost, veganFriendly, restaurantID);	
		Manager.get().getPlateRepository().save(plate);
		return new ResponseEntity<>("Plato añadido correctamente", HttpStatus.OK);
	}

	/*********************************************************************
	*
	* - Method name: modifyPlate
	* - Description of the Method: check the modify plate data so that they are correct and save it in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New plate data
	* 		• long ID: ID of the plate to modify
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> modifyPlate(JSONObject jso, long id) {
		Optional<Plate> optPlate = Manager.get().getPlateRepository().findById(id);	
		if(!optPlate.isPresent()) {
			return new ResponseEntity<>(PLATENOTFOUND, HttpStatus.BAD_REQUEST);
		}
		String name = jso.getString("name");
		String photo = jso.getString("photo");
		String description = jso.getString("description");
		double cost = 0;
		boolean veganFriendly =jso.getBoolean("veganFriendly");
		long restaurantID = Long.parseLong(jso.getString("restaurantID"));
		
		JSONObject response = new JSONObject();
		
		try {
			cost = Double.parseDouble(jso.getString("cost"));
		}catch(java.lang.NumberFormatException exception) {
			response.put(ERRORCOST, COSTNOTVALID);
		}

		if (cost < 0)
			response.put(ERRORCOST, COSTNOTVALID);
		
		if(response.length() != 0)
			return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
		
		Plate plate = optPlate.get();
		plate.setName(name);
		plate.setPhoto(photo);
		plate.setDescription(description);
		plate.setCost(cost);
		plate.setVeganFriendly(veganFriendly);
		plate.setRestaurantID(restaurantID);
		
		Manager.get().getPlateRepository().save(plate);
		return new ResponseEntity<>("Plato modificado correctamente", HttpStatus.OK);
	}

	/*********************************************************************
	*
	* - Method name: deletePlate
	* - Description of the Method: Look for the plate by id and if it exits it is deleted
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long ID: ID of the plate to delete
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> deletePlate(long id) {
		if (!Manager.get().getPlateRepository().existsById(id)) {
			return new ResponseEntity<>(PLATENOTFOUND, HttpStatus.BAD_REQUEST);
		}
		Manager.get().getPlateRepository().deleteById(id);
		return new ResponseEntity<>("Plato eliminado correctamente", HttpStatus.OK);
	}

	/*********************************************************************
	*
	* - Method name: showPlate
	* - Description of the Method: Find plate in the repository by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the plate to look for
	* - Return value: Plate
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public Plate showPlate(long id) {
		Optional<Plate> plate = Manager.get().getPlateRepository().findById(id);
		if (!plate.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PLATENOTFOUND);
		return plate.get();
	}
	
	/*********************************************************************
	*
	* - Method name: showAllPlatesFromRestaurant
	* - Description of the Method: Find all plates in a restaurant in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* 		• long id: ID of the restaurant to look for
	* - Return value: List <Plate>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public List<Plate> showAllPlatesFromRestaurant(long id) {
		JSONObject response = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		List<Plate> plates = Manager.get().getPlateRepository().findPlateByRestaurantID(id);
		for (Plate plate : plates)
			try {
				response.put(String.valueOf(plate.getId()), mapper.writeValueAsString(plate));
			} catch (JsonProcessingException | JSONException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, RESTAURANTNOTFOUND);
			}
		
		return plates;
		
	}
	
	
}