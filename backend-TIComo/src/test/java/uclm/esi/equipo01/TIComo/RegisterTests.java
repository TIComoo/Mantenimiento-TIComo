package uclm.esi.equipo01.TIComo;

import static org.junit.Assert.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.github.openjson.JSONObject;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.http.UserController;
import uclm.esi.equipo01.model.Client;
import uclm.esi.equipo01.model.DatabaseSequence;
import uclm.esi.equipo01.service.ClientService;
import uclm.esi.equipo01.service.UserService;

/*********************************************************************
*
* Class Name: RegisterService
* Class description: Dedicated to the management of register information
*
**********************************************************************/

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class RegisterTests {
	
	@Autowired
	private ClientService clientService;
	
	private UserController controller;
	private UserService service;
	
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
    	RegisterTests.mongoOperations = mongoOperations;
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
    	Manager.get().getClientRepository().deleteAll();
    	mongoOperations.findAndRemove(query(where("_id").is(Client.SEQUENCE_ID)), DatabaseSequence.class);
    	UserController controller = new UserController();
		Map<String, Object> info = new HashMap<>();
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "client123*CLIENT");
		info.put("name","Carlos");
		info.put("surname","Pulido");
		info.put("nif","12345678A");
		info.put("address","Paseo de la Universidad, 4, 13071 Ciudad Real");
		info.put("phone","666666666");
		info.put("role", "CLIENT");
		controller.register(info);
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
        service = Mockito.mock(UserService.class);
        controller = new UserController();
    }
	
	/*********************************************************************
	*
	* - Method name: test01 to test02
	* - Description of the Method: Tests carried out to register in user controller
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
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "55692512X");
		info.put("address", "XXX");
		info.put("phone", "666 777 778");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "Pepito23@");
		info.put("role", "CLIENT");
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.register(jso)).thenReturn(new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = controller.register(info);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test02() {
		Map<String, Object> info = new HashMap<String, Object>();
	    
	    info.put("name", "Lucía");
	    info.put("surname", "Martínez");
	    info.put("nif", "5569251X");
	    info.put("address", "XXX");
	    info.put("phone", "66677777");
	    info.put("email", "pepito45@gmail.com");
	    info.put("password", "pepito23@");
	    info.put("role", "CLIENT");
	    
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.register(jso)).thenReturn(new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = controller.register(info);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test03 to test70
	* - Description of the Method: Tests carried out to register in client service
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
	    
	    info.put("name", "Lucía");
	    info.put("surname", "Martínez");
	    info.put("nif", "5569251X");
	    info.put("address", "XXX");
	    info.put("phone", "66677777");
	    info.put("email", "pepito45@gmail.com");
	    info.put("password", "pepito23@");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	

	@Test
	public void test04() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía");
	    info.put("surname", "M4rtinez");
	    info.put("nif", "-5556925X");
	    info.put("address", "XXX");
	    info.put("phone", "566 777 777");
	    info.put("email", "pepito.45@gmail.com");
	    info.put("password", "PEPITO1234@");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test05() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía");
	    info.put("surname", "Martinez Galvez");
	    info.put("nif", "55569251X");
	    info.put("address", "XXX");
	    info.put("phone", "6667777778");
	    info.put("email", "pepito@gmail.com");
	    info.put("password", "PepitoGrillo&");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test06() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía");
	    info.put("surname", "M4rtinez Galvez");
	    info.put("nif", "55569251");
	    info.put("address", "XXX");
	    info.put("phone", "-666666666");
	    info.put("email", "pepito.com");
	    info.put("password", "Pepito1234");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test07() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía");
	    info.put("surname", "Martínez");
	    info.put("nif", "5569251X");
	    info.put("address", "XXX");
	    info.put("phone", "888 777 777");
	    info.put("email", "pepito45@gmail.com");
	    info.put("password", "Pe12@");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test08() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía4");
	    info.put("surname", "M4rtinez Galvez");
	    info.put("nif", "5569251X");
	    info.put("address", "XXX");
	    info.put("phone", "6667777778");
	    info.put("email", "pepito.com");
	    info.put("password", "Pe12@");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test09() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía4");
	    info.put("surname", "Martinez Galvez");
	    info.put("nif", "55569251");
	    info.put("address", "XXX");
	    info.put("phone", "999 777 777");
	    info.put("email", "pepito@gmail.com");
	    info.put("password", "Pepito1234@");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test10() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía4");
	    info.put("surname", "M4rtinez Galvez");
	    info.put("nif", "5569251X");
	    info.put("address", "XXX");
	    info.put("phone", "777 777 777");
	    info.put("email", "pepito@gmail.com");
	    info.put("password", "pePi123/");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test11() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía4");
	    info.put("surname", "Martínez");
	    info.put("nif", "-5556925X");
	    info.put("address", "XXX");
	    info.put("phone", "666 777 777");
	    info.put("email", "pepito.com");
	    info.put("password", "pepito23@");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test12() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía4");
	    info.put("surname", "M4rtinez");
	    info.put("nif", "55569251X");
	    info.put("address", "XXX");
	    info.put("phone", "666777777");
	    info.put("email", "pepito45@gmail.com");
	    info.put("password", "PEPITO1234@");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test13() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía4");
	    info.put("surname", "Martinez Galvez");
	    info.put("nif", "55569251X");
	    info.put("address", "XXX");
	    info.put("phone", "66677777");
	    info.put("email", "pepito.45@gmail.com");
	    info.put("password", "PepitoGrillo&");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test14() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía4");
	    info.put("surname", "Martinez Galvez");
	    info.put("nif", "55569251");
	    info.put("address", "XXX");
	    info.put("phone", "566 777 777");
	    info.put("email", "pepito@gmail.com");
	    info.put("password", "Pepito1234");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test15() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía");
	    info.put("surname", "Martínez");
	    info.put("nif", "55569251X");
	    info.put("address", "XXX");
	    info.put("phone", "777 777 777");
	    info.put("email", "pepito.com");
	    info.put("password", "PEPITO1234@");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test16() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía");
	    info.put("surname", "M4rtinez");
	    info.put("nif", "55569251");
	    info.put("address", "XXX");
	    info.put("phone", "666 777 777");
	    info.put("email", "pepito45@gmail.com");
	    info.put("password", "PepitoGrillo&");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test17() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía");
	    info.put("surname", "Martinez Galvez");
	    info.put("nif", "5569251X");
	    info.put("address", "XXX");
	    info.put("phone", "666777777");
	    info.put("email", "pepito.45@gmail.com");
	    info.put("password", "Pepito1234");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test18() {
	    JSONObject info = new JSONObject();

	    info.put("name", "Lucía");
	    info.put("surname", "M4rtinez Galvez");
	    info.put("nif", "-5556925X");
	    info.put("address", "XXX");
	    info.put("phone", "66677777");
	    info.put("email", "pepito@gmail.com");
	    info.put("password", "Pe12@");
	    info.put("role", "CLIENT");
	    
		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test19() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "55569251");
		info.put("address", "XXX");
		info.put("phone", "-666666666");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "Pepito1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test20() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "888 777 777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "pePi123/");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test21() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "-5556925X");
		info.put("address", "XXX");
		info.put("phone", "999 777 777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "pepito23@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test22() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "M4rtinez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "566 777 777");
		info.put("email", "pepito45@gmail.com");
		info.put("password", "Pepito1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test23() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "-5556925X");
		info.put("address", "XXX");
		info.put("phone", "6667777778");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "pePi123/");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test24() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "-55569251X");
		info.put("address", "XXX");
		info.put("phone", "-666666666");
		info.put("email", "pepito@gmail.com");
		info.put("password", "pepito23@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test25() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "888 777 777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "PEPITO1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test26() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "M4rtinez");
		info.put("nif", "55569251");
		info.put("address", "XXX");
		info.put("phone", "999 777 777");
		info.put("email", "pepito.com");
		info.put("password", "PepitoGrillo&");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test27() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "777 777 777");
		info.put("email", "pepito45@gmail.com");
		info.put("password", "Pepito1234");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test28() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "-5556925X");
		info.put("address", "XXX");
		info.put("phone", "666 777 777");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "Pe12@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test29() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez");
		info.put("nif", "55569251");
		info.put("address", "XXX");
		info.put("phone", "66677777");
		info.put("email", "pepito.com");
		info.put("password", "pePi123/");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test30() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "999 777 777");
		info.put("email", "pepito45@gmail.com");
		info.put("password", "Pe12@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test31() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "777 777 777");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "Pepito1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test32() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "55569251");
		info.put("address", "XXX");
		info.put("phone", "666 777 777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "pePi123/");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test33() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "666777777");
		info.put("email", "pepito.com");
		info.put("password", "Pepito1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	@Test
	public void test34() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "566 777 777");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "pepito23@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test35() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "55569251");
		info.put("address", "XXX");
		info.put("phone", "6667777778");
		info.put("email", "pepito@gmail.com");
		info.put("password", "PEPITO1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test36() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "-666666666");
		info.put("email", "pepito@gmail.com");
		info.put("password", "PepitoGrillo&");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test37() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez");
		info.put("nif", "-5556925X");
		info.put("address", "XXX");
		info.put("phone", "888 777 777");
		info.put("email", "pepito.com");
		info.put("password", "Pepito1234");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test38() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "55569251");
		info.put("address", "XXX");
		info.put("phone", "66677777");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "PEPITO1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test39() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "566 777 777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "PepitoGrillo&");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test40() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "M4rtinez");
		info.put("nif", "-5556925X");
		info.put("address", "XXX");
		info.put("phone", "6667777778");
		info.put("email", "pepito@gmail.com");
		info.put("password", "Pepito1234");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test41() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "-666666666");
		info.put("email", "pepito.com");
		info.put("password", "Pe12@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test42() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "999 777 777");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "pePi123/");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test43() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez");
		info.put("nif", "-5556925X");
		info.put("address", "XXX");
		info.put("phone", "777 777 777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "Pepito1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test44() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "M4rtinez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "666 777 777");
		info.put("email", "pepito.com");
		info.put("password", "pePi123/");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test45() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "666777777");
		info.put("email", "pepito45@gmail.com");
		info.put("password", "pepito23@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test46() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "566 777 777");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "Pepito1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test47() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "55569251");
		info.put("address", "XXX");
		info.put("phone", "777 777 777");
		info.put("email", "pepito.com");
		info.put("password", "pepito23@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	
	@Test
	public void test48() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "666 777 777");
		info.put("email", "pepito45@gmail.com");
		info.put("password", "PEPITO1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test49() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "-5556925X");
		info.put("address", "XXX");
		info.put("phone", "666777777");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "PepitoGrillo&");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test50() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "66677777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "Pepito1234");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test51() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "566 777 777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "Pe12@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test52() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "-666666666");
		info.put("email", "pepito45@gmail.com");
		info.put("password", "pePi123/");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test53() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "M4rtinez");
		info.put("nif", "55569251");
		info.put("address", "XXX");
		info.put("phone", "666777777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "Pe12@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test54() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "66677777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "Pepito1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test55() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "-5556925X");
		info.put("address", "XXX");
		info.put("phone", "566 777 777");
		info.put("email", "pepito.com");
		info.put("password", "pePi123/");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test56() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "6667777778");
		info.put("email", "pepito45@gmail.com");
		info.put("password", "Pepito1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test57() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "M4rtinez");
		info.put("nif", "55569251");
		info.put("address", "XXX");
		info.put("phone", "888 777 777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "pepito23@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test58() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "999 777 777");
		info.put("email", "pepito.com");
		info.put("password", "PEPITO1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test59() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "-5556925X");
		info.put("address", "XXX");
		info.put("phone", "777 777 777");
		info.put("email", "pepito45@gmail.com");
		info.put("password", "PepitoGrillo&");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test60() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía4");
		info.put("surname", "Martinez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "666 777 777");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "Pepito1234");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	
	@Test
	public void test61() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "-5556925X");
		info.put("address", "XXX");
		info.put("phone", "-666666666");
		info.put("email", "pepito@gmail.com");
		info.put("password", "PEPITO1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test62() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "888 777 777");
		info.put("email", "pepito.com");
		info.put("password", "PepitoGrillo&");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test63() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "999 777 777");
		info.put("email", "pepito45@gmail.com");
		info.put("password", "Pepito1234");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test64() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "55569251");
		info.put("address", "XXX");
		info.put("phone", "777 777 777");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "Pe12@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test65() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "666 777 777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "Pepito1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test66() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez Galvez");
		info.put("nif", "-5556925X");
		info.put("address", "XXX");
		info.put("phone", "666777777");
		info.put("email", "pepito@gmail.com");
		info.put("password", "pePi123/");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test67() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez Galvez");
		info.put("nif", "55569251X");
		info.put("address", "XXX");
		info.put("phone", "66677777");
		info.put("email", "pepito.com");
		info.put("password", "Pepito1234@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test68() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "55569251");
		info.put("address", "XXX");
		info.put("phone", "566 777 777");
		info.put("email", "pepito45@gmail.com");
		info.put("password", "pePi123/");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test69() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "M4rtinez");
		info.put("nif", "5569251X");
		info.put("address", "XXX");
		info.put("phone", "6667777778");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "pepito23@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Los datos introducidos son incorrectos", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test70() {
		JSONObject info = new JSONObject();

		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "55692512X");
		info.put("address", "XXX");
		info.put("phone", "666777778");
		info.put("email", "pepito.45@gmail.com");
		info.put("password", "Pepito23@");
		info.put("role", "CLIENT");

		ResponseEntity<String> result = clientService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Usuario registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
}