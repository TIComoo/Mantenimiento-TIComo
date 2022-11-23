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

import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.http.RiderController;
import uclm.esi.equipo01.http.UserController;
import uclm.esi.equipo01.model.DatabaseSequence;
import uclm.esi.equipo01.model.Rider;
import uclm.esi.equipo01.service.RiderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.AfterClass;
import org.junit.Before;
import org.mockito.Mockito;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class RiderTests {
	
	@Autowired
	private RiderService riderService;
	
	private RiderController riderController;
	private RiderService service;
	private UserController controller;
	
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
    	RiderTests.mongoOperations = mongoOperations;
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
    	Manager.get().getRiderRepository().deleteAll();
    	mongoOperations.findAndRemove(query(where("_id").is(Rider.SEQUENCE_ID)), DatabaseSequence.class);
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
        service = Mockito.mock(RiderService.class);
        riderController = new RiderController();
        controller = new UserController();
    }
	
	/*********************************************************************
	*
	* - Method name: test01 to test02
	* - Description of the Method: Tests carried out to register rider in user controller
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
		info.put("vehicleType", "Bicicleta");
		info.put("licensePlate", "1234XXX");
		info.put("license","true");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepito23@");		
		info.put("role", "RIDER");
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.register(jso)).thenReturn(new ResponseEntity<>("Rider registrado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = controller.register(info);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test02() {
	    Map<String, Object> info = new HashMap<String, Object>();
		
		info.put("name", "Luc1a");
		info.put("surname", "M4rtinez");
		info.put("nif", "556912X");
		info.put("vehicleType", "Bicicleta");
		info.put("licensePlate", "1234XX");
		info.put("license","true");
		info.put("email", "carlosphin.gmail.com");
		info.put("password", "pepito23@");
		
		info.put("role", "RIDER");
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.register(jso)).thenReturn(new ResponseEntity<>("Rider registrado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = controller.register(info);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test03 to test10
	* - Description of the Method: Tests carried out to register rider in rider service
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
		info.put("surname", "Martinez");
		info.put("nif", "55692512X");
		info.put("vehicleType", "Bicicleta");
		info.put("licensePlate", "1234XXX");
		info.put("license","true");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepito23@");
		
		info.put("role", "RIDER");
		
	    ResponseEntity<String> result = riderService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test04() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "55692512X");
		info.put("vehicleType", "Bicicleta");
		info.put("licensePlate", "1234 XXX");
		info.put("license","true");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito23@");
		
		info.put("role", "RIDER");
		
	    ResponseEntity<String> result = riderService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider registrado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test05() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "55692512X");
		info.put("vehicleType", "Bicicleta");
		info.put("licensePlate", "1234 XXX");
		info.put("license","false");
		info.put("email", "carlosphin.45@gmail.com");
		info.put("password", "Pepito23@");
		
		info.put("role", "RIDER");
		
	    ResponseEntity<String> result = riderService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider registrado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test06() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Luc1a");
		info.put("surname", "M4rtinez");
		info.put("nif", "503567X");
		info.put("vehicleType", "Bicicleta");
		info.put("licensePlate", "234 XDF");
		info.put("license","false");
		info.put("email", "carlosphin.gmail.com");
		info.put("password", "Pepito23");
		
	    ResponseEntity<String> result = riderService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider registrado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test07() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "50505050");
		info.put("vehicleType", "Bicicleta");
		info.put("licensePlate", "3455 DF");
		info.put("license","true");
		info.put("email", "carlosphin@gmail");
		info.put("password", "Pep23@");
		
		info.put("role", "RIDER");
		
	    ResponseEntity<String> result = riderService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider registrado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test08() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "-60565436D");
		info.put("vehicleType", "Bicicleta");
		info.put("licensePlate", "1234456");
		info.put("license","false");
		info.put("email", "carlosphin.com");
		info.put("password", "PepitoHola@");
		
		info.put("role", "RIDER");
		
	    ResponseEntity<String> result = riderService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider registrado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test09() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "55692512X");
		info.put("vehicleType", "Bicicleta");
		info.put("licensePlate", "1234XXX");
		info.put("license","true");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "pepito23@");
		
		info.put("role", "RIDER");
		
	    ResponseEntity<String> result = riderService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider registrado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test10() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("nif", "55692512X");
		info.put("vehicleType", "Bicicleta");
		info.put("licensePlate", "1234XXX");
		info.put("license","false");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "PEPITO23@");
		
		info.put("role", "RIDER");
		
	    ResponseEntity<String> result = riderService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider registrado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test11 to test12
	* - Description of the Method: Tests carried out to modify rider in rider controller
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
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "55692512T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XXX");
		info.put("license","true");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.modifyRider(jso, id)).thenReturn(new ResponseEntity<>("Rider modificado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = riderController.modifyRider(info, id);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	@Test
	public void test12() {
	    Map<String, Object> info = new HashMap<String, Object>();
		
		info.put("name", "Lucia23");
		info.put("surname", "M4artinez");
		info.put("nif", "556912546X");
		info.put("vehicleType", "Bicicletas");
		info.put("licensePlate", "1234XX");
		info.put("license","false");
		info.put("email", "carlosphin@gmail");
		info.put("password", "pep1to23@");
		info.put("activeAccount", true);
		
		long id = 2;
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.modifyRider(jso, id)).thenReturn(new ResponseEntity<>("Rider modificado correctamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = riderController.modifyRider(info, id);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test13 to test24
	* - Description of the Method: Tests carried out to modify rider in rider service
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
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "55692512T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XXX");
		info.put("license","true");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado correctamente", HttpStatus.OK);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test14() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia1234");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "55692512T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XXX");
		info.put("license","false");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test15() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez-Gálvez09");
		info.put("nif", "55692512T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XXX");
		info.put("license","true");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test16() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "5562T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XXX");
		info.put("license","false");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test17() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "5569251234T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XXX");
		info.put("license","true");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test18() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "55692512");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XXX");
		info.put("license","false");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test19() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "55692512T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XX");
		info.put("license","true");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test20() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "55692512T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XX");
		info.put("license","true");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test21() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "55692512T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XXX");
		info.put("license","true");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado correctamente", HttpStatus.OK);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test22() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "55692512T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XXX");
		info.put("license","false");
		info.put("email", "carlosphin45.gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test23() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "55692512T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XXX");
		info.put("license","true");
		info.put("email", "carlosphin45@gmail");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test24() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucia Ale");
		info.put("surname", "Martinez Gálvez");
		info.put("nif", "55692512T");
		info.put("vehicleType", "Moto");
		info.put("licensePlate", "1224XXX");
		info.put("license","false");
		info.put("email", "carlosphin45@gmail.com");
		info.put("password", "Pepito233@");
		info.put("activeAccount", true);
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.modifyRider(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider modificado correctamente", HttpStatus.OK);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test25 to test26
	* - Description of the Method: Tests carried out to delete rider in user controller
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test25() {
    
		long id = 3;
		
		Mockito.when(service.deleteRider(id)).thenReturn(new ResponseEntity<>("Rider eliminado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = riderController.deleteRider(id);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test26() {

		long id = 20;
		
		Mockito.when(service.deleteRider(id)).thenReturn(new ResponseEntity<>("Rider eliminado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = riderController.deleteRider(id);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test27 to test31
	* - Description of the Method: Tests carried out to delete rider in rider service
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test27() {
		
		long id = 2;
		
	    ResponseEntity<String> result = riderService.deleteRider(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider eliminado correctamente", HttpStatus.OK);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test28() {
		
		long id = 1;
		
	    ResponseEntity<String> result = riderService.deleteRider(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider eliminado correctamente", HttpStatus.OK);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test29() {
		
		long id = 10;
		
	    ResponseEntity<String> result = riderService.deleteRider(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider eliminado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test30() {
		
		long id = 12;
		
	    ResponseEntity<String> result = riderService.deleteRider(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider eliminado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test31() {
		
		long id = 100;
		
	    ResponseEntity<String> result = riderService.deleteRider(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Rider eliminado incorrectamente", HttpStatus.BAD_REQUEST);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	

}
