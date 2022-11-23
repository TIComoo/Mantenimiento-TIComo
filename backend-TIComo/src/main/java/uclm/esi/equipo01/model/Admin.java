package uclm.esi.equipo01.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import uclm.esi.equipo01.http.Manager;


/*********************************************************************
*
* Class Name: Admin
* Class description: Dedicated to the management admin methods and data
*
**********************************************************************/

@Document(collection = "Admins")
public class Admin extends User{
	
	@Transient
	public static final int SEQUENCE_ID = Sequence.ADMIN.getValue();
	
	private String zone;
	
	public Admin() {
		super();
	}

	/*********************************************************************
	*
	* - Method name: Admin
	* - Description of the Method: Admin class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String email: admin email
	* 		• String pwd: admin password
	* 		• String name: admin name
	* 		• String surname: admin surname
	* 		• String zone: admin zone
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public Admin(String email, String pwd, String name, String surname, String zone) {
		super(email, pwd, name, surname);
		super.setId(Manager.get().generateSequence(SEQUENCE_ID));
		this.zone = zone;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	@Override
	public boolean isActiveAccount() {
		return true;
	}
}