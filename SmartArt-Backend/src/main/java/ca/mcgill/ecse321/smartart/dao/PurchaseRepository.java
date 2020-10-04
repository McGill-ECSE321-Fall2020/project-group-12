package ca.mcgill.ecse321.smartart.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartart.model.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer>{

	Purchase findPurchaseByPurchaseID(int purchaseID);

}