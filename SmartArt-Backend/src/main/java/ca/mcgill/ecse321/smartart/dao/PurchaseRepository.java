package ca.mcgill.ecse321.smartart.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartart.model.Purchase;

/**
 * 
 * @author Group 12
 * Interface used as a template for a repository of purchases.
 */
public interface PurchaseRepository extends CrudRepository<Purchase, Integer>{

	Purchase findPurchaseByPurchaseID(int purchaseID);

}