package uclm.esi.equipo01.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import uclm.esi.equipo01.http.Manager;

/*********************************************************************
*
* Class Name: Restaurant
* Class description: Dedicated to the management restaurant methods and data
*
**********************************************************************/

@Document(collection = "Restaurants")
public class Restaurant {
	
	@Transient
	public static final int SEQUENCE_ID = Sequence.RESTAURANT.getValue();
	
	@Id
    private long id;
	
	private String name;
	
	private String email;
	
	private String cif;
	private String address;
	private String phone;
	private String category;
	private String commercialRegister;
	private double averageRate;
	
	public Restaurant() {
		super();
	}
	
	/*********************************************************************
	*
	* - Method name: Client
	* - Description of the Method: Client class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String name: restaurant name
	* 		• String commercialRegister: restaurant commercial register
	* 		• String cif: restaurant cif
	* 		• String address: restaurant address
	* 		• String phone: restaurant phone
	* 		• String email: restaurant email
	* 		• String category: restaurant category
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	public Restaurant(String name, String commercialRegister, String cif, String address, String phone,
			String email, String category) {
		super();
		this.setId(Manager.get().generateSequence(SEQUENCE_ID));
		this.name = name;
		this.commercialRegister = commercialRegister;
		this.cif = cif;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.category = category;
		this.averageRate = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCommercialRegister() {
		return commercialRegister;
	}

	public void setCommercialRegister(String commercialRegister) {
		this.commercialRegister = commercialRegister;
	}

	public double getAverageRate() {
		return averageRate;
	}

	public void setAverageRate(double averageRate) {
		this.averageRate = averageRate;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", email=" + email + ", cif=" + cif
				+ ", address=" + address + ", phone=" + phone + ", category=" + category + ", commercialRegister="
				+ commercialRegister + "]";
	}
	
}
