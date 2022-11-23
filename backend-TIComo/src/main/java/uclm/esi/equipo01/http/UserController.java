package uclm.esi.equipo01.http;

import java.util.Map;

import com.github.openjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uclm.esi.equipo01.service.AdminService;
import uclm.esi.equipo01.service.ClientService;
import uclm.esi.equipo01.service.RiderService;

/*********************************************************************
*
* Class Name: UserController
* Class description: Connect between frontend .js classes and backend service classes
*
**********************************************************************/

@CrossOrigin(origins = {"https://ticomo01.web.app", "http://localhost:3000"})
@RestController
@RequestMapping("user")
public class UserController {
	
	private static AdminService adminService;
	
	/*********************************************************************
	*
	* - Method name: setAdminService
	* - Description of the Method: Initialize the adminService
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: 
	* 		• AdminService adminService: Class global variable
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Autowired
	public void setAdminService(AdminService adminService) {
		UserController.adminService = adminService;
	}
	
	private static RiderService riderService;
	
	/*********************************************************************
	*
	* - Method name: setRiderService
	* - Description of the Method: Initialize the riderService
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: 
	* 		• RiderService riderService: Class global variable
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Autowired
	public void setRiderService(RiderService riderService) {
		UserController.riderService = riderService;
	}
	
	private static ClientService clientService;
	
	/*********************************************************************
	*
	* - Method name: setClientService
	* - Description of the Method: Initialize the clientService
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: 
	* 		• ClientService clientService: Class global variable
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Autowired
	public void setClientService(ClientService clientService) {
		UserController.clientService = clientService;
	}
	
	/*********************************************************************
	*
	* - Method name: register
	* - Description of the Method: check the type of user and call its corresponding service
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: New user data and his rol
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Map<String, Object> info){

		JSONObject jso = new JSONObject(info);
		String rol = jso.getString("role");
		
		switch(rol) {
			case "ADMIN":
				return adminService.register(jso);
			case "RIDER":
				return riderService.register(jso);
			default:
				return clientService.register(jso);
		}		
	}
	
	/*********************************************************************
	*
	* - Method name: login
	* - Description of the Method:
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: User login data
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, Object> info){
		JSONObject jso = new JSONObject(info);
		return clientService.login(jso);			
	}

}
