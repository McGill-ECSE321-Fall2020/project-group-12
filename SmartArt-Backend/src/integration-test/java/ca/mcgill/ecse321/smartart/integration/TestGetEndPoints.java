package ca.mcgill.ecse321.smartart.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import ca.mcgill.ecse321.smartart.dao.*;
import ca.mcgill.ecse321.smartart.model.*;
import ca.mcgill.ecse321.smartart.service.SmartArtService;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestGetEndPoints {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	private HttpHeaders headers = new HttpHeaders();
	
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
	private SmartArtService service;
	
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
		Gallery gallery = service.createGallery("Gallery", "Montreal", 0.05);
		Artist artist = service.createArtist("ben@mail.com", "Ben", "abc123", gallery);
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
	}
	
}