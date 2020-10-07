package ca.mcgill.ecse321.smartart.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartart.model.Administrator;

/**
 * 
 * @author Group 12
 * Interface used as a template for a repository of administrators.
 */
public interface AdministratorRepository extends CrudRepository<Administrator, String>{

	Administrator findAdministratorByEmail(String email);

}