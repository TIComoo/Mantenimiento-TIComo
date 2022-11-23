package uclm.esi.equipo01.service;

import java.util.List;
import java.util.Optional;

import com.github.openjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.model.Admin;
import uclm.esi.equipo01.model.User;

/*********************************************************************
*
* Class Name: AdminService
* Class description: Dedicated to the management of admin information
*
**********************************************************************/

@Service
public class AdminService extends UserService{

	/*********************************************************************
	*
	* - Method name: showAllAdmins
	* - Description of the Method: Find all admin in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: List <Admin>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public List<Admin> showAllAdmins() {
		return Manager.get().getAdminRepository().findAll();
	}

	/*********************************************************************
	*
	* - Method name: register
	* - Description of the Method: check the admin data so that they are correct and save it in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New Admin data
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Override
	public ResponseEntity<String> register(JSONObject jso) {
		String email = jso.getString("email").toLowerCase();
		String pwd = jso.getString("password");
		String name = jso.getString("name");
		String surname = jso.getString("surname");
		String zone = jso.getString("zone");
		
		JSONObject response = new JSONObject();
		
		if(!validatorService.isValidPwd(pwd)) 
			response.put("errorPwd", "Contraseña no válida");
		
		if (!validatorService.isValidEmail(email) || validatorService.isRepeatedEmail(email))
			response.put("errorEmail", "Email no válido");
		
		if (!validatorService.isValidName(name))
			response.put("errorName", "Nombre no válido");
		
		if (!validatorService.isValidSurname(surname))
			response.put("errorSurname", "Apellido no válido");

		if(response.length() != 0)
			return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
		
		User admin = new Admin(email, pwd, name, surname, zone);	
		Manager.get().getAdminRepository().save((Admin)admin);	
		
		response.put("status", "Admin registrado correctamente");
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}

	/*********************************************************************
	*
	* - Method name: showAdmin
	* - Description of the Method: Find Admin in the repository by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the admin to look for
	* - Return value: Admin
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public Admin showAdmin(long id) {
		Optional<Admin> admin = Manager.get().getAdminRepository().findById(id);
		if (!admin.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Administrador no encontrado");
		return admin.get();
	}

	/*********************************************************************
	*
	* - Method name: modifyAdmin
	* - Description of the Method: check the modify admin data so that they are correct and save it in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New admin data
	* 		• long ID: ID of the admin to modify
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> modifyAdmin(JSONObject jso, long id) {
		Optional<Admin> optAdmin = Manager.get().getAdminRepository().findById(id);	
		if(!optAdmin.isPresent()) {
			return new ResponseEntity<>("Admin no encontrado", HttpStatus.BAD_REQUEST);
		}
	
		String email = jso.getString("email").toLowerCase();
		String name = jso.getString("name");
		String surname = jso.getString("surname");
		String zone = jso.getString("zone");
		
		JSONObject response = new JSONObject();
		
		if (!validatorService.isValidEmail(email) || validatorService.isRepeatedEmail(id, email, "ADMIN"))
			response.put("errorEmail", "Email no válido");
		
		if (!validatorService.isValidName(name))
			response.put("errorName", "Nombre no válido");
		
		if (!validatorService.isValidSurname(surname))
			response.put("errorSurname", "Apellido no válido");
		
		if(response.length() != 0)
			return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
		
		User admin = optAdmin.get();
		admin.setEmail(email);
		admin.setName(name);
		admin.setSurname(surname);
		((Admin) admin).setZone(zone);
		
		Manager.get().getAdminRepository().save((Admin)admin);	
		
		response.put("status", "Admin registrado correctamente");
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}
	
	/*********************************************************************
	*
	* - Method name: deleteAdmin
	* - Description of the Method: Look for the admin by id and if it exits it is deleted
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long ID: ID of the admin to delete
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> deleteAdmin(long id) {
		if (!Manager.get().getAdminRepository().existsById(id)) {
			return new ResponseEntity<>("Admin no encontrado", HttpStatus.BAD_REQUEST);
		}
		Manager.get().getAdminRepository().deleteById(id);
		return new ResponseEntity<>("Admin eliminado correctamente", HttpStatus.OK);
	}


}
