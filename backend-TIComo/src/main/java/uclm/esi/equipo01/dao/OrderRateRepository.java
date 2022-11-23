package uclm.esi.equipo01.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.esi.equipo01.model.OrderRate;

/*********************************************************************
*
* Class Name: OrderRepository.
* Class description: Provides mechanism for storage, retrieval, search, update and delete operation on order Objects.
*
**********************************************************************/
@Repository
public interface OrderRateRepository extends MongoRepository<OrderRate, Long> {
	
	List<OrderRate> findByRestaurantID(long restaurantID);
	List<OrderRate> findByRiderID(long riderID);

}
