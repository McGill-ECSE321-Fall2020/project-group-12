package ca.mcgill.ecse321.smartart.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import org.junit.jupiter.api.AfterEach;
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
	@Autowired
	private SmartArtRepository smartartRepository;

	@AfterEach
	public void clearDatabase() {
		artistRepository.deleteAll();
		administratorRepository.deleteAll();
		buyerRepository.deleteAll();
		galleryRepository.deleteAll();
		postingRepository.deleteAll();
		purchaseRepository.deleteAll();
		smartartRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadGallery() {
		String name = "gal";
		String city = "mtl";
		double commission = 0.05;
		
		Gallery g = smartartRepository.createGallery(name, city, commission);
		g = null;
		g = smartartRepository.getGallery(name);
		assertNotNull(g);
		assertEquals(name, g.getName());
	}
	

	
	@Test
	public void testPersistAndLoadArtist() {
		String email = "mike@mail.com";
		String name = "mike";
		String galName = "gal";
		
		Artist artist = new Artist();
		Gallery gallery = new Gallery();

		artist.setEmail(email);
		artist.setName(name);
		gallery.setName(galName);
		
		artist.setGallery(gallery);
		
		Set<Artist> artists = new HashSet<Artist>();
		artists.add(artist);
		gallery.setArtists(artists);
		
		artistRepository.save(artist);
		galleryRepository.save(gallery);

		artist = null;
		gallery = null;

		artist = artistRepository.findArtistByEmail(email);
		gallery = galleryRepository.findGalleryByName(galName);
		assertNotNull(artist);
		assertNotNull(gallery);
		assertEquals(email, artist.getEmail());
	}
	
	@Test
	public void testPersistAndLoadBuyer() {
		String email = "todd@mail.com";
		String name = "todd";
		
		Buyer buyer = new Buyer();
		
		buyer.setEmail(email);
		buyer.setName(name);
		buyerRepository.save(buyer);

		buyer = null;

		buyer = buyerRepository.findBuyerByEmail(email);
		assertNotNull(buyer);
		assertEquals(email, buyer.getEmail());
	}

	@Test
	public void testPersistAndLoadAdministrator() {
		String email = "test@mail.com";
		// First example for object save/load
		Administrator administrator = new Administrator();
		// First example for attribute save/load
		administrator.setEmail(email);
		administratorRepository.save(administrator);

		administrator = null;

		administrator = administratorRepository.findAdministratorByEmail(email);
		assertNotNull(administrator);
		assertEquals(email, administrator.getEmail());
	}

}
