package uclm.esi.equipo01.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.esi.equipo01.model.Rider;

/*********************************************************************
*
* Class Name: RiderRepository.
* Class description: Provides mechanism for storage, retrieval, search, update and delete operation on rider Objects.
*
**********************************************************************/
@Repository
public interface RiderRepository extends MongoRepository<Rider, Long> {

	Rider findByEmailAndPwd(String email, String pwd);

}