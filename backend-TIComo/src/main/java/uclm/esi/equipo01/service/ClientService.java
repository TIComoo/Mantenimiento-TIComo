package uclm.esi.equipo01.service;

import java.util.List;
import java.util.Optional;

import com.github.openjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.model.Client;
import uclm.esi.equipo01.model.User;

/*********************************************************************
*
* Class Name: ClientService
* Class description: Dedicated to the management of Client information
*
**********************************************************************/

@Service
public class ClientService extends UserService{
	
	public static final String CLIENTNOTFOUND = "Cliente no encontrado";
	
	/*********************************************************************
	*
	* - Method name: register
	* - Description of the Method: check the client data so that they are correct and save it in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New Client data
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
		String nif = jso.getString("nif");
		String direction = jso.getString("address");
		String phone = jso.getString("phone");
		
		JSONObject response = new JSONObject();
		
		if(!validatorService.isValidPwd(pwd)) 
			response.put("errorPwd", "Contraseña no válida");
		
		if (!validatorService.isValidEmail(email) || validatorService.isRepeatedEmail(email))
			response.put("errorEmail", "Email no válido");
		
		if (!validatorService.isValidName(name))
			response.put("errorName", "Nombre no válido");
		
		if (!validatorService.isValidSurname(surname))
			response.put("errorSurname", "Apellido no válido");
		
		if(!validatorService.isValidNIF(nif))
			response.put("errorNIF", "NIF no válido");
				
		if(!validatorService.isValidPhone(phone))
			response.put("errorPhone", "Teléfono no válido");
		
		if(response.length() != 0)
			return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
		
		User client = new Client(email, pwd, name, surname, nif, direction, phone);	
		Manager.get().getClientRepository().save((Client)client);	
		
		response.put("status", "Usuario registrado correctamente");
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		
	}
	
	/*********************************************************************
	*
	* - Method name: showClient
	* - Description of the Method: Find Client in the repository by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the Client to look for
	* - Return value: Client
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public Client showClient(long id) {
		Optional<Client> client = Manager.get().getClientRepository().findById(id);
		if (!client.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLIENTNOTFOUND);
		return client.get();
	}
	
	/*********************************************************************
	*
	* - Method name: showAllClients
	* - Description of the Method: Find all Clients in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: List <Client>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public List<Client> showAllClients(){
		return Manager.get().getClientRepository().findAll();
		
	}
	
	/*********************************************************************
	*
	* - Method name: deleteClient
	* - Description of the Method: Look for the Client by id and if it exits it is deleted
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long ID: ID of the Client to delete
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> deleteClient(long id) {
		if (!Manager.get().getClientRepository().existsById(id)) {
			return new ResponseEntity<>(CLIENTNOTFOUND, HttpStatus.BAD_REQUEST);
		}
		Manager.get().getClientRepository().deleteById(id);
		return new ResponseEntity<>("Cliente eliminado correctamente", HttpStatus.OK);
	}

	/*********************************************************************
	*
	* - Method name: modifyClient
	* - Description of the Method: check the modify client data so that they are correct and save it in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New Client data
	* 		• long ID: ID of the Client to modify
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> modifyClient(JSONObject jso, long id) {
		Optional<Client> optClient = Manager.get().getClientRepository().findById(id);	
		if(!optClient.isPresent()) {
			return new ResponseEntity<>(CLIENTNOTFOUND, HttpStatus.BAD_REQUEST);
		}	
		String email = jso.getString("email").toLowerCase();
		String name = jso.getString("name");
		String surname = jso.getString("surname");
	    String nif = jso.getString("nif");
	    String address = jso.getString("address");
	    String phone = jso.getString("phone");	
		boolean activeAccount = jso.getBoolean("activeAccount");
		
		JSONObject response = new JSONObject();
		
		if (!validatorService.isValidEmail(email) || validatorService.isRepeatedEmail(id, email, "CLIENT"))
			response.put("errorEmail", "Email no válido");
		
		if (!validatorService.isValidName(name))
			response.put("errorName", "Nombre no válido");
		
		if (!validatorService.isValidSurname(surname))
			response.put("errorSurname", "Apellido no válido");
		
		if(!validatorService.isValidNIF(nif))
			response.put("errorNIF", "nif no válido");
		
		if(!validatorService.isValidPhone(phone))
			response.put("errorPhone", "Teléfono no válido");
		
		if(response.length() != 0)
			return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
		
		User client = optClient.get();
		client.setEmail(email);
		client.setName(name);
		client.setSurname(surname);
		((Client) client).setNIF(nif);
		((Client) client).setAddress(address);
		((Client) client).setPhone(phone);
		((Client) client).setActiveAccount(activeAccount);
		
		Manager.get().getClientRepository().save((Client)client);	
		
		response.put("status", "Cliente modificado correctamente");
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}

	
}
