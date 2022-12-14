package uclm.esi.equipo01.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.esi.equipo01.model.AtencionTelefonica;

/*********************************************************************
*
* Class Name: AtencionTelefonicaRepository.
* Class description: Provides mechanism for storage, retrieval, search, update and delete operation on atencion telefonica Objects.
*
**********************************************************************/
@Repository
public interface AtencionTelefonicaRepository extends MongoRepository<AtencionTelefonica, Long> {
	AtencionTelefonica findByEmailAndPwd(String email, String pwd);
}
