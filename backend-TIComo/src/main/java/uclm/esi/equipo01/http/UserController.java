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
	
	@Autowired
	private  AdminService adminService;
	
	@Autowired
	private  RiderService riderService;
	
	@Autowired
	private  ClientService clientService;
	

	
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
