package uclm.esi.equipo01.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import uclm.esi.equipo01.model.Plate;

import java.util.List;

/*********************************************************************
*
* Class Name: PlateRepository.
* Class description: Provides mechanism for storage, retrieval, search, update and delete operation on plate Objects.
*
**********************************************************************/
public interface PlateRepository extends MongoRepository<Plate, Long>{
	
	List<Plate> findPlateByRestaurantID(long restaurantID);

}