package uclm.esi.equipo01.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.esi.equipo01.model.Restaurant;

/*********************************************************************
*
* Class Name: RestaurantRepository.
* Class description: Provides mechanism for storage, retrieval, search, update and delete operation on restaurant Objects.
*
**********************************************************************/
@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, Long>{
	

}