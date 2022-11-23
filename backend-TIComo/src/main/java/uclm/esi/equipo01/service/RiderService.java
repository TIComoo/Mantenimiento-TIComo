package uclm.esi.equipo01.service;

import java.util.List;
import java.util.Optional;

import com.github.openjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.model.Rider;
import uclm.esi.equipo01.model.User;

/*********************************************************************
*
* Class Name: RiderService
* Class description: Dedicated to the management of Rider information
*
**********************************************************************/

@Service
public class RiderService extends UserService{
	
	private static final String RIDERNOTFOUND = "Rider no encontrado";
	
	/*********************************************************************
	*
	* - Method name: showAllRiders
	* - Description of the Method: Find all Rider in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None
	* - Return value: List <Rider>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public List<Rider> showAllRiders() {
		return Manager.get().getRiderRepository().findAll();
	}
	
	/*********************************************************************
	*
	* - Method name: register
	* - Description of the Method: check the rider data so that they are correct and save it in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New Rider data
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Override
	public ResponseEntity<String> register(JSONObject jso) {
		String name = jso.getString("name");
		String surname = jso.getString("surname");
		String nif = jso.getString("nif");
		String vehicle = jso.getString("vehicleType");
		String licensePlate;
		boolean license = jso.getBoolean("license");
		String email = jso.getString("email").toLowerCase();
		String password = jso.getString("password");
		
		if (license)
			licensePlate = jso.getString("licensePlate");
		else
			licensePlate = null;	
		
		JSONObject response = new JSONObject();
		
		if (!validatorService.isValidName(name))
			response.put("errorName", "Nombre no válido");
		
		if (!validatorService.isValidSurname(surname))
			response.put("errorSurname", "Apellido no válido");
		
		if(!validatorService.isValidNIF(nif))
			response.put("errorNIF", "nif no válido");
		
		if(license && !validatorService.isValidLicensePlate(licensePlate))
			response.put("errorLicensePlate", "Matrícula no válida");
		
		if (!validatorService.isValidEmail(email) || validatorService.isRepeatedEmail(email))
			response.put("errorEmail", "Email no válido");
		
		if(!validatorService.isValidPwd(password)) 
			response.put("errorPwd", "Contraseña no válida");
		
		if(response.length() != 0)
			return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
		
		User rider = new Rider(email,password,name,surname,nif,vehicle,licensePlate,license);	
		Manager.get().getRiderRepository().save((Rider)rider);	
		
		response.put("status", "Rider registrado correctamente");
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}

	/*********************************************************************
	*
	* - Method name: modifyRider
	* - Description of the Method: check the modify rider data so that they are correct and save it in the repository
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• JSONObject jso: New rider data
	* 		• long ID: ID of the rider to modify
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> modifyRider(JSONObject jso, long id) {
		Optional<Rider> optRider = Manager.get().getRiderRepository().findById(id);	
		if(!optRider.isPresent()) {
			return new ResponseEntity<>(RIDERNOTFOUND, HttpStatus.BAD_REQUEST);
		}
		String name = jso.getString("name");
		String surname = jso.getString("surname");
		String nif = jso.getString("nif");
		String vehicle = jso.getString("vehicleType");
		boolean license = jso.getBoolean("license");
		String licensePlate = jso.getString("licensePlate");
		String email = jso.getString("email").toLowerCase();
		boolean activeAccount = jso.getBoolean("activeAccount");
		
		JSONObject response = new JSONObject();
		
		if (!validatorService.isValidName(name))
			response.put("errorName", "Nombre no válido");
		
		if (!validatorService.isValidSurname(surname))
			response.put("errorSurname", "Apellido no válido");
		
		if(!validatorService.isValidNIF(nif))
			response.put("errorNIF", "nif no válido");
		
		if(license && !validatorService.isValidLicensePlate(licensePlate))
			response.put("errorLicensePlate", "Matrícula no válida");
		
		if (!validatorService.isValidEmail(email) || validatorService.isRepeatedEmail(id, email, "RIDER"))
			response.put("errorEmail", "Email no válido");
		
		if(response.length() != 0)
			return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
		
		User rider = optRider.get();
		rider.setName(name);
		rider.setSurname(surname);
		((Rider) rider).setNif(nif);
		((Rider) rider).setVehicleType(vehicle);
		if (license)
			((Rider) rider).setLicensePlate(licensePlate);
		else
			((Rider) rider).setLicensePlate(null);	
		((Rider) rider).setLicense(license);
		rider.setEmail(email);
		((Rider) rider).setActiveAccount(activeAccount);
				
		Manager.get().getRiderRepository().save((Rider)rider);	
		
		response.put("status", "Rider modificado correctamente");
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}

	/*********************************************************************
	*
	* - Method name: deleteRider
	* - Description of the Method: Look for the rider by id and if it exits it is deleted
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long ID: ID of the rider to delete
	* - Return value: ResponseEntity<String>
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public ResponseEntity<String> deleteRider(long id) {
		if (!Manager.get().getRiderRepository().existsById(id)) {
			return new ResponseEntity<>(RIDERNOTFOUND, HttpStatus.BAD_REQUEST);
		}
		Manager.get().getRiderRepository().deleteById(id);
		return new ResponseEntity<>("Rider eliminado correctamente", HttpStatus.OK);
	}
	
	/*********************************************************************
	*
	* - Method name: showRoder
	* - Description of the Method: Find rider in the repository by id
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: ID of the rider to look for
	* - Return value: Rider
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public Rider showRider(long id) {
		Optional<Rider> rider = Manager.get().getRiderRepository().findById(id);
		if (!rider.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, RIDERNOTFOUND);
		return rider.get();
	}

}
