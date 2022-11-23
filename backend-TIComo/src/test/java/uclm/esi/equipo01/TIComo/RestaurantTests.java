package uclm.esi.equipo01.TIComo;

import static org.junit.Assert.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.HashMap;
import java.util.Map;

import com.github.openjson.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.http.RestaurantController;
import uclm.esi.equipo01.model.DatabaseSequence;
import uclm.esi.equipo01.model.Plate;
import uclm.esi.equipo01.model.Restaurant;
import uclm.esi.equipo01.service.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/*********************************************************************
*
* Class Name: Restaurant Test
* Class description: Dedicated to the restaurant test
*
**********************************************************************/

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class RestaurantTests {
	
	@Autowired
	private RestaurantService restaurantService;
	
	private RestaurantController controller;
	private RestaurantService service;
	
    private static MongoOperations mongoOperations;

	/*********************************************************************
	*
	* - Method name: setMongoOperations
	* - Description of the Method: initialize the data of the DB
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
    	RestaurantTests.mongoOperations = mongoOperations;
    }
    
	/*********************************************************************
	*
	* - Method name: initiate
	* - Description of the Method: remove all data DB after execution
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
    @AfterClass
    public static void initiate() {
    	Manager.get().getRestaurantRepository().deleteAll();
    	mongoOperations.findAndRemove(query(where("_id").is(Restaurant.SEQUENCE_ID)), DatabaseSequence.class);
    	Manager.get().getPlateRepository().deleteAll();
    	mongoOperations.findAndRemove(query(where("_id").is(Plate.SEQUENCE_ID)), DatabaseSequence.class);
    }
	
	/*********************************************************************
	*
	* - Method name: setUp
	* - Description of the Method: initialize the data of the service and controller before execution
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Before
    public void setUp() {
        service = Mockito.mock(RestaurantService.class);
        controller = new RestaurantController();
 
    }
	
	/*********************************************************************
	*
	* - Method name: test01 to test02
	* - Description of the Method: Tests carried out to add a restaurant in restaurant controller
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test01() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "666 666 666");
		info.put("email", "comida@gmail.com");
		info.put("category", "Mexicano");
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.addRestaurant(jso)).thenReturn(new ResponseEntity<>("El restaurante introducido incorrectamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = controller.addRestaurant(info);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test02() {
	    Map<String, Object> info = new HashMap<String, Object>();
	 
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("email", "@gmail.com");
		info.put("cif", "123456X");
		info.put("address", "XXX");
		info.put("phone", "122 223 234");
		info.put("category", "M3xicano");
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.addRestaurant(jso)).thenReturn(new ResponseEntity<>("El restaurante introducido incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = controller.addRestaurant(info);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test03 to test11
	* - Description of the Method: Tests carried out to add a restaurant in restaurant service
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test03() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("email", "comida@gmail.com");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "666 666 666");
		info.put("category", "Mexicano");
		
		ResponseEntity<String> result = restaurantService.addRestaurant(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test04() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("email", "ñamñam123$gmail.com");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "664567091");
		info.put("category", "Mexicano");
		
		ResponseEntity<String> result = restaurantService.addRestaurant(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test05() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("email", "comida1235@gmail.com");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "765 98 34 56");
		info.put("category", "Mexicano");
		
		ResponseEntity<String> result = restaurantService.addRestaurant(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test06() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("email", "@gmail.com");
		info.put("cif", "123456X");
		info.put("address", "XXX");
		info.put("phone", "122 223 234");
		info.put("category", "M3xicano");
		
		ResponseEntity<String> result = restaurantService.addRestaurant(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test07() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("email", "Comida.gmail.com");
		info.put("cif", "123456789");
		info.put("address", "XXX");
		info.put("phone", "666 666 66");
		info.put("category", "M3xicano");
		
		ResponseEntity<String> result = restaurantService.addRestaurant(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test08() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("email", "comida@com");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "122 345 567 7");
		info.put("category", "M3xicano");
		
		ResponseEntity<String> result = restaurantService.addRestaurant(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test09() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("email", "comida@com");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "999 999 999");
		info.put("category", "M3xicano");
		
		ResponseEntity<String> result = restaurantService.addRestaurant(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test10() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("email", "comida@com");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "777 777 777");
		info.put("category", "M3xicano");
		
		ResponseEntity<String> result = restaurantService.addRestaurant(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test11() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("email", "comida@com");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "888 888 888");
		info.put("category", "M3xicano");
		
		ResponseEntity<String> result = restaurantService.addRestaurant(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test12 to test13
	* - Description of the Method: Tests carried out to modify a restaurant in restaurant controller
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test12() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "666 666 666");
		info.put("email", "comida@gmail.com");
		info.put("category", "Mexino");
		
		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.modifyRestaurant(jso, id)).thenReturn(new ResponseEntity<>("El restaurante modificado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = controller.modifyRestaurant(info, id);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test13() {
	    Map<String, Object> info = new HashMap<String, Object>();
	 
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("email", "comida@com");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "888 888 888");
		info.put("category", "M3xicno");
		
		long id = 2;
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.modifyRestaurant(jso, id)).thenReturn(new ResponseEntity<>("El restaurante modificado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = controller.modifyRestaurant(info, id);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test14 to test18
	* - Description of the Method: Tests carried out to modify a restaurant in restaurant service
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test14() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "Restaurante");
		info.put("commercialRegister", "Comida rápida");
		info.put("email", "comida123@gmail.com");
		info.put("cif", "87654321T");
		info.put("address", "Calle Calatrava, 20");
		info.put("phone", "690 656 986");
		info.put("category", "Mexicano Especial");
		
		long id = 1;
		
		ResponseEntity<String> result = restaurantService.modifyRestaurant(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante modificado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test15() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("cif", "123456X");
		info.put("address", "XXX");
		info.put("phone", "666 666 666");
		info.put("email", "comida@gmail.com");
		info.put("category", "Mexino");
		
		long id = 2;
		
		ResponseEntity<String> result = restaurantService.modifyRestaurant(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test16() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "466 666 666");
		info.put("email", "comida@gmail.com");
		info.put("category", "Mexino");
		
		long id = 2;
		
		ResponseEntity<String> result = restaurantService.modifyRestaurant(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test17() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "666 666 666");
		info.put("email", "@gmail.com");
		info.put("category", "Mexino");
		
		long id = 2;
		
		ResponseEntity<String> result = restaurantService.modifyRestaurant(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test18() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("commercialRegister", "XXX");
		info.put("cif", "12345678X");
		info.put("address", "XXX");
		info.put("phone", "666 666 666");
		info.put("email", "comida@gmail.com");
		info.put("category", "M3xino");
		
		long id = 2;
		
		ResponseEntity<String> result = restaurantService.modifyRestaurant(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante modificado incorrectamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
		
	/*********************************************************************
	*
	* - Method name: test19 to test20
	* - Description of the Method: Tests carried out to add a plate in restaurant controller
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test19() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("name", "XXX");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "34.5");
		info.put("veganFriendly", "true");
		info.put("restaurantID", "1");
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.addPlate(jso)).thenReturn(new ResponseEntity<>("Plato introducido correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = controller.addPlate(info);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test20() {
	    Map<String, Object> info = new HashMap<String, Object>();
	 
		info.put("name", "XXX");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "-23.67");
		info.put("veganFriendly", "false");
		info.put("restaurantID", "200");
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.addPlate(jso)).thenReturn(new ResponseEntity<>("Plato introducido incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = controller.addPlate(info);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test21 to test25
	* - Description of the Method: Tests carried out to add a plate in restaurant service
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test21() {
		
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXXX");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "34.5");
		info.put("veganFriendly", "true");
		info.put("restaurantID", "1");
		
		ResponseEntity<String> result = restaurantService.addPlate(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Plato introducido correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test22() {
		
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "45");
		info.put("veganFriendly", "false");
		info.put("restaurantID", "3");
		
		ResponseEntity<String> result = restaurantService.addPlate(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Plato introducido correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test23() {
		
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "0.5");
		info.put("veganFriendly", "true");
		info.put("restaurantID", "2");
		
		ResponseEntity<String> result = restaurantService.addPlate(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Plato introducido correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test24() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "-23.67");
		info.put("veganFriendly", "false");
		info.put("restaurantID", "99");
		
		ResponseEntity<String> result = restaurantService.addPlate(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Plato introducido incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test25() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "54,5");
		info.put("veganFriendly", "true");
		info.put("restaurantID", "45");
		
		ResponseEntity<String> result = restaurantService.addPlate(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Plato introducido incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test26 to test27
	* - Description of the Method: Tests carried out to modify a plate in restaurant controller
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test26() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("name", "XXX");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "34.5");
		info.put("veganFriendly", "true");
		info.put("restaurantID", "1");
		
		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.modifyPlate(jso, id)).thenReturn(new ResponseEntity<>("El plato modificado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = controller.modifyPlate(info, id);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test27() {
	    Map<String, Object> info = new HashMap<String, Object>();
	 
		info.put("name", "XXX");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "-23.67");
		info.put("veganFriendly", "false");
		info.put("restaurantID", "4");
		
		long id = 2;
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.modifyPlate(jso, id)).thenReturn(new ResponseEntity<>("El plato modificado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = controller.modifyPlate(info, id);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test28 to test31
	* - Description of the Method: Tests carried out to modify a plate in service controller
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test28() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("name", "Plato general");
		info.put("photo", "imagenPerfecta.png");
		info.put("description", "Lleva cebolla, huevo y tomate");
		info.put("cost", "3.56");
		info.put("veganFriendly", "true");
		info.put("restaurantID", "1");
		
		long id = 2;
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.modifyPlate(jso, id)).thenReturn(new ResponseEntity<>("Plato modificado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = controller.modifyPlate(info, id);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test29() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "-34.5");
		info.put("veganFriendly", "true");
		info.put("restaurantID", "1");
		
		long id = 2;
		
		ResponseEntity<String> result = restaurantService.modifyPlate(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Plato modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test30() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "Patatas");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "45678909876543");
		info.put("veganFriendly", "true");
		info.put("restaurantID", "1");
		
		long id = 2;
		
		ResponseEntity<String> result = restaurantService.modifyPlate(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Plato modificado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test31() {
		JSONObject info = new JSONObject();
	    
		info.put("name", "XXX");
		info.put("photo", "XXX");
		info.put("description", "XXX");
		info.put("cost", "-456434234242");
		info.put("veganFriendly", "true");
		info.put("restaurantID", "1");
		
		long id = 2;
		
		ResponseEntity<String> result = restaurantService.modifyPlate(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Plato modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test32 to test33
	* - Description of the Method: Tests carried out to delete a plate in restaurant controller
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test32() {
		
		long id = 1;
		
		Mockito.when(service.deletePlate(id)).thenReturn(new ResponseEntity<>("El plato eliminado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = controller.deletePlate(id);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test33() {
		
		long id = 20;
		
		Mockito.when(service.deletePlate(id)).thenReturn(new ResponseEntity<>("El plato eliminado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = controller.deletePlate(id);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test34 to test39
	* - Description of the Method: Tests carried out to delete a plate in restaurant service
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test34() {
		
		long id = 3;
		
		ResponseEntity<String> result = restaurantService.deletePlate(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El plato eliminado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test35() {
		
		long id = 2;
		
		ResponseEntity<String> result = restaurantService.deletePlate(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El plato eliminado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test36() {
		
		long id = 1;
		
		ResponseEntity<String> result = restaurantService.deletePlate(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El plato eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test37() {
		
		long id = 5;
		
		ResponseEntity<String> result = restaurantService.deletePlate(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El plato eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test38() {
		
		long id = 30;
		
		ResponseEntity<String> result = restaurantService.deletePlate(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El plato eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test39() {
		
		long id = 100;
	
		ResponseEntity<String> result = restaurantService.deletePlate(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El plato eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test40 to test41
	* - Description of the Method: Tests carried out to delete a restaurant in restaurant controller
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test40() {
		
		long id = 1;
		
		Mockito.when(service.deleteRestaurant(id)).thenReturn(new ResponseEntity<>("El restaurante eliminado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = controller.deleteRestaurant(id);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
		
	@Test
	public void test41() {
		
		long id = 20;
	
		Mockito.when(service.deleteRestaurant(id)).thenReturn(new ResponseEntity<>("El restaurante eliminado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = controller.deleteRestaurant(id);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test42 to test47
	* - Description of the Method: Tests carried out to delete a restaurant in restaurant service
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test42() {
		
		long id = 3;
		
		ResponseEntity<String> result = restaurantService.deleteRestaurant(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante eliminado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
		
	@Test
	public void test43() {
		
		long id = 2;
		
		ResponseEntity<String> result = restaurantService.deleteRestaurant(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante eliminado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test44() {
		
		long id = 1;
		
		ResponseEntity<String> result = restaurantService.deleteRestaurant(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test45() {
		
		long id = 5;
		
		ResponseEntity<String> result = restaurantService.deleteRestaurant(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test46() {
		
		long id = 30;
		
		ResponseEntity<String> result = restaurantService.deleteRestaurant(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test47() {
		
		long id = 100;
		
		ResponseEntity<String> result = restaurantService.deleteRestaurant(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
}
