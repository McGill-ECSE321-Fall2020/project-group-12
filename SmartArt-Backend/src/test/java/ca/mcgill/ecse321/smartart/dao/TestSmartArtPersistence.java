package ca.mcgill.ecse321.smartart.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

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

	@BeforeEach
	public void createGallery() {
		String name = "gal";
		String city = "mtl";
		double commission = 0.05;
		Gallery g = smartartRepository.createGallery(name, city, commission);
	}
	
	@Test
	public void testPersistAndLoadGallery() {
		
		Gallery g = smartartRepository.getGallery("gal");
		assertNotNull(g);
		assertEquals("gal", g.getName());
	}
	
	@Test
	public void testPersistAndLoadArtist() {
		String email = "mike@mail.com";
		String name = "mike";
		String galName = "gal";
		
		Artist artist = new Artist();
		Gallery gallery = smartartRepository.getGallery(galName);

		artist.setEmail(email);
		artist.setName(name);
		
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
		String email = "bob@mail.com";
		String name = "bob";
		String galName = "gal";
		
		Buyer buyer = new Buyer();
		Gallery gallery = smartartRepository.getGallery(galName);

		buyer.setEmail(email);
		buyer.setName(name);
		
		buyer.setGallery(gallery);
		
		Set<Buyer> buyers = new HashSet<Buyer>();
		buyers.add(buyer);
		gallery.setBuyers(buyers);
		
		buyerRepository.save(buyer);
		galleryRepository.save(gallery);

		buyer = null;
		gallery = null;

		buyer = buyerRepository.findBuyerByEmail(email);
		gallery = galleryRepository.findGalleryByName(galName);
		assertNotNull(buyer);
		assertNotNull(gallery);
		assertEquals(email, buyer.getEmail());
	}

	@Test
	public void testPersistAndLoadAdministrator() {
		String email = "bob@mail.com";
		String name = "bob";
		String galName = "gal";
		
		Administrator administrator = new Administrator();
		Gallery gallery = smartartRepository.getGallery(galName);

		administrator.setEmail(email);
		administrator.setName(name);
		
		administrator.setGallery(gallery);
		
		Set<Administrator> administrators = new HashSet<Administrator>();
		administrators.add(administrator);
		gallery.setAdministrators(administrators);
		
		administratorRepository.save(administrator);
		galleryRepository.save(gallery);

		administrator = null;
		gallery = null;

		administrator = administratorRepository.findAdministratorByEmail(email);
		gallery = galleryRepository.findGalleryByName(galName);
		assertNotNull(administrator);
		assertNotNull(gallery);
		assertEquals(email, administrator.getEmail());
	}

}
