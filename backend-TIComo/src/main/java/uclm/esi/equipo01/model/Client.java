package uclm.esi.equipo01.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import uclm.esi.equipo01.http.Manager;

/*********************************************************************
*
* Class Name: Client
* Class description: Dedicated to the management client methods and data
*
**********************************************************************/

@Document(collection = "Clients")
public class Client extends User {
	
	@Transient
	public static final int SEQUENCE_ID = Sequence.CLIENT.getValue();
	
    private String nif;	
    private String address;
    private String phone;
    private boolean activeAccount;
    
	public Client() {
		super();
	}
    
	/*********************************************************************
	*
	* - Method name: Client
	* - Description of the Method: Client class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String email: client email
	* 		• String pwd: client password
	* 		• String name: client name
	* 		• String surname: client surname
	* 		• String NIF: client NIF
	* 		• String address: client address
	* 		• String phone: client phone
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public Client(String email, String pwd, String name, String surname, String nif, String address, 
			String phone) {
		super(email, pwd, name, surname);
		super.setId(Manager.get().generateSequence(SEQUENCE_ID));
		this.nif = nif;
		this.address = address;
		this.phone = phone;
		this.activeAccount = true;
	}

	/*********************************************************************
	*
	* - Method name: Client
	* - Description of the Method: Client class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String email: client email
	* 		• String pwd: client password
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public Client(String email, String pwd) {
		super(email,pwd);
	}

	public String getNIF() {
		return nif;
	}

	public void setNIF(String nif) {
		this.nif = nif;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isActiveAccount() {
		return activeAccount;
	}
	
	public void setActiveAccount(boolean activeAccount) {
		this.activeAccount = activeAccount;
	}

	@Override
	public String toString() {
		return "Client [NIF=" + nif + ", address=" + address + ", phone=" + phone + ", activeAccount="
				+ activeAccount + "]";
	}
	
}