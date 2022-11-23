package uclm.esi.equipo01.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/*********************************************************************
*
* Class Name: User
* Class description: Dedicated to the management user methods and data
*
**********************************************************************/

public abstract class User {
	
	@Indexed(unique = true)
    private String email;
	
    private String pwd;
	private String name;
	private String surname;
	
	@Id
    private long id;
	
	protected User() {
		super();
	}
	
	/*********************************************************************
	*
	* - Method name: User
	* - Description of the Method: User class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String email: user email
	* 		• String pwd: user password
	* 		• String name: user name
	* 		• String surname: user surname
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	protected User(String email, String pwd, String name, String surname) {
		super();
		this.email = email;
		this.pwd = org.apache.commons.codec.digest.DigestUtils.sha512Hex(pwd);
		this.name = name;
		this.surname = surname;
	}
	
	/*********************************************************************
	*
	* - Method name: User
	* - Description of the Method: User class constructor
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String email: user email
	* 		• String pwd: user password
	* - Return value: None
	* - Required Files: None
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None
	*
	*********************************************************************/
	protected User(String email, String pwd) {
		this.email = email;
		this.pwd = pwd;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = org.apache.commons.codec.digest.DigestUtils.sha512Hex(pwd);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", pwd=" + pwd + ", name=" + name + ", surname=" + surname + "]";
	}

	public abstract boolean isActiveAccount();

}
