package ca.mcgill.ecse321.smartart.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartart.dao.*;
import ca.mcgill.ecse321.smartart.model.*;

@Service
public class SmartArtService {

	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private AdministratorRepository administratorRepository;
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private GalleryRepository galleryRepository;
	@Autowired
	private PostingRepository postingRepository;
	@Autowired
	private PurchaseRepository purchaseRepository;

	@Transactional
	public Administrator createAdministrator(String email, String name, String password, Gallery gallery) {
		// Input validation
	    String error = "";
	    if (email == null || email.trim().length() == 0) {
	        error = error + "Administrator email cannot be empty. ";
	    }
	    if (name == null || name.trim().length() == 0) {
	        error = error + "Administrator name cannot be empty. ";
	    }
	    if (password == null || password.trim().length() == 0) {
	        error = error + "Administrator password cannot be empty. ";
	    }
	    if (gallery == null) {
	        error = error + "Administrator gallery cannot be empty. ";
	    }
	    
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
	    
	    
		Administrator administrator = new Administrator();
		administrator.setEmail(email);
		administrator.setName(name);
		administrator.setPassword(password);
		gallery.addAdministrator(administrator);
		administratorRepository.save(administrator);
		galleryRepository.save(gallery);
		return administrator;
	}
	
	@Transactional
	public Administrator getAdministrator(String email) {
		if (email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Administrator email cannot be empty.");
		}
		
		Administrator administrator = administratorRepository.findAdministratorByEmail(email);
		return administrator;
	}
	
	@Transactional
	public List<Administrator> getAllAdministrators(){
		return toList(administratorRepository.findAll());
	}
	
	@Transactional
	public Artist createArtist(String email, String name, String password, Gallery gallery) {
		// Input validation
	    String error = "";
	    if (email == null || email.trim().length() == 0) {
	        error = error + "Artist email cannot be empty. ";
	    }
	    if (name == null || name.trim().length() == 0) {
	        error = error + "Artist name cannot be empty. ";
	    }
	    if (password == null || password.trim().length() == 0) {
	        error = error + "Artist password cannot be empty. ";
	    }
	    if (gallery == null) {
	        error = error + "Artist gallery cannot be empty. ";
	    }
	    
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
		
	    
		Artist artist = new Artist();
		artist.setEmail(email);
		artist.setName(name);
		artist.setPassword(password);
		gallery.addArtist(artist);
		artistRepository.save(artist);
		galleryRepository.save(gallery);
		return artist;
	}
	
	@Transactional
	public Artist getArtist(String email) {
		if (email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Artist email cannot be empty.");
		}
		
		Artist artist = artistRepository.findArtistByEmail(email);
		return artist;
	}
	
	@Transactional
	public List<Artist> getAllArtists(){
		return toList(artistRepository.findAll());
	}
	
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
	
	@Transactional
	public Gallery createGallery(String name, String city, double commission) {
		// Input validation
	    String error = "";
	    if (name == null || name.trim().length() == 0) {
	        error = error + "Gallery name cannot be empty. ";
	    }
	    if (city == null || city.trim().length() == 0) {
	        error = error + "Gallery city cannot be empty. ";
	    }
	    if (commission < 0) {
	        error = error + "Gallery commission cannot be less than 0. ";
	    }
	    
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
	    
	    
		Gallery gallery = new Gallery();
		gallery.setName(name);
		gallery.setCity(city);
		gallery.setCommission(commission);
		galleryRepository.save(gallery);
		return gallery;
	}
	
	@Transactional
	public Gallery getGallery(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Gallery name cannot be empty.");
		}
		Gallery gallery = galleryRepository.findGalleryByName(name);
		return gallery;
	}
	
	@Transactional
	public List<Gallery> getAllGalleries(){
		return toList(galleryRepository.findAll());
	}
	
	@Transactional
	public Posting createPosting(int postingID, Artist artist, double price, double x, double y, double z, String title, String description, Date date) {
		// Input validation
	    String error = "";
	    if (artist == null) {
	        error = error + "Posting artist cannot be empty. ";
	    }
	    if (title == null || title.trim().length() == 0) {
	        error = error + "Posting title cannot be empty. ";
	    }
	    if (description == null || description.trim().length() == 0) {
	        error = error + "Posting description cannot be empty. ";
	    }
	    if (price <= 0) {
	        error = error + "Posting price must be above 0. ";
	    }
	    if (x <= 0) {
	        error = error + "Posting xDim must be above 0. ";
	    }
	    if (y <= 0) {
	        error = error + "Posting yDim must be above 0. ";
	    }
	    if (z <= 0) {
	        error = error + "Posting zDim must be above 0. ";
	    }
	    
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
		
		
		Posting posting = new Posting();
		posting.setPostingID(postingID);
		artist.addPosting(posting);
		artist.getGallery().addPosting(posting);
		posting.setArtStatus(ArtStatus.Available);
		posting.setXDim(x);
		posting.setYDim(y);
		posting.setZDim(z);
		posting.setTitle(title);
		posting.setDescription(description);
		posting.setDate(date);
		postingRepository.save(posting);
		artistRepository.save(artist);
		galleryRepository.save(artist.getGallery());
		return posting;
	}
	
	@Transactional
	public Posting getPosting(int postingID) {
		Posting posting = postingRepository.findPostingByPostingID(postingID);
		return posting;
	}
	
	@Transactional
	public List<Posting> getAllPostings(){
		return toList(postingRepository.findAll());
	}
	
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
	    
	   
		Purchase purchase = new Purchase();
		purchase.setPurchaseID(purchaseID);
		purchase.setTotalPrice(0);
		buyer.addPurchase(purchase);
		purchaseRepository.save(purchase);
		buyerRepository.save(buyer);
		return purchase;
	}
	
	@Transactional
	public Purchase getPurchase(int purchaseID) {
		Purchase purchase = purchaseRepository.findPurchaseByPurchaseID(purchaseID);
		return purchase;
	}
	
	@Transactional
	public List<Purchase> getAllPurchases(){
		return toList(purchaseRepository.findAll());
	}
	
	///////////////////////
	////Action Methods/////
	///////////////////////
	
	@Transactional
	public void addToCart(Buyer buyer, Posting posting) {
		// Input validation
	    String error = "";
	    if (buyer == null) {
	        error = error + "addToCart buyer cannot be empty. ";
	    }
	    if (posting == null) {
	        error = error + "addToCart posting cannot be empty. ";
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
		cart.setTotalPrice(cart.getTotalPrice() + posting.getPrice());
	}
	
	@Transactional
	public void removeFromCart(Buyer buyer, Posting posting) {
		// Input validation
	    String error = "";
	    if (buyer == null) {
	        error = error + "removeFromCart buyer cannot be empty. ";
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

	}
	
	@Transactional
	public void removePosting(Posting posting) throws NullPointerException{
		Artist artist = posting.getArtist();
		artist.removePosting(posting);
		artist.getGallery().getPostings().remove(posting);
		posting = null;
		
	}
	
	@Transactional
	public boolean makePurchase(Purchase purchase, DeliveryType deliveryType) {
		if(purchase == null || purchase.getTotalPrice() <= 0) return false;
		
		Buyer buyer = purchase.getBuyer();
		
		for(Posting p: purchase.getPostings()) {
			p.setArtStatus(ArtStatus.Purchased);
		}
		
		purchase.setDeliveryType(deliveryType);
		buyer.setCart(null);
		return true;
	}

	
	///////////////////////////////
	///Private helper methods//////
	///////////////////////////////
	
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
	
	private int generatePostingID() {
		Random r = new Random();
		int id = r.nextInt();
		while(postingRepository.findPostingByPostingID(id) != null) {
			id = r.nextInt();
		}
		return id;
	}

}
