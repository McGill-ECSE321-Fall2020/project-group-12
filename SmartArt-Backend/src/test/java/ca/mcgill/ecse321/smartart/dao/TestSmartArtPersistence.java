package ca.mcgill.ecse321.smartart.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.smartart.model.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestSmartArtPersistence {

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

	@BeforeEach
	public void createGallery() {
		String galName = "Guginhiem";
		String city = "bilbao";
		double commission = 0.2;
		
		Gallery gallery = new Gallery();
		gallery.setName(galName);
		gallery.setCity(city);
		gallery.setCommission(commission);
		
		galleryRepository.save(gallery);
	}
	
	@AfterEach
	public void clearDatabase() {
		administratorRepository.deleteAll();
		artistRepository.deleteAll();
		buyerRepository.deleteAll();
		galleryRepository.deleteAll();
		postingRepository.deleteAll();
		purchaseRepository.deleteAll();
	}

	
	@Test
	public void testPersistAndLoadGallery() {
		String galName = "Guginhiem";
		String city = "bilbao";
		double commission = 0.2;
		String name = "ella";
		String email = "ella@mail.com";
		
		Artist artist = new Artist();
		artist.setName(name);
		artist.setEmail(email);
		
		Gallery gallery = galleryRepository.findGalleryByName(galName);
		
		gallery.addArtist(artist);
	
		galleryRepository.save(gallery);
		artistRepository.save(artist);
		
		gallery = null;
		
		gallery = galleryRepository.findGalleryByName(galName);
		
		assertNotNull(gallery);
		assertEquals(galName, gallery.getName());
		assertNotNull(gallery.getArtists());
		assertEquals(name, ((Artist) gallery.getArtists().toArray()[0]).getName());
	}
	
	@Test
	public void testPersistAndLoadArtist() {
		String email = "mike@mail.com";
		String name = "mike";
		int phone = 555758455;
		String pw = "abc123";
		String galName = "Guginhiem";
		
		Artist a = new Artist();
		a.setName(name);
		a.setEmail(email);
		a.setPassword(pw);
		a.setPhone(phone);
		
		Gallery g = galleryRepository.findGalleryByName(galName);
		g.addArtist(a);
	
		artistRepository.save(a);
		galleryRepository.save(g);
	
		a = null;

		a = artistRepository.findArtistByEmail(email);
		
		assertNotNull(a);
		assertEquals(email, a.getEmail());
		assertEquals(g.getName(), a.getGallery().getName());
	}
	
	@Test
	public void testPersistAndLoadBuyer() {
		String email = "bob@mail.com";
		String name = "bob";
		int phone = 555758455;
		String pw = "abc123";
		String galName = "Guginhiem";
		
		Buyer b = new Buyer();
		b.setName(name);
		b.setEmail(email);
		b.setPassword(pw);
		b.setPhone(phone);
		
		Gallery g = galleryRepository.findGalleryByName(galName);
		g.addBuyer(b);
		
	
		galleryRepository.save(g);
		buyerRepository.save(b);
	
		b = null;

		b = buyerRepository.findBuyerByEmail(email);
		
		assertNotNull(b);
		assertEquals(email, b.getEmail());
		assertEquals(g.getName(), b.getGallery().getName());
	}

	@Test
	public void testPersistAndLoadAdministrator() {
		String email = "pam@mail.com";
		String name = "pam";
		int phone = 555758455;
		String pw = "abc123";
		String galName = "Guginhiem";
		
		Administrator a = new Administrator();
		a.setName(name);
		a.setEmail(email);
		a.setPassword(pw);
		a.setPhone(phone);
		
		Gallery g = galleryRepository.findGalleryByName(galName);
		g.addAdministrator(a);
	
		galleryRepository.save(g);
		administratorRepository.save(a);
	
		a = null;

		a = administratorRepository.findAdministratorByEmail(email);
		
		assertNotNull(a);
		assertEquals(email, a.getEmail());
		assertEquals(g.getName(), a.getGallery().getName());
	}
	
	@Test
	public void testPersistAndLoadPosting() {
		int id = 7464;
		String title = "Nu couche";
		double price = 100000000;
		String email = "amy@mail.com";
		String name = "amy";
		String galName = "Guginhiem";
		ArtStatus artStatus = ArtStatus.Available;
		
		Gallery g = galleryRepository.findGalleryByName(galName);
		
		Artist a = new Artist();
		a.setName(name);
		a.setEmail(email);
		
		Posting p = new Posting();
		p.setPostingID(id);
		p.setTitle(title);
		p.setPrice(price);
		p.setArtStatus(artStatus);
		
		g.addArtist(a);
		
		a.addPosting(p);
		
		g.addPosting(p);
		
		artistRepository.save(a);
		postingRepository.save(p);
		galleryRepository.save(g);
		
		p = null;
		
		p = postingRepository.findPostingByPostingID(id);
		
		assertNotNull(p);
		assertEquals(name, p.getArtist().getName());
		assertEquals(price, p.getPrice());
		assertEquals(artStatus, p.getArtStatus());
	}
	
	@Test
	public void testPersistAndLoadPurchase() {
		int id = 9874;
		String email = "meg@mail.com";
		String name = "meg";
		String galName = "Guginhiem";
		DeliveryType deliveryType = DeliveryType.PickUp;
		
		Gallery g = galleryRepository.findGalleryByName(galName);
		
		Buyer b = new Buyer();
		b.setName(name);
		b.setEmail(email);
		b.setGallery(g);
		
		Purchase p = new Purchase();
		p.setPurchaseID(id);
		p.setTotalPrice(0);
		p.setDeliveryType(deliveryType);
		
		b.addPurchase(p);
		
		buyerRepository.save(b);
		purchaseRepository.save(p);
		
		p = null;
		
		p = purchaseRepository.findPurchaseByPurchaseID(id);
		
		assertNotNull(p);
		assertEquals(name, p.getBuyer().getName());
		assertEquals(id, p.getPurchaseID());
		assertEquals(deliveryType, p.getDeliveryType());
	}

}
