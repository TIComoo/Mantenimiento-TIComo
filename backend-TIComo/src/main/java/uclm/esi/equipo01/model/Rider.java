package uclm.esi.equipo01.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import uclm.esi.equipo01.http.Manager;

/*********************************************************************
*
* Class Name: Rider
* Class description: Dedicated to the management rider methods and data
*
**********************************************************************/

@Document(collection = "Riders")
public class Rider extends User{
	
	@Transient
	public static final int SEQUENCE_ID = Sequence.RIDER.getValue();
	
	private String nif;
	private String vehicleType;
	private String licensePlate;
	private boolean license;
	private boolean activeAccount;
	private double averageRate;
	
	public Rider() {
		super();
	}
	
	/*********************************************************************
	*
	* - Method name: Rider
	* - Description of the Method: Rider class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String email: rider email
	* 		• String pwd: rider password
	* 		• String name: rider name
	* 		• String surname: rider surname
	* 		• String nif: rider nif
	* 		• String vehicleType: rider vehicle type
	* 		• String licensePlate: rider license plate
	* 		• boolean license: if rider has license
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public Rider(String email, String pwd, String name, String surname, String nif, String vehicleType, String licensePlate, 
			boolean license) {
		super(email, pwd, name, surname);
		super.setId(Manager.get().generateSequence(SEQUENCE_ID));
		this.nif = nif;
		this.setVehicleType(vehicleType);
		this.licensePlate = licensePlate;
		this.license = license;
		this.activeAccount = true;
		this.averageRate = 0;
	}

	/*********************************************************************
	*
	* - Method name: Rider
	* - Description of the Method: Rider class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• long id: rider id
	* 		• String name: rider name
	* 		• String surname: rider surname
	* 		• String nif: rider nif
	* 		• String vehicleType: rider vehicle type
	* 		• String licensePlate: rider license plate
	* 		• boolean license: if rider has license
	* 		• String email: rider email
	* 		• String pwd: rider password
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public Rider(long id, String name, String surname, String nif, String vehicle, String licensePlate,
			boolean license, String email, String password) {
		super(email, password, name, surname);
		super.setId(id);
		this.nif = nif;
		this.setVehicleType(vehicle);
		this.licensePlate = licensePlate;
		this.license = license;
		this.activeAccount = true;	
	}
	
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public boolean isLicense() {
		return license;
	}

	public void setLicense(boolean license) {
		this.license = license;
	}

	public boolean isActiveAccount() {
		return activeAccount;
	}

	public void setActiveAccount(boolean activeAccount) {
		this.activeAccount = activeAccount;
	}

	public double getAverageRate() {
		return averageRate;
	}

	public void setAverageRate(double averageRate) {
		this.averageRate = averageRate;
	}

	@Override
	public String toString() {
		return "Rider [nif=" + nif + ", licensePlate=" + licensePlate + ", license=" + license + ", activeAccount="
				+ activeAccount + "]";
	}
	
}