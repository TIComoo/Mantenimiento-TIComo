package uclm.esi.equipo01.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.esi.equipo01.model.Plate;

import java.util.List;

/*********************************************************************
*
* Class Name: PlateRepository.
* Class description: Provides mechanism for storage, retrieval, search, update and delete operation on plate Objects.
*
**********************************************************************/
@Repository
public interface PlateRepository extends MongoRepository<Plate, Long>{
	
	List<Plate> findPlateByRestaurantID(long restaurantID);

}