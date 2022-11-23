package uclm.esi.equipo01.http;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.openjson.JSONObject;

import uclm.esi.equipo01.model.Admin;
import uclm.esi.equipo01.service.AdminService;

/*********************************************************************
*
* Class Name: AdminController
* Class description: Connect between frontend .js classes and backend service classes
*
**********************************************************************/

@CrossOrigin(origins = {"https://ticomo01.web.app", "http://localhost:3000"})
@RestController
@RequestMapping("admin")
public class AdminController {
	
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
		AdminController.adminService = adminService;
	}
	
	/*********************************************************************
	*
	* - Method name: showAllAdmins
	* - Description of the Method: Call the service to show all admins
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: List <Admin>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showAllAdmins")
	public List<Admin> showAllAdmins(){
		return adminService.showAllAdmins();			
	}
	
	/*********************************************************************
	*
	* - Method name: showAdmin
	* - Description of the Method: Call the service to show a admin by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the admin to look for
	* - Return value: Admin
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showAdmin/{id}")
	public Admin showAdmin(@PathVariable long id){
		return adminService.showAdmin(id);	
	}
	
	/*********************************************************************
	*
	* - Method name: modifyAdmin
	* - Description of the Method: Call the service to modify a admin by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: Admin modified data
	* 		• long id: ID of the admin to modify
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/modifyAdmin/{id}")
	public ResponseEntity<String> modifyAdmin(@RequestBody Map<String, Object> info, @PathVariable long id) {
		JSONObject jso = new JSONObject(info);
		return adminService.modifyAdmin(jso, id);
	}

	/*********************************************************************
	*
	* - Method name: deleteAdmin
	* - Description of the Method: Call the service to delete a admin by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the admin to delete
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/deleteAdmin/{id}")
	public ResponseEntity<String> deleteAdmin(@PathVariable long id) {
		return adminService.deleteAdmin(id);
	}

}
