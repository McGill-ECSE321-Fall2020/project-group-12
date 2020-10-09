package ca.mcgill.ecse321.smartart.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartart.model.Buyer;

/**
 * 
 * @author Group 12
 * Interface used as a template for a repository of buyers.
 */
public interface BuyerRepository extends CrudRepository<Buyer, String>{

	Buyer findBuyerByEmail(String email);

}