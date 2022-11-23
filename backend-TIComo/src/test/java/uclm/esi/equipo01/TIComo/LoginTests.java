package uclm.esi.equipo01.TIComo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import com.github.openjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import uclm.esi.equipo01.http.UserController;
import uclm.esi.equipo01.service.ClientService;
import uclm.esi.equipo01.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.Before;
import org.mockito.Mockito;

/*********************************************************************
*
* Class Name: Login Test
* Class description: Dedicated to the login test
*
**********************************************************************/

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class LoginTests {
	
	@Autowired
	private ClientService clientService;
	
	private UserController controller;
	private UserService service;
	
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
	* - Description of the Method: Tests carried out to login in user controller
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
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "client123*CLIENT");
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.login(jso)).thenReturn(new ResponseEntity<>("Usuario logeado correctamente", HttpStatus.OK));
		ResponseEntity<String> httpResponse = controller.login(info);
		
		assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}
	
	@Test
	public void test02() {
	    Map<String, Object> info = new HashMap<String, Object>();
		info.put("email", "");
		info.put("password", "client123*CLIENT");
		
		JSONObject jso = new JSONObject(info);
		
		Mockito.when(service.login(jso)).thenReturn(new ResponseEntity<>("Email o contraseña no válida", HttpStatus.UNAUTHORIZED));
		ResponseEntity<String> httpResponse = controller.login(info);
		
		assertEquals(HttpStatus.UNAUTHORIZED, httpResponse.getStatusCode());
	}
	
	
	/*********************************************************************
	*
	* - Method name: test03 to test12
	* - Description of the Method: Tests carried out to login in client service
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
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "client123*CLIENT");
		
	    ResponseEntity<String> result = clientService.login(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Usuario logeado correctamente", HttpStatus.OK);
		assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
		
	@Test
	public void test04() {
	    JSONObject info = new JSONObject();
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "client123*CLIENT");
		
	    ResponseEntity<String> result = clientService.login(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Usuario logeado correctamente", HttpStatus.OK);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test05() {
	    JSONObject info = new JSONObject();
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "CLIENT123*client");
		
	    ResponseEntity<String> result = clientService.login(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Email o contraseña no válida", HttpStatus.UNAUTHORIZED);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test06() {
	    JSONObject info = new JSONObject();
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "admin");
		
	    ResponseEntity<String> result = clientService.login(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Email o contraseña no válida", HttpStatus.UNAUTHORIZED);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test07() {
	    JSONObject info = new JSONObject();
	    
		info.put("email", "carlosphin123@gmail.com");
		info.put("password", "client123*CLIENT");
		
	    ResponseEntity<String> result = clientService.login(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Email o contraseña no válida", HttpStatus.UNAUTHORIZED);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test8() {
	    JSONObject info = new JSONObject();
	    
		info.put("email", "");
		info.put("password", "");
		
	    ResponseEntity<String> result = clientService.login(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Email o contraseña no válida", HttpStatus.UNAUTHORIZED);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test9() {
	    JSONObject info = new JSONObject();
	    
		info.put("email", "client123*CLIENT");
		info.put("password", "carlosphinclient@gmail.com");
		
	    ResponseEntity<String> result = clientService.login(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Email o contraseña no válida", HttpStatus.UNAUTHORIZED);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test10() {
	    JSONObject info = new JSONObject();
	    
		info.put("email", "carlosphinclient@gmail.com");
		info.put("password", "");
		
	    ResponseEntity<String> result = clientService.login(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Email o contraseña no válida", HttpStatus.UNAUTHORIZED);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test11() {
	    JSONObject info = new JSONObject();
	    
		info.put("email", "");
		info.put("password", "client123*CLIENT");
		
	    ResponseEntity<String> result = clientService.login(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Email o contraseña no válida", HttpStatus.UNAUTHORIZED);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}
	
	@Test
	public void test12() {
	    JSONObject info = new JSONObject();

		info.put("email", "CARLOSPHINCLIENT@GMAIL.COM");
		info.put("password", "client123*CLIENT");
		
	    ResponseEntity<String> result = clientService.login(info);
	    ResponseEntity<String> valueExpected = new ResponseEntity<>("Usuario logeado correctamente", HttpStatus.OK);
	    assertEquals(valueExpected.getStatusCode(), result.getStatusCode());
	}

}
