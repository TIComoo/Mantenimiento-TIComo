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

import uclm.esi.equipo01.model.Client;
import uclm.esi.equipo01.service.ClientService;

/*********************************************************************
*
* Class Name: ClientController
* Class description: Connect between frontend .js classes and backend service classes
*
**********************************************************************/

@CrossOrigin(origins = {"https://ticomo01.web.app", "http://localhost:3000"})
@RestController
@RequestMapping("client")
public class ClientController {
	
	private static ClientService clientService;
	
	@Autowired
	public void setClientService(ClientService clientService) {
		ClientController.clientService = clientService;
	}
	
	/*********************************************************************
	*
	* - Method name: deleteClient
	* - Description of the Method: Call the service to delete a Client by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the client to delete
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/deleteClient/{id}")
	public ResponseEntity<String> deleteClient(@PathVariable long id) {
		return clientService.deleteClient(id);
	}
	
	/*********************************************************************
	*
	* - Method name: modifyClient
	* - Description of the Method: Call the service to modify a Client by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the client to modify
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/modifyClient/{id}")
	public ResponseEntity<String> modifyClient(@RequestBody Map<String, Object> info, @PathVariable long id) {
		JSONObject jso = new JSONObject(info);
		return clientService.modifyClient(jso, id);
	}
	
	/*********************************************************************
	*
	* - Method name: showAllClients
	* - Description of the Method: Call the service to show all Clients
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: Client list
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showAllClients")
	public List<Client> showAllClients(){
		return clientService.showAllClients();			
	}
	
	/*********************************************************************
	*
	* - Method name: showClient
	* - Description of the Method: Call the service to show a Client by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the client to look for
	* - Return value: Client
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showClient/{id}")
	public Client showClient(@PathVariable long id){
		return clientService.showClient(id);	
	}

}
