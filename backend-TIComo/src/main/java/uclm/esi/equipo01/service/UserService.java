package uclm.esi.equipo01.service;

import com.github.openjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.model.User;

/*********************************************************************
*
* Class Name: UserService
* Class description: Dedicated to the management of user information in common
*
**********************************************************************/

@Service
public abstract class UserService {
	
	private static final String ERRORACCOUNT = "errorAccount";
	private static final String ACCOUNTNOTACTIVATED = "Account not activated";
	
	protected static ValidatorService validatorService;

	public abstract ResponseEntity<String> register(JSONObject jso);
	
	/*********************************************************************
	*
	* - Method name: login
	* - Description of the Method: check if the data is correct and what type of user it is
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New user data
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> login(JSONObject jso){
		String email = jso.getString("email").toLowerCase();
		String pwd = org.apache.commons.codec.digest.DigestUtils.sha512Hex(jso.getString("password"));
		
		
		User user;
			
		user = Manager.get().getAdminRepository().findByEmailAndPwd(email, pwd);
		
		if (user != null) {
			if (!user.isActiveAccount()) {
				JSONObject response = new JSONObject();
				response.put(ERRORACCOUNT, ACCOUNTNOTACTIVATED);
				return new ResponseEntity<>(response.toString(), HttpStatus.UNAUTHORIZED);
			}
			
			
			JSONObject response = new JSONObject(user);
			response.put("role", "ADMIN");
			return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		}
			
		user = Manager.get().getRiderRepository().findByEmailAndPwd(email, pwd);
		
		if (user != null) {
			if (!user.isActiveAccount()) {
				JSONObject response = new JSONObject();
				response.put(ERRORACCOUNT, ACCOUNTNOTACTIVATED);
				return new ResponseEntity<>(response.toString(), HttpStatus.UNAUTHORIZED);
			}
			JSONObject response = new JSONObject(user);
			response.put("role", "RIDER");
			return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		}
		
		user = Manager.get().getClientRepository().findByEmailAndPwd(email, pwd);
		
		if (user != null) {
			if (!user.isActiveAccount()) {
				JSONObject response = new JSONObject();
				response.put(ERRORACCOUNT, ACCOUNTNOTACTIVATED);
				return new ResponseEntity<>(response.toString(), HttpStatus.UNAUTHORIZED);
			}
			JSONObject response = new JSONObject(user);
			response.put("role", "CLIENT");
			return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		}
		
		JSONObject response = new JSONObject();
		response.put("error", "Email o contraseña no válida");
		return new ResponseEntity<>(response.toString(), HttpStatus.UNAUTHORIZED);
	}
	
	@Autowired
	public void setValidatorService(ValidatorService validatorService) {
		UserService.validatorService = validatorService;
	}
	
}
