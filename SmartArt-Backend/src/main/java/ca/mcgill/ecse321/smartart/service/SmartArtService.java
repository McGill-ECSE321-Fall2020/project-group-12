package ca.mcgill.ecse321.smartart.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartart.dao.*;
import ca.mcgill.ecse321.smartart.dto.*;
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
	public Administrator createAdministrator(AdministratorDto data) {
		Administrator admin = createAdministrator(data.getEmail(), data.getName(), data.getPassword(), convertToModel(data.getGallery()));
		return admin;
	}
	
	@Transactional
	public Administrator getAdministrator(String email) {
		if (email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Administrator email cannot be empty.");
		}
		
		Administrator administrator = administratorRepository.findAdministratorByEmail(email);
		if (administrator == null) {
			throw new IllegalArgumentException("Administrator does not exist.");
		}
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
	public Artist createArtist(ArtistDto data) {
		Artist artist = createArtist(data.getEmail(), data.getName(), data.getPassword(), convertToModel(data.getGallery()));
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
	public Buyer createBuyer(BuyerDto data) {
		Buyer buyer = createBuyer(data.getEmail(), data.getName(), data.getPassword(), convertToModel(data.getGallery()));
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
	public Gallery createGallery(GalleryDto data) {
		Gallery gallery = createGallery(data.getName(), data.getCity(), data.getCommission());
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
	public Posting createPosting(int postingID, Artist artist, int price, double x, double y, double z, String title, String description, Date date, String image) {
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
		posting.setArtStatus(ArtStatus.Available);
		posting.setPrice(price);
		posting.setXDim(x);
		posting.setYDim(y);
		posting.setZDim(z);
		posting.setTitle(title);
		posting.setDescription(description);
		posting.setDate(date);
		posting.setImage(image);
		
		artist.addPosting(posting);
		artist.getGallery().addPosting(posting);
		
		postingRepository.save(posting);
		artistRepository.save(artist);
		galleryRepository.save(artist.getGallery());
		return posting;
	}
	
	@Transactional
	public Posting createPosting(PostingDto data) {
		Posting posting = createPosting(data.getPostingID(), convertToModel(data.getArtist()), data.getPrice(), data.getXDim(), data.getYDim(), data.getZDim(), data.getTitle(), data.getDescription(), data.getDate(), data.getImage());
		return posting;
	}
	
	@Transactional
	public Posting deletePosting(PostingDto data) {
		
		Posting posting = postingRepository.findPostingByPostingID(data.getPostingID());
		
		if(posting == null) throw new NullPointerException("Cannot remove null posting, perhaps it has already been deleted");
		
		if(posting.getArtStatus() == ArtStatus.Purchased) throw new IllegalArgumentException("Cannot delete a posting that has been purchased");
		
		Gallery gallery = posting.getGallery();
		Artist artist = posting.getArtist();
		artist.removePosting(posting);
		gallery.removePosting(posting);
		
		postingRepository.delete(posting);
		artistRepository.save(artist);
		galleryRepository.save(gallery);
		
		return posting;
	}
	@Transactional
	public Posting getPosting(int postingID) {
		Posting posting = postingRepository.findPostingByPostingID(postingID);
		return posting;
	}
	
	@Transactional
	public List<Posting> getPostingsByArtist(String email) {
		Artist artist = artistRepository.findArtistByEmail(email);
		if(artist == null)
			return null;
		return toList(artist.getPostings());
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
		purchase.setBuyer(buyer);
		purchase.setTotalPrice(0);
		buyer.addPurchase(purchase);
		purchaseRepository.save(purchase);
		buyerRepository.save(buyer);
		return purchase;
	}
	
	@Transactional
	public Purchase createPurchase(PurchaseDto data) {
		Purchase purchase = createPurchase(data.getPurchaseID(), convertToModel(data.getBuyer()));
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
		return toList(buyer.getPurchases());
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
	
	///////////////////////
	////Action Methods/////
	///////////////////////
	
	@Transactional
	public void clearDatabase() {
		galleryRepository.deleteAll();
		artistRepository.deleteAll();
		buyerRepository.deleteAll();
		administratorRepository.deleteAll();
		purchaseRepository.deleteAll();
		postingRepository.deleteAll();
	}	
	
	////////////////
	//Cart Methods//
	///////////////
	
	@Transactional
	public Purchase addToCart(Buyer buyer, Posting posting) {
		
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
	public Purchase addToCart(BuyerDto data, int postingID) {
		Posting posting = postingRepository.findPostingByPostingID(postingID);
		Purchase purchase = addToCart(convertToModel(data), posting);
		return purchase;
	}
	
	@Transactional
	public Purchase removeFromCart(Buyer buyer, Posting posting) {
		
		// Input validation
	    String error = "";
	    if (buyer == null) {
	        error = error + "removeFromCart buyer cannot be empty. ";
	    }
	    error = error.trim();
	    if (error.length() > 0) {
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
	public Purchase removeFromCart(BuyerDto data, int postingID) {
		Posting posting = postingRepository.findPostingByPostingID(postingID);
		Purchase purchase = removeFromCart(convertToModel(data), posting);
		return purchase;
	}
	
	////////////////////
	//Purchase Methods//
	////////////////////
	
	@Transactional
	public Purchase makePurchase(PurchaseDto data, DeliveryType deliveryType) {
		Purchase purchase = purchaseRepository.findPurchaseByPurchaseID(data.getPurchaseID());
		
		if(purchase == null || purchase.getTotalPrice() <= 0) 
			throw new IllegalArgumentException("Must have a purchase order to make purchase");
		
		if(deliveryType != DeliveryType.PickUp && deliveryType != DeliveryType.Shipped)
			throw new IllegalArgumentException("Delivery Type not valid");
			
		Buyer buyer = purchase.getBuyer();
		
		for(Posting p: purchase.getPostings()) {
			p.setArtStatus(ArtStatus.Purchased);
		}
		
		purchase.setDeliveryType(deliveryType);
		purchase.setTotalPrice(calcFinalPrice(purchase));
		buyer.addPurchase(purchase);
		buyer.setCart(null);
		
		buyerRepository.save(buyer);
		purchaseRepository.save(purchase);
		
		return purchase;
	}
	

	@Transactional
	public boolean cancelPurchase(PurchaseDto data) {
		Purchase purchase = convertToModel(data);
		
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
	
	
	///////////////////////////////
	///Private helper methods//////
	///////////////////////////////
	
	private Purchase convertToModel(PurchaseDto data) {
		
		int purchaseID = data.getPurchaseID();
		Purchase purchase = purchaseRepository.findPurchaseByPurchaseID(purchaseID);
		
		if (purchase == null) {
			
			purchase = new Purchase();
			
			Buyer buyer = convertToModel(data.getBuyer());
			DeliveryType delivery = data.getDeliveryType();
			int totalPrice = data.getTotalPrice();
			LocalDateTime time = data.getTime();
			for(PostingDto p : data.getPostings()) {
				purchase.addPosting(convertToModel(p));
			}
			purchase.setBuyer(buyer);
			purchase.setDeliveryType(delivery);
			purchase.setPurchaseID(purchaseID);
			purchase.setTotalPrice(totalPrice);
			purchase.setTime(time);
		}
		
		return purchase;
	}
	
	private Administrator convertToModel(AdministratorDto data) {
		String email = data.getEmail();
		Administrator admin = administratorRepository.findAdministratorByEmail(email);
		
		if (admin == null) {
			String name = data.getName();
			String password = data.getPassword();
			Gallery gallery = convertToModel(data.getGallery());
			
			admin = new Administrator();
			
			admin.setEmail(email);
			admin.setGallery(gallery);
			admin.setName(name);
			admin.setPassword(password);
		}
		
		return admin;
	}
	
	private Buyer convertToModel(BuyerDto data) {
		
		String email = data.getEmail();
		Buyer buyer = buyerRepository.findBuyerByEmail(email);
		
		if (buyer == null) {
			String name = data.getName();
			String password = data.getPassword();
			Gallery gallery = convertToModel(data.getGallery());
			
			buyer = new Buyer();
			
			buyer.setEmail(email);
			buyer.setGallery(gallery);
			buyer.setName(name);
			buyer.setPassword(password);
		}
		
		return buyer;
	}
	private Posting convertToModel(PostingDto data) {
		
		int postingID = data.getPostingID();
		Posting posting = postingRepository.findPostingByPostingID(postingID);
		
		if (posting == null) {
			
			posting = new Posting();
			
			Artist artist = convertToModel(data.getArtist());
			int price = data.getPrice();
			String title = data.getTitle();
			String description = data.getDescription();
			double xDim = data.getXDim();
			double yDim = data.getYDim();
			double zDim = data.getZDim();
			Date date = data.getDate();
			Gallery gallery = convertToModel(data.getGallery());
			ArtStatus status = data.getArtStatus();
			posting = new Posting();
			posting.setArtist(artist);
			posting.setPrice(price);
			posting.setTitle(title);
			posting.setDescription(description);
			posting.setXDim(xDim);
			posting.setYDim(yDim);
			posting.setZDim(zDim);
			posting.setDate(date);
			posting.setGallery(gallery);
			posting.setArtStatus(status);
		}
		
		return posting;
	}
	

	private Artist convertToModel(ArtistDto data) {
		String email = data.getEmail();
		Artist artist = artistRepository.findArtistByEmail(email);
		
		if (artist == null) {
			String name = data.getName();
			String password = data.getPassword();
			Gallery gallery = convertToModel(data.getGallery());
			
			artist = new Artist();
			artist.setEmail(email);
			artist.setGallery(gallery);
			artist.setName(name);
			artist.setPassword(password);
		}
		
		return artist;
		
	}
	
	private Gallery convertToModel(GalleryDto data) {
        String name = data.getName();
        Gallery gallery = galleryRepository.findGalleryByName(name);
        if (gallery ==  null) {
            String city = data.getCity();
            double commission = data.getCommission();
            gallery = new Gallery();
            gallery.setName(name);
            gallery.setCity(city);
            gallery.setCommission(commission);
        }

        return gallery;
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
	
	private int generatePostingID() {
		Random r = new Random();
		int id = r.nextInt();
		while(postingRepository.findPostingByPostingID(id) != null) {
			id = r.nextInt();
		}
		return id;
	}

	private int calcFinalPrice(Purchase purchase) {
		Gallery gallery = purchase.getBuyer().getGallery();
		return (int)(purchase.getTotalPrice() * (1 + gallery.getCommission()));
	}
	
}
