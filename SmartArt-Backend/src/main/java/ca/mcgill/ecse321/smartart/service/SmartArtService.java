package ca.mcgill.ecse321.smartart.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
		administrator.setGallery(gallery);
		administratorRepository.save(administrator);
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
		artist.setGallery(gallery);
		artistRepository.save(artist);
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
		buyer.setGallery(gallery);
		buyerRepository.save(buyer);
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
		posting.setArtist(artist);
		posting.setGallery(artist.getGallery());
		posting.setPrice(price);
		posting.setArtStatus(ArtStatus.Available);
		posting.setXDim(x);
		posting.setYDim(y);
		posting.setZDim(z);
		posting.setDate(date);
		posting.setDescription(description);
		postingRepository.save(posting);
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

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
