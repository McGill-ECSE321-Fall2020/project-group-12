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
		Administrator administrator = administratorRepository.findAdministratorByEmail(email);
		return administrator;
	}
	
	@Transactional
	public List<Administrator> getAllAdministrators(){
		return toList(administratorRepository.findAll());
	}
	
	@Transactional
	public Artist createArtist(String email, String name, String password, Gallery gallery) {
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
		Artist artist = artistRepository.findArtistByEmail(email);
		return artist;
	}
	
	@Transactional
	public List<Artist> getAllArtists(){
		return toList(artistRepository.findAll());
	}
	
	@Transactional
	public Buyer createBuyer(String email, String name, String password, Gallery gallery) {
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
		Buyer buyer = buyerRepository.findBuyerByEmail(email);
		return buyer;
	}
	
	@Transactional
	public List<Buyer> getAllBuyers(){
		return toList(buyerRepository.findAll());
	}
	
	@Transactional
	public Gallery createGallery(String name, String city, double commission) {
		Gallery gallery = new Gallery();
		gallery.setName(name);
		gallery.setCity(city);
		gallery.setCommission(commission);
		galleryRepository.save(gallery);
		return gallery;
	}
	
	@Transactional
	public Gallery getGallery(String name) {
		Gallery gallery = galleryRepository.findGalleryByName(name);
		return gallery;
	}
	
	@Transactional
	public Posting createPosting(int postingID, Artist artist, double price, double x, double y, double z, Date date, String description) {
		Posting posting = new Posting();
		posting.setPostingID(postingID);
		artist.addPosting(posting);
		artist.getGallery().addPosting(posting);
		posting.setArtStatus(ArtStatus.Available);
		posting.setXDim(x);
		posting.setYDim(y);
		posting.setZDim(z);
		posting.setDate(date);
		posting.setDescription(description);
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
		Purchase purchase = new Purchase();
		purchase.setPurchaseID(purchaseID);
		buyer.addPurchase(purchase);
		purchaseRepository.save(purchase);
		buyerRepository.save(buyer);
		return purchase;
	}
	
	@Transactional
	public void addToCart(Buyer buyer, Posting posting) {
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
		//TODO
	}
	
	@Transactional
	public void removePosting(Posting posting) {
		//TODO
	}
	
	@Transactional
	public boolean makePurchase(Purchase purchase) {
		//TODO
		return false;
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

}
