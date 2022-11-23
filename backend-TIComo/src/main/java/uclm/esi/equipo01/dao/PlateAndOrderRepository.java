package uclm.esi.equipo01.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.esi.equipo01.model.PlateAndOrder;

/*********************************************************************
*
* Class Name: PlateAndOrderRepository.
* Class description: Provides mechanism for storage, retrieval, search, update and delete operation on order Objects.
*
**********************************************************************/
@Repository
public interface PlateAndOrderRepository extends MongoRepository<PlateAndOrder, Long>{

	List<PlateAndOrder> findPlateAndOrderByOrderID(long orderID);
}
