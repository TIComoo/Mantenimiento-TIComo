package uclm.esi.equipo01.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.LowercaseCharacterRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;
import org.springframework.stereotype.Service;

import uclm.esi.equipo01.http.Manager;
import uclm.esi.equipo01.model.Admin;
import uclm.esi.equipo01.model.Client;
import uclm.esi.equipo01.model.Plate;
import uclm.esi.equipo01.model.Restaurant;
import uclm.esi.equipo01.model.Rider;

/*********************************************************************
*
* Class Name: ValidatorService.
* Class description: Validates different requirements.
*
**********************************************************************/
@Service
public class ValidatorService {
	
	String checkNumbers = "[0-9]*";
	String checkAlphabetic = "[A-Z]*";
	
	/*********************************************************************
	*
	* - Method name: isValidEmail
	* - Description of the Method: check if the email is correct
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String email: entered email
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public boolean isValidEmail(String email) {
		boolean isValidEmail = true;
		String rep = "A-Za-z0-9";
		String s = "\\";
		char c = '(';
		String p1 = "^[_" + rep + "-" + s + "+]+" + c + s + ".[_" + rep + "-]+)*@";
		String p2 = "[" + rep + "-]+" + c + s + ".[" + rep + "]+)*" + c + s + ".[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(p1 + p2);
		Matcher matcher = pattern.matcher(email);
		
		if (!matcher.find())
			isValidEmail = false;
		return isValidEmail;
	}
	
	/*********************************************************************
	*
	* - Method name: isRepeatedEmail
	* - Description of the Method: check if the email is repeated
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String email: entered email
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public boolean isRepeatedEmail(String email) {
		boolean isRepeatedEmail;
		
		List<Admin> admins = Manager.get().getAdminRepository().findAll();
		List<Rider> riders = Manager.get().getRiderRepository().findAll();
		List<Client> clients = Manager.get().getClientRepository().findAll();
		
		List<String> emails = new ArrayList<>();
		
		for (Admin admin : admins) {
			emails.add(admin.getEmail());
		}
		
		for (Rider rider : riders) {
			emails.add(rider.getEmail());
		}
		
		for (Client client : clients) {
			emails.add(client.getEmail());
		}
		
		if(emails.contains(email))
			isRepeatedEmail = true;
		else
			isRepeatedEmail = false;
		
		return isRepeatedEmail;		
	}
	
	/*********************************************************************
	*
	* - Method name: isRepeatedEmail
	* - Description of the Method: check if the email is repeated by role
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: user id
	* 		• String email: entered email
	* 		• String role: user role
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public boolean isRepeatedEmail(long id, String email, String role) {
		boolean isRepeatedEmail;
		
		List<Admin> admins = Manager.get().getAdminRepository().findAll();
		List<Rider> riders = Manager.get().getRiderRepository().findAll();
		List<Client> clients = Manager.get().getClientRepository().findAll();
		
		List<String> emails = new ArrayList<>();
		
		for (Admin admin : admins) {
			if(!(role.equals("ADMIN") && id == admin.getId())) {
				emails.add(admin.getEmail());
			}
		}
		
		for (Rider rider : riders) {
			if(!(role.equals("RIDER") && id == rider.getId())) {
				emails.add(rider.getEmail());
			}
		}
		
		for (Client client : clients) {
			if(!(role.equals("CLIENT") && id == client.getId())) {
				emails.add(client.getEmail());
			}
		}
		
		if(emails.contains(email))
			isRepeatedEmail = true;
		
		else
			isRepeatedEmail = false;
		
		return isRepeatedEmail;
	}
	
	/*********************************************************************
	*
	* - Method name: isRepeatedEmail
	* - Description of the Method: check if the email of restaurant is repeated
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• List<Restaurant> restaurants: restaurants list
	* 		• String email: entered email
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/	
	public boolean isRepeatedEmail(List<Restaurant> restaurants, String email) {
		boolean isRepeatedEmail;
		
		List<String> emails = new ArrayList<>();
		
		for (Restaurant restaurant : restaurants) {
			emails.add(restaurant.getEmail());
		}
		
		if(emails.contains(email))
			isRepeatedEmail = false;
		else 
			isRepeatedEmail = true;
		
		return isRepeatedEmail;		
	}
	
	/*********************************************************************
	*
	* - Method name: isValidPwd
	* - Description of the Method: check if the password is corrected
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String pwd: entered password
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/	
	public boolean isValidPwd(String pwd) {	
		boolean isValidPwd;
	
		PasswordValidator validator = new PasswordValidator(Arrays.asList(
			new LengthRule(8, 30), 
	        new UppercaseCharacterRule(1),
	        new LowercaseCharacterRule(1),
	        new DigitCharacterRule(1), 
	        new SpecialCharacterRule(1), 
	        new WhitespaceRule()));

	    RuleResult result = validator.validate(new PasswordData(pwd));
	        
	    if (!result.isValid()) 
	    	isValidPwd = false;
	    else
	    	isValidPwd = true;
	    
	    return isValidPwd;
	}
	
	/*********************************************************************
	*
	* - Method name: isValidPhone
	* - Description of the Method: check if the phone is corrected
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String phone: entered phone
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/	
	public boolean isValidPhone(String phone) {
		boolean validPhone = true;	
		
		phone = phone.replace(" ","");	
		if(phone.length() != 9) 
			validPhone = false;
		else {
			if(!phone.matches(checkNumbers))
				validPhone = false;		
			if(!phone.substring(0, 1).matches("[6-9]*"))
				validPhone = false;				
		}
		
		return validPhone;	
	}
	
	/*********************************************************************
	*
	* - Method name: isValidNIF
	* - Description of the Method: check if the NIF is corrected
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String NIF: entered NIF
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public boolean isValidNIF(String nif) {
		boolean validNIF = true;
		
		if (nif.length() != 9)
			validNIF = false;
		else {
			if(!nif.substring(0, nif.length()-1).matches(checkNumbers))
				validNIF = false;			
			if(!Character.isAlphabetic(nif.charAt(nif.length()- 1))){
				validNIF = false;
			}		
		}
		
		return validNIF;
	}
	
	/*********************************************************************
	*
	* - Method name: isValidName
	* - Description of the Method: check if the name is corrected
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String name: entered name
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public boolean isValidName(String name) {
		return checkNumber(name);
	}
	
	/*********************************************************************
	*
	* - Method name: isValidSurname
	* - Description of the Method: check if the surname is corrected
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String surname: entered surname
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public boolean isValidSurname(String surname) {
		return checkNumber(surname);
	}
	
	/*********************************************************************
	*
	* - Method name: checkNumber
	* - Description of the Method: check if the string has numbers
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String string: string to check
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public boolean checkNumber(String string) {
		for (int i = 0; i < string.length(); i++) {
			if(Character.isDigit(string.charAt(i))) {
	            return false;
	        }
	      }
		return true;
	}
	
	/*********************************************************************
	*
	* - Method name: isValidCIF
	* - Description of the Method: check if the CIF is corrected
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String CIF: entered CIF
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public boolean isValidCIF(String cif) {
		boolean validCIF = true;
		
		if (cif.length() != 9)
			validCIF = false;
		else {
			if(!cif.substring(0, cif.length()-1).matches(checkNumbers))
				validCIF = false;			
			if(!Character.isAlphabetic(cif.charAt(cif.length()- 1))){
				validCIF = false;
			}		
		}
		
		return validCIF;
	}
	
	/*********************************************************************
	*
	* - Method name: isRespeatedPlate
	* - Description of the Method: check if the plate is repeated
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• List<Plate> plates: plates list
	* 		• String name: plate name
	* 		• long id: plate id
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public boolean isRepeatedPlate(List<Plate> plates, String name, long id) {
		boolean isRepeatedPlate;
		
		List<String> names = new ArrayList<>();
		
		for (Plate plate : plates) {
			if(plate.getId() != id)
				names.add(plate.getName());
		}
		
		if(names.contains(name))
			isRepeatedPlate = true;
		else
			isRepeatedPlate = false;
			
		return isRepeatedPlate;
	}
	
	/*********************************************************************
	*
	* - Method name: isValidLicensePlate
	* - Description of the Method: check if the license plate is corrected
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String licensePlate: license plate number
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public boolean isValidLicensePlate(String licensePlate) {
		
		boolean validLicensePlate = true;
		
		licensePlate = licensePlate.replace(" ","");
		
		if (licensePlate.length() != 7)
			validLicensePlate = false;
		else {
			if(!licensePlate.substring(0, licensePlate.length()-3).matches(checkNumbers))
				validLicensePlate = false;		
			if(!licensePlate.substring(licensePlate.length()-2, licensePlate.length()).matches(checkAlphabetic))
				validLicensePlate = false;
		}
		return validLicensePlate;
	}

	/*********************************************************************
	*
	* - Method name: isRespeatedPlate
	* - Description of the Method: check if the plate is repeated
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• List<Plate> plates: plates list
	* 		• String name: plate name
	* - Return value: boolean
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public boolean isRepeatedPlate(List<Plate> plates, String name) {
		boolean isRepeatedPlate;
		
		List<String> names = new ArrayList<>();
		
		for (Plate plate : plates) {
			names.add(plate.getName());
		}
		
		if(names.contains(name))
			isRepeatedPlate = true;
		else
			isRepeatedPlate = false;
			
		return isRepeatedPlate;
	}

}
