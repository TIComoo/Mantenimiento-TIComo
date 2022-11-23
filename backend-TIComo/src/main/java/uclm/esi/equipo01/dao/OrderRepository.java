package uclm.esi.equipo01.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.esi.equipo01.model.Order;

/*********************************************************************
*
* Class Name: OrderRepository.
* Class description: Provides mechanism for storage, retrieval, search, update and delete operation on order Objects.
*
**********************************************************************/
@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

	List<Order> findByClientID(long clientID);
	List<Order> findByRiderID(long riderID);
	List<Order> findByRestaurantID(long restaurantID);
}
