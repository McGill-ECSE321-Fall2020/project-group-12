package ca.mcgill.ecse321.smartart.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import ca.mcgill.ecse321.smartart.dao.*;
import ca.mcgill.ecse321.smartart.model.*;
import ca.mcgill.ecse321.smartart.service.AdministratorService;
import ca.mcgill.ecse321.smartart.service.ArtistService;
import ca.mcgill.ecse321.smartart.service.BuyerService;
import ca.mcgill.ecse321.smartart.service.GalleryService;
import ca.mcgill.ecse321.smartart.service.PostingService;
import ca.mcgill.ecse321.smartart.service.PurchaseService;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestGetEndPoints {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
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
	private AdministratorService administratorService;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private GalleryService galleryService;
	@Autowired
	private PostingService postingService;
	@Autowired
	private PurchaseService purchaseService;
	
	@AfterEach
	public void clearDatabase() {
		galleryRepository.deleteAll();
		administratorRepository.deleteAll();
		artistRepository.deleteAll();
		buyerRepository.deleteAll();
		postingRepository.deleteAll();
		purchaseRepository.deleteAll();
	}
	
	@BeforeEach
	public void setUpTests() {
		Gallery gallery = galleryService.createGallery("Gallery", "Montreal", 0.05);
		Artist artist = artistService.createArtist("ben@mail.com", "Ben", "abc123", gallery);
		administratorService.createAdministrator("greg@mail.com", "Greg", "abc123", gallery);
		Buyer buyer = buyerService.createBuyer("aidan@mail.com", "Aidan", "abc123", gallery);
		postingService.createPosting(124344, artist, 123, 1, 1, 1, "Art", "This is Art", new Date(0), "fakeimage");
		Purchase purchase = purchaseService.createPurchase(21122, buyer);
		buyer.setCart(purchase);
		buyerRepository.save(buyer);
	}
	
	@Test
	public void getGalleries() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/galleries", HttpMethod.GET, null, String.class);
		// Check Status
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    String result = response.getBody().toString();
	    assertTrue(result.contains("[{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}]"));
	}
	
	@Test
	public void getGalleryByID() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/galleries/Gallery", HttpMethod.GET, null, String.class);
		// Check Status
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    String result = response.getBody().toString();
	    assertTrue(result.contains("{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}"));
	}
	
	@Test
	public void getArtists() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/artists", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("[{\"email\":\"ben@mail.com\",\"name\":\"Ben\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}}]"));
	}
	
	@Test
	public void getArtistByEmail() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/artists/ben@mail.com", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("{\"email\":\"ben@mail.com\",\"name\":\"Ben\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}}"));
	}
	
	@Test
	public void getAdministrators() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/administrators", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("[{\"email\":\"greg@mail.com\",\"name\":\"Greg\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}}]"));
	}
	
	@Test
	public void getAdministratorByEmail() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/administrators/greg@mail.com", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("{\"email\":\"greg@mail.com\",\"name\":\"Greg\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}}"));
	}
	@Test
	public void getBuyers() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/buyers", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("[{\"email\":\"aidan@mail.com\",\"name\":\"Aidan\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}}]"));
	}
	
	@Test
	public void getBuyerByEmail() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/buyers/aidan@mail.com", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("{\"email\":\"aidan@mail.com\",\"name\":\"Aidan\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}}"));
	}
	
	@Test
	public void getPostings() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/postings", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("[{\"postingID\":124344,\"artist\":{\"email\":\"ben@mail.com\",\"name\":\"Ben\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}},\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05},\"price\":123,\"title\":\"Art\",\"description\":\"This is Art\",\"artStatus\":\"Available\",\"date\":\"1969-12-31\",\"xdim\":1.0,\"ydim\":1.0,\"zdim\":1.0}]"));
	}
	
	@Test
	public void getPostingByID() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/postings/124344", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("{\"postingID\":124344,\"artist\":{\"email\":\"ben@mail.com\",\"name\":\"Ben\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}},\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05},\"price\":123,\"title\":\"Art\",\"description\":\"This is Art\",\"artStatus\":\"Available\",\"date\":\"1969-12-31\",\"xdim\":1.0,\"ydim\":1.0,\"zdim\":1.0}"));
	}
	
	@Test
	public void getPostingsByArtist() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/postings/artist/ben@mail.com", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("[{\"postingID\":124344,\"artist\":{\"email\":\"ben@mail.com\",\"name\":\"Ben\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}},\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05},\"price\":123,\"title\":\"Art\",\"description\":\"This is Art\",\"artStatus\":\"Available\",\"date\":\"1969-12-31\",\"xdim\":1.0,\"ydim\":1.0,\"zdim\":1.0}]"));
	}
	
	@Test
	public void getPurchases() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/purchases", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("[{\"purchaseID\":21122,\"buyer\":{\"email\":\"aidan@mail.com\",\"name\":\"Aidan\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}},\"totalPrice\":0,\"postings\":[],\"deliveryType\":null,\"time\":null}]"));
	}
	
	@Test
	public void getPurchaseByID() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/purchases/21122", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("{\"purchaseID\":21122,\"buyer\":{\"email\":\"aidan@mail.com\",\"name\":\"Aidan\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}},\"totalPrice\":0,\"postings\":[],\"deliveryType\":null,\"time\":null}"));
	}
	
	@Test
	public void getPurchasesByBuyer() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/purchases/buyer/aidan@mail.com", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("[{\"purchaseID\":21122,\"buyer\":{\"email\":\"aidan@mail.com\",\"name\":\"Aidan\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}},\"totalPrice\":0,\"postings\":[],\"deliveryType\":null,\"time\":null}]"));
	}
	
	@Test
	public void getCart() {
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/purchases/cart/aidan@mail.com", HttpMethod.GET, null, String.class);
		// Check Status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String result = response.getBody().toString();
		assertTrue(result.contains("{\"purchaseID\":21122,\"buyer\":{\"email\":\"aidan@mail.com\",\"name\":\"Aidan\",\"password\":\"abc123\",\"gallery\":{\"name\":\"Gallery\",\"city\":\"Montreal\",\"commission\":0.05}},\"totalPrice\":0,\"postings\":[],\"deliveryType\":null,\"time\":null}"));
	}
	
}