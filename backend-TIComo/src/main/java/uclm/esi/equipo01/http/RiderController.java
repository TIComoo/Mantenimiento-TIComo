package uclm.esi.equipo01.http;

import java.util.List;
import java.util.Map;

import com.github.openjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uclm.esi.equipo01.model.Rider;
import uclm.esi.equipo01.service.RiderService;

/*********************************************************************
*
* Class Name: RiderController
* Class description: Connect between frontend .js classes and backend service classes
*
**********************************************************************/

@CrossOrigin(origins = {"https://ticomo01.web.app", "http://localhost:3000"})
@RestController
@RequestMapping("rider")
public class RiderController {

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
		RiderController.riderService = riderService;
	}
	
	/*********************************************************************
	*
	* - Method name: showAllRiders
	* - Description of the Method: Call the service to show all Riders
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: Rider list
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showAllRiders")
	public List<Rider> showAllRiders(){
		return riderService.showAllRiders();			
	}

	/*********************************************************************
	*
	* - Method name: modifyRider
	* - Description of the Method: Call the service to modify a Rider by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• Map<String, Object> info: Rider modified data
	* 		• long id: ID of the Rider to modify
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/modifyRider/{id}")
	public ResponseEntity<String> modifyRider(@RequestBody Map<String, Object> info, @PathVariable long id) {
		JSONObject jso = new JSONObject(info);
		return riderService.modifyRider(jso, id);
	}
	
	/*********************************************************************
	*
	* - Method name: deleteRider
	* - Description of the Method: Call the service to delete a Rider by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the rider to delete
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@PostMapping("/deleteRider/{id}")
	public ResponseEntity<String> deleteRider(@PathVariable long id){		
		return riderService.deleteRider(id);		
	}
	
	/*********************************************************************
	*
	* - Method name: showRider
	* - Description of the Method: Call the service to show a Rider by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the rider to look for
	* - Return value: Rider
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@GetMapping("/showRider/{id}")
	public Rider showRider(@PathVariable long id){
		return riderService.showRider(id);	
	}

}
