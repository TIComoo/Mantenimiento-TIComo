package uclm.esi.equipo01.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import uclm.esi.equipo01.dao.AdminRepository;
import uclm.esi.equipo01.dao.ClientRepository;
import uclm.esi.equipo01.dao.OrderRateRepository;
import uclm.esi.equipo01.dao.OrderRepository;
import uclm.esi.equipo01.dao.PlateRepository;
import uclm.esi.equipo01.dao.RestaurantRepository;
import uclm.esi.equipo01.dao.RiderRepository;
import uclm.esi.equipo01.dao.PlateAndOrderRepository;
import uclm.esi.equipo01.service.SequenceGeneratorService;

/*********************************************************************
*
* Class Name: Manager.
* Class description: Manages a set of objects of another class, which are often resources.
*
**********************************************************************/
@Component
public class Manager {
	
	@Autowired
	private SequenceGeneratorService sequenceGenerator;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RiderRepository riderRepository;
	
	@Autowired
	private ClientRepository clientRepository; 
	
	@Autowired 
	private RestaurantRepository restaurantRepository; 
	
	@Autowired
	private PlateRepository plateRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderRateRepository orderRateRepository;
	
	@Autowired
	private PlateAndOrderRepository plateAndOrderRepository;
		
	/*********************************************************************
	*
	* Class Name: ManagerHolder.
	* Class description: Manages the Manager Object following the singleton pattern.
	*
	**********************************************************************/
	private static class ManagerHolder {
		static Manager singleton = new Manager();
	}
	
	/*********************************************************************
	*
	* - Method name: get.
	* - Description of the Method: Gets the instance of the Manager Object.
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do: None.
	* - Return value: Manager Object.
	* - Required Files: None.
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Bean
	public static Manager get() {
		return ManagerHolder.singleton;
	}
	
	/*********************************************************************
	*
	* - Method name: generateSequence.
	* - Description of the Method: Generates id for an Object.
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• int sequenceId: Sequence id of the Object.
	* - Return value: Id of the Object.
	* - Required Files: None.
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	public long generateSequence(int sequenceId) {
		return sequenceGenerator.generateSequence(sequenceId);
	}
	
	public long setSequence(int sequenceId, int value) {
		return sequenceGenerator.setSequence(sequenceId, value);
	}
	
	public AdminRepository getAdminRepository() {
		return adminRepository;
	}
	
	public RiderRepository getRiderRepository() {
		return riderRepository;
	}

	public ClientRepository getClientRepository() {
		return clientRepository;
	}

	public RestaurantRepository getRestaurantRepository() {
		return restaurantRepository;
	}
	
	public PlateRepository getPlateRepository() {
		return plateRepository;
	}
	
	public OrderRepository getOrderRepository() {
		return orderRepository;
	}
	
	public OrderRateRepository getOrderRateRepository() {
		return orderRateRepository;
	}
	
	public PlateAndOrderRepository getPlateAndOrderRepository() {
		return plateAndOrderRepository;
	}
	
}