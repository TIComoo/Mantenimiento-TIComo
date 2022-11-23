package uclm.esi.equipo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*********************************************************************
*
* Class Name: TiComoApplication.
* Class description: Main class of the project.
*
**********************************************************************/

@SpringBootApplication
public class TiComoApplication {
	
	/*********************************************************************
	*
	* - Method name: main.
	* - Description of the Method: Starts the application.
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• String[] args: Contains the supplied command-line arguments as an array of String Objects.
	* - Return value: Manager None.
	* - Required Files: None.
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public static void main(String[] args) {
		SpringApplication.run(TiComoApplication.class, args);

	}
	
}
