package ca.mcgill.ecse321.smartart.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
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

import ca.mcgill.ecse321.smartart.SmartArtApplication;
import ca.mcgill.ecse321.smartart.dao.BuyerRepository;
import ca.mcgill.ecse321.smartart.dao.GalleryRepository;
import ca.mcgill.ecse321.smartart.dao.PurchaseRepository;
import ca.mcgill.ecse321.smartart.dto.ArtistDto;
import ca.mcgill.ecse321.smartart.dto.BuyerDto;
import ca.mcgill.ecse321.smartart.dto.GalleryDto;
import ca.mcgill.ecse321.smartart.dto.PostingDto;
import ca.mcgill.ecse321.smartart.dto.PurchaseDto;
import ca.mcgill.ecse321.smartart.model.Gallery;
import ca.mcgill.ecse321.smartart.service.BuyerService;
import ca.mcgill.ecse321.smartart.service.GalleryService;

@ActiveProfiles("test")
@SpringBootTest(classes = SmartArtApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestPurchaseRest {
	
	@LocalServerPort
	private int port = 8080;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private HttpHeaders headers = new HttpHeaders();
	
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private GalleryRepository galleryRepository;
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private GalleryService galleryService;
	@Autowired
	private BuyerService buyerService;
	
	@AfterEach
	public void clearDatabase() {
		galleryRepository.deleteAll();
		buyerRepository.deleteAll();
		purchaseRepository.deleteAll();
	}
	
	
	@Test
	public void createPurchase() {
		//create gallery for buyer
		GalleryDto gallery = new GalleryDto("TestGallery");
		//persist gallery
		galleryService.createGallery(gallery);
		
		//create buyer for purchase
		BuyerDto buyer = new BuyerDto("test@mail.com", gallery);
		//persist buyer
		buyerService.createBuyer(buyer);
		
		//dto to be passed
		PurchaseDto purchase = new PurchaseDto(buyer);
		
	
		HttpEntity<PurchaseDto> entity = new HttpEntity<PurchaseDto>(purchase, headers);
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/purchase/create", HttpMethod.POST, entity, String.class);
		//check Status
	    assertEquals(HttpStatus.CREATED, response.getStatusCode());
	    String result = response.getBody().toString();
	    //check that proper purchase was returned
	    //assertTrue(result.contains("\"purchaseID\":123"));
	    //check association to buyer
	    assertTrue(result.contains("\"email\":\"test@mail.com\""));
	}
	
	@Test
	public void createPurchaseNoBuyer() {
		//dto to be passed
		PurchaseDto purchase = new PurchaseDto();
		purchase.setPurchaseID(123);
		
		HttpEntity<PurchaseDto> entity = new HttpEntity<PurchaseDto>(purchase, headers);
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/purchase/create", HttpMethod.POST, entity, String.class);
		//check Status
	    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	    String result = response.getBody().toString();
	 	//check error message
	    assertTrue(result.contains("Purchase buyer cannot be empty"));    
	}
	
	@Test
	public void createDuplicatePurchase() {
		//create gallery for buyer
		GalleryDto gallery = new GalleryDto("TestGallery");
		//persist gallery
		galleryService.createGallery(gallery);
		
		//create buyer for purchase
		BuyerDto buyer = new BuyerDto("test@mail.com", gallery);
		//persist buyer
		buyerService.createBuyer(buyer);
		
		//create original purchase
	}

}
