package ca.mcgill.ecse321.smartart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartart.dao.BuyerRepository;
import ca.mcgill.ecse321.smartart.dao.PostingRepository;
import ca.mcgill.ecse321.smartart.dao.PurchaseRepository;
import ca.mcgill.ecse321.smartart.dto.BuyerDto;
import ca.mcgill.ecse321.smartart.dto.PurchaseDto;
import ca.mcgill.ecse321.smartart.model.ArtStatus;
import ca.mcgill.ecse321.smartart.model.Buyer;
import ca.mcgill.ecse321.smartart.model.DeliveryType;
import ca.mcgill.ecse321.smartart.model.Posting;
import ca.mcgill.ecse321.smartart.model.Purchase;

@Service
public class PurchaseService {
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private PostingRepository postingRepository;
	@Autowired
	private ServiceHelper helper;
	@Transactional
	public Purchase createPurchase(int purchaseID, Buyer buyer) {
		// Input validation
	    String error = "";
	    if (buyer == null) {
	        error = error + "Purchase buyer cannot be empty. ";
	    }
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
	    if (purchaseRepository.findPurchaseByPurchaseID(purchaseID) != null) {
	        throw new IllegalArgumentException("A purchase with this ID already exists.");
	    }
	    
	   
		Purchase purchase = new Purchase();
		purchase.setPurchaseID(purchaseID);
		purchase.setBuyer(buyer);
		purchase.setTotalPrice(0);
		buyer.addPurchase(purchase);
		purchaseRepository.save(purchase);
		buyerRepository.save(buyer);
		return purchase;
	}
	
	@Transactional
	public Purchase createPurchase(PurchaseDto data) throws IllegalArgumentException {
		Purchase purchase = createPurchase(data.getPurchaseID(), helper.convertToModel(data.getBuyer()));
		return purchase;
	}
	
	@Transactional
	public Purchase getPurchase(int purchaseID) {
		Purchase purchase = purchaseRepository.findPurchaseByPurchaseID(purchaseID);
		return purchase;
	}
	
	@Transactional
	public List<Purchase> getPurchasesByBuyer(String email){
		Buyer buyer = buyerRepository.findBuyerByEmail(email);
		if(buyer == null)
			throw new IllegalArgumentException("No buyer with email " + email);
		return helper.toList(buyer.getPurchases());
	}
	
	@Transactional
	public Purchase getCart(String email) {
		Buyer buyer = buyerRepository.findBuyerByEmail(email);
		
		if(buyer == null) throw new IllegalArgumentException("No buyer with email " + email);
		
		return buyer.getCart();
	}
	
	@Transactional
	public List<Purchase> getAllPurchases(){
		return toList(purchaseRepository.findAll());
	}
	
	@Transactional
	public Purchase makePurchase(Purchase purchase, DeliveryType deliveryType) {
		if(purchase == null || purchase.getTotalPrice() <= 0) 
			throw new IllegalArgumentException("Must have a purchase order to make purchase");
		
		if(deliveryType != DeliveryType.PickUp && deliveryType != DeliveryType.Shipped)
			throw new IllegalArgumentException("Delivery Type not valid");
			
		Buyer buyer = purchase.getBuyer();
		
		for(Posting p: purchase.getPostings()) {
			p.setArtStatus(ArtStatus.Purchased);
		}
		
		purchase.setDeliveryType(deliveryType);
		purchase.setTotalPrice(helper.calcFinalPrice(purchase));
		buyer.addPurchase(purchase);
		buyer.setCart(null);
		
		buyerRepository.save(buyer);
		purchaseRepository.save(purchase);
		
		return purchase;
	}
	
	@Transactional
	public Purchase makePurchase(PurchaseDto data, DeliveryType deliveryType) throws IllegalArgumentException {
		Purchase purchase = purchaseRepository.findPurchaseByPurchaseID(data.getPurchaseID());
		
		return makePurchase(purchase, deliveryType);
	}
	
	@Transactional
	public boolean cancelPurchase(Purchase purchase) {
		if(purchase == null) 
			throw new IllegalArgumentException("Must have a purchase to cancel purchase");
		
		LocalDateTime now = LocalDateTime.now();
		Buyer buyer = purchase.getBuyer();
		
		if(now.minusMinutes(10).isAfter(purchase.getTime())) return false;
		
		for(Posting p : purchase.getPostings())
			p.setArtStatus(ArtStatus.Available);
		
		buyer.removePurchase(purchase);
		buyerRepository.save(buyer);
		purchaseRepository.delete(purchase);
		return true;
	}
	
	@Transactional
	public boolean cancelPurchase(PurchaseDto data) throws IllegalArgumentException {
		Purchase purchase = purchaseRepository.findPurchaseByPurchaseID(data.getPurchaseID());
		
		return cancelPurchase(purchase);
	}
	
	@Transactional
	public Purchase addToCart(Buyer buyer, Posting posting) {
		// Input validation
	    String error = "";
	    if (buyer == null) {
	        error = error + "addToCart buyer cannot be empty. ";
	    }
	    if (posting == null) {
	        error = error + "addToCart posting cannot be empty. ";
	        throw new IllegalArgumentException(error);
	    }
	    if (posting.getArtStatus() != ArtStatus.Available) {
	    	error = error + "addToCart posting cannot be On Hold or Purchased. ";
	    }
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
	    
		Purchase cart = buyer.getCart();
		if(cart == null) {
			int id = generatePurchaseID();
			cart = createPurchase(id, buyer);
			buyer.setCart(cart);
		}
		cart.addPosting(posting);
		posting.setArtStatus(ArtStatus.OnHold);
		purchaseRepository.save(cart);
		postingRepository.save(posting);
		buyerRepository.save(buyer);
		return cart;
	}
	
	@Transactional
	public Purchase addToCart(BuyerDto buyerData, int postingID) throws IllegalArgumentException {
		Buyer buyer = buyerRepository.findBuyerByEmail(buyerData.getEmail());
		Posting posting = postingRepository.findPostingByPostingID(postingID);
		
		return addToCart(buyer, posting);
	}
	
	@Transactional
	public Purchase removeFromCart(Buyer buyer, Posting posting) {
		// Input validation
	    String error = "";
	    if (buyer == null) {
	        error = error + "removeFromCart buyer cannot be empty. ";
	        throw new IllegalArgumentException(error);
	    }
	    
	    Purchase cart = buyer.getCart();
	    if (cart == null) {
	        error = error + "removeFromCart cart cannot be null. ";
	    }
	    if (posting == null) {
	        error = error + "removeFromCart posting cannot be empty. ";
	    }
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
		
		if(cart.removePosting(posting)) {
			posting.setArtStatus(ArtStatus.Available);
			cart.setTotalPrice(cart.getTotalPrice() - posting.getPrice());
		}	
		purchaseRepository.save(cart);
		postingRepository.save(posting);
		return cart;
	}
	@Transactional
	public Purchase removeFromCart(BuyerDto buyerData, int postingID) throws IllegalArgumentException {
		Buyer buyer = buyerRepository.findBuyerByEmail(buyerData.getEmail());
		Posting posting = postingRepository.findPostingByPostingID(postingID);
		
		return removeFromCart(buyer, posting);
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	private int generatePurchaseID() {
		Random r = new Random();
		int id = r.nextInt();
		while(purchaseRepository.findPurchaseByPurchaseID(id) != null) {
			id = r.nextInt();
		}
		return id;
	}
	
}
