package uclm.esi.equipo01.dao;


import org.springframework.data.mongodb.repository.MongoRepository;

import uclm.esi.equipo01.model.Admin;

/*********************************************************************
*
* Class Name: AdminRepository.
* Class description: Provides mechanism for storage, retrieval, search, update and delete operation on admin Objects.
*
**********************************************************************/

public interface AdminRepository extends MongoRepository<Admin, Long> {

	Admin findByEmailAndPwd(String email, String pwd);

}