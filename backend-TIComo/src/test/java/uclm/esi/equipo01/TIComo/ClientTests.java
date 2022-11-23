package uclm.esi.equipo01.TIComo;
import static org.junit.Assert.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.HashMap;
import java.util.Map;

import com.github.openjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import uclm.esi.equipo01.http.ClientController;
import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.http.UserController;
import uclm.esi.equipo01.model.Client;
import uclm.esi.equipo01.model.DatabaseSequence;
import uclm.esi.equipo01.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.AfterClass;
import org.junit.Before;
import org.mockito.Mockito;

/*********************************************************************
*
* Class Name: Client Test
* Class description: Dedicated to the client test
*
**********************************************************************/

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class ClientTests {
	
	@Autowired
	private ClientService clientService;
	
	private UserController controller;
	private ClientService service;
	private ClientController clientController;
	
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
    	ClientTests.mongoOperations = mongoOperations;
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
        service = Mockito.mock(ClientService.class);
        controller = new UserController();
        clientController = new ClientController(); 
    }
	
	/*********************************************************************
	*
	* - Method name: test01 to test02
	* - Description of the Method: Tests carried out to modify client in client controller
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
	    
	    info.put("email", "pepito345@gmail.com");
	    info.put("password", "PepitoCARACOLA23@");
	    info.put("name", "Paquita");
	    info.put("surname", "Martinez Gonzalez");
	    info.put("nif", "55392512X");
	    info.put("address", "XXX");
	    info.put("phone", "666577348");
	    info.put("activeAccount", "True");

		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.modifyClient(jso, id)).thenReturn(new ResponseEntity<>("Cliente modificado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = clientController.modifyClient(info, id);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test02() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("email", "carlosphinclient.gmail.com");
		info.put("password", "client*CLIENT");
		info.put("name","C4rlos");
		info.put("surname","Pul1do");
		info.put("nif","123458A");
		info.put("address","Paseo de la Universidad, 4, 13071 Ciudad Real");
		info.put("phone","6666666677");
	    info.put("activeAccount", "True");
	    
		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.modifyClient(jso, id)).thenReturn(new ResponseEntity<>("Cliente modificado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = clientController.modifyClient(info, id);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test03 to test10
	* - Description of the Method: Tests carried out to modify client in client service
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
	    Map<String, Object> info = new HashMap<String, Object>();
	    
	    info.put("email", "pepito345@gmail.com");
	    info.put("password", "PepitoCARACOLA23@");
	    info.put("name", "Paquita");
	    info.put("surname", "Martinez Gonzalez");
	    info.put("nif", "55392512X");
	    info.put("address", "XXX");
	    info.put("phone", "666577348");
	    info.put("activeAccount", "True");

		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		ResponseEntity<String> result = clientService.modifyClient(jso, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test04() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "client123*Client");
		info.put("name","Carlos");
		info.put("surname","Pulido");
		info.put("nif","12345678A");
		info.put("address","Paseo de la Universidad, 4, 13071 Ciudad Real");
		info.put("phone","766666666");
	    info.put("activeAccount", "True");

		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		ResponseEntity<String> result = clientService.modifyClient(jso, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test05() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "client123*CLIENT");
		info.put("name","Carlos");
		info.put("surname","Pulido Sánchez");
		info.put("nif","12375678A");
		info.put("address","Paseo de la Universidad, 4, 13071 Ciudad Real");
		info.put("phone","668666366");
	    info.put("activeAccount", "True");

		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		ResponseEntity<String> result = clientService.modifyClient(jso, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test06() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "client123*CLIENT");
		info.put("name","Carlos");
		info.put("surname","Pulido");
		info.put("nif","12345678A");
		info.put("address","Paseo de la Universidad, 4, 13071 Ciudad Real");
		info.put("phone","666666666");
	    info.put("activeAccount", "False");

		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		ResponseEntity<String> result = clientService.modifyClient(jso, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test07() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("email", "carlosphinclient.gmail.com");
		info.put("password", "client123*");
		info.put("name","C4rlos");
		info.put("surname","Pul1do");
		info.put("nif","12345678AX");
		info.put("address","Paseo de la Universidad, 4, 13071 Ciudad Real");
		info.put("phone","6666666667");
	    info.put("activeAccount", "True");

		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		ResponseEntity<String> result = clientService.modifyClient(jso, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido correctamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test08() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "123*CLIENT");
		info.put("name","Carlos");
		info.put("surname","Pulido");
		info.put("nif","1234578A");
		info.put("address","Paseo de la Universidad, 4, 13071 Ciudad Real");
		info.put("phone","666666666");
	    info.put("activeAccount", "True");

		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		ResponseEntity<String> result = clientService.modifyClient(jso, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido correctamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test09() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "client*CLIENT");
		info.put("name","Carlos");
		info.put("surname","Pulido");
		info.put("nif","123456784");
		info.put("address","Paseo de la Universidad, 4, 13071 Ciudad Real");
		info.put("phone","6666666");
	    info.put("activeAccount", "True");

		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		ResponseEntity<String> result = clientService.modifyClient(jso, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido correctamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test10() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "client123CLIENT");
		info.put("name","Carlos");
		info.put("surname","Pulido");
		info.put("nif","1234567RA");
		info.put("address","Paseo de la Universidad, 4, 13071 Ciudad Real");
		info.put("phone","166666666");
	    info.put("activeAccount", "True");

		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		ResponseEntity<String> result = clientService.modifyClient(jso, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El restaurante introducido correctamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test11 to test12
	* - Description of the Method: Tests carried out to delete client in client controller
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test11() {

		long id = 1;
		
		Mockito.when(service.deleteClient(id)).thenReturn(new ResponseEntity<>("Cliente eliminado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = clientController.deleteClient(id);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test12() {

		long id = 100;
		
		Mockito.when(service.deleteClient(id)).thenReturn(new ResponseEntity<>("Cliente eliminado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = clientController.deleteClient(id);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test12 to testXX
	* - Description of the Method: Tests carried out to delete client in client service
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test13() {

		long id = 2;
		
		ResponseEntity<String> result = clientService.deleteClient(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El cliente eliminado correctamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test14() {

		long id = 100;
		
		ResponseEntity<String> result = clientService.deleteClient(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El cliente eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test15() {

		long id = 38;
		
		ResponseEntity<String> result = clientService.deleteClient(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El cliente eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test16() {

		long id = 49;
		
		ResponseEntity<String> result = clientService.deleteClient(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("El cliente eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
}
