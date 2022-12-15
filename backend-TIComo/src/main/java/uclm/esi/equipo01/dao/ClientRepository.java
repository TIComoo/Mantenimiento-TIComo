package uclm.esi.equipo01.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import uclm.esi.equipo01.model.Client;

/*********************************************************************
*
* Class Name: ClientRepository.
* Class description: Provides mechanism for storage, retrieval, search, update and delete operation on client Objects.
*
**********************************************************************/
public interface ClientRepository extends MongoRepository<Client, Long> {
	
	Client findByEmailAndPwd(String email, String pwd);
	
}