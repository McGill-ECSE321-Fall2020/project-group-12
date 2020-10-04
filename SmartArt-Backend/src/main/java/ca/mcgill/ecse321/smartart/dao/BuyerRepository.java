package ca.mcgill.ecse321.smartart.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartart.model.Buyer;

public interface BuyerRepository extends CrudRepository<Buyer, String>{

	Buyer findBuyerByEmail(String email);

}