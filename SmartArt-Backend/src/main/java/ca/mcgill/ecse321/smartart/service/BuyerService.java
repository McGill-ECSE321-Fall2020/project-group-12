package ca.mcgill.ecse321.smartart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartart.dao.BuyerRepository;
import ca.mcgill.ecse321.smartart.dao.GalleryRepository;
import ca.mcgill.ecse321.smartart.dto.BuyerDto;
import ca.mcgill.ecse321.smartart.model.Buyer;
import ca.mcgill.ecse321.smartart.model.Gallery;

public class BuyerService {
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private GalleryRepository galleryRepository;
	@Autowired
	private ServiceHelper helper;
	
	@Transactional
	public Buyer createBuyer(String email, String name, String password, Gallery gallery) {
		// Input validation
	    String error = "";
	    if (email == null || email.trim().length() == 0) {
	        error = error + "Buyer email cannot be empty. ";
	    }
	    if (name == null || name.trim().length() == 0) {
	        error = error + "Buyer name cannot be empty. ";
	    }
	    if (password == null || password.trim().length() == 0) {
	        error = error + "Buyer password cannot be empty. ";
	    }
	    if (gallery == null) {
	        error = error + "Buyer gallery cannot be empty. ";
	    }
	    
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
	    
	    
		Buyer buyer = new Buyer();
		buyer.setEmail(email);
		buyer.setName(name);
		buyer.setPassword(password);
		gallery.addBuyer(buyer);
		buyerRepository.save(buyer);
		galleryRepository.save(gallery);
		return buyer;
	}
	
	@Transactional
	public Buyer createBuyer(BuyerDto data) {
		Buyer buyer = createBuyer(data.getEmail(), data.getName(), data.getPassword(), helper.convertToModel(data.getGallery()));
		return buyer;
	}
	
	@Transactional
	public Buyer getBuyer(String email) {
		if (email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Buyer email cannot be empty.");
		}
		
		Buyer buyer = buyerRepository.findBuyerByEmail(email);
		return buyer;
	}
	
	@Transactional
	public List<Buyer> getAllBuyers(){
		return toList(buyerRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
