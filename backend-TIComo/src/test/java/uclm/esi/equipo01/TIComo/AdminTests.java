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
import uclm.esi.equipo01.http.AdminController;
import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.http.UserController;
import uclm.esi.equipo01.model.Admin;
import uclm.esi.equipo01.model.DatabaseSequence;
import uclm.esi.equipo01.service.AdminService;

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
* Class Name: Admin Test
* Class description: Dedicated to the admin test
*
**********************************************************************/

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class AdminTests {
	
	@Autowired
	private AdminService adminService;
	
	private UserController controller;
	private AdminService service;
	private AdminController adminController;
	
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
    	AdminTests.mongoOperations = mongoOperations;
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
    	Manager.get().getAdminRepository().deleteAll();
    	mongoOperations.findAndRemove(query(where("_id").is(Admin.SEQUENCE_ID)), DatabaseSequence.class);
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
        service = Mockito.mock(AdminService.class);
        controller = new UserController();
        adminController = new AdminController(); 
    }
	
	
	/*********************************************************************
	*
	* - Method name: test01 to test02
	* - Description of the Method: Tests carried out to register admin in admin controller
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
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepito23@");
		
		info.put("role", "ADMIN");
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.register(jso)).thenReturn(new ResponseEntity<>("Administrador registrado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = controller.register(info);
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test02() {
	    Map<String, Object> info = new HashMap<String, Object>();
		
		info.put("name", "Lucí4");
		info.put("surname", "M4rtinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin.gmail.com");
		info.put("password", "pepito23@");
		
		info.put("role", "ADMIN");
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.register(jso)).thenReturn(new ResponseEntity<>("Administrador registrado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = controller.register(info);
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	
	/*********************************************************************
	*
	* - Method name: test03 to test14
	* - Description of the Method: Tests carried out to register admin in admin service
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
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepito23@");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test04() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía Petra");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin345@gmail.com");
		info.put("password", "Pepito23@hola");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test05() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez Gonzalez");
		info.put("zone", "XXX");
		info.put("email", "carlos345@gmail.com");
		info.put("password", "Pep!to23@G0");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test06() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucí4");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepito23@");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test07() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martin3z");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepito23@");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test08() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin.gmail.com");
		info.put("password", "Pepito23@");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test09() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail");
		info.put("password", "Pepito23@");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test10() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "pepito23@");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test11() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "PepitoRT@");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test12() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepito2345");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test13() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pe23@");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test14() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "PEPITO23@");
		
		info.put("role", "ADMIN");
		
	    ResponseEntity<String> result = adminService.register(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador registrado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

	/*********************************************************************
	*
	* - Method name: test15 to test16
	* - Description of the Method: Tests carried out to modify admin in admin controller
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test15() {
	    Map<String, Object> info = new HashMap<String, Object>();
	    
		info.put("name", "Calamar");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepito23@");
		
		long id = 1;
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.modifyAdmin(jso, id)).thenReturn(new ResponseEntity<>("Administrador modificado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = adminController.modifyAdmin(info, id);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
		
	@Test
	public void test16() {
	    Map<String, Object> info = new HashMap<String, Object>();
		
		info.put("name", "Lucí4");
		info.put("surname", "M4rtinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin.gmail.com");
		info.put("password", "pepito23@");
		
		long id = 2;
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.modifyAdmin(jso, id)).thenReturn(new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = adminController.modifyAdmin(info, id);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}	
	
	/*********************************************************************
	*
	* - Method name: test17 to test28
	* - Description of the Method: Tests carried out to modify admin in admin service
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test17() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Lucía Petra");
		info.put("surname", "Martinez Gonzalez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin46@gmail.com");
		info.put("password", "Pepito23@45mago");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test18() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Calamar123");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepito23@");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test19() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Calamar");
		info.put("surname", "M45rtinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepito23@");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test20() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Calamar");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphingmail.com");
		info.put("password", "Pepito23@");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test21() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Calamar");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmailcom");
		info.put("password", "Pepito23@");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test22() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Calamar");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail");
		info.put("password", "Pepito23@");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test23() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Calamar");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin.com");
		info.put("password", "Pepito23@");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test24() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Calamar");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "pepito23@");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test25() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Calamar");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepitotry@");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test26() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Calamar");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "PEPITO23@");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test27() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Calamar");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pepito23MAGO");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test28() {
	    JSONObject info = new JSONObject();
	    
		info.put("name", "Calamar");
		info.put("surname", "Martinez");
		info.put("zone", "XXX");
		info.put("email", "carlosphin@gmail.com");
		info.put("password", "Pe23@");
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.modifyAdmin(info, id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador modificado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test29 to test30
	* - Description of the Method: Tests carried out to delete admin in admin controller
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/

	@Test
	public void test29() {

		long id = 1;
		
		Mockito.when(service.deleteAdmin(id)).thenReturn(new ResponseEntity<>("Administrador eliminado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = adminController.deleteAdmin(id);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test30() {

		long id = 30;
		
		Mockito.when(service.deleteAdmin(id)).thenReturn(new ResponseEntity<>("Administrador eliminado incorrectamente", HttpStatus.BAD_REQUEST));
		ResponseEntity<String> httpResponse = adminController.deleteAdmin(id);
		
		assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
	}
	
	/*********************************************************************
	*
	* - Method name: test31 to test36
	* - Description of the Method: Tests carried out to delete admin in admin service
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Test
	public void test31() {
		
		long id = 3;
		
	    ResponseEntity<String> result = adminService.deleteAdmin(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador eliminado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test32() {
		
		long id = 2;
		
	    ResponseEntity<String> result = adminService.deleteAdmin(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador eliminado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test33() {
		
		long id = 1;
		
	    ResponseEntity<String> result = adminService.deleteAdmin(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test34() {
		
		long id = 5;
		
	    ResponseEntity<String> result = adminService.deleteAdmin(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test35() {
		
		long id = 30;
		
	    ResponseEntity<String> result = adminService.deleteAdmin(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test36() {
		
		long id = 100;
		
	    ResponseEntity<String> result = adminService.deleteAdmin(id);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Administrador eliminado incorrectamente", HttpStatus.BAD_REQUEST);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}		
	
}
