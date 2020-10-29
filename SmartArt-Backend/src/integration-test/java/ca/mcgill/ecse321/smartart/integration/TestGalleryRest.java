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
import ca.mcgill.ecse321.smartart.dao.GalleryRepository;
import ca.mcgill.ecse321.smartart.dto.GalleryDto;
import ca.mcgill.ecse321.smartart.model.Gallery;
import ca.mcgill.ecse321.smartart.service.GalleryService;

@ActiveProfiles("test")
@SpringBootTest(classes = SmartArtApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestGalleryRest {
	
	@LocalServerPort
	private int port = 8080;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private HttpHeaders headers = new HttpHeaders();
	
	@Autowired
	private GalleryRepository galleryRepository;
	
	private GalleryService galleryService;
	
	@AfterEach
	public void clearDatabase() {
		galleryRepository.deleteAll();
	}
	
	@Test
	public void createGallery() {
		GalleryDto gallery = new GalleryDto();
		gallery.setName("TestGallery");
		gallery.setCity("Montreal");
		gallery.setCommission(0.01);
		
		HttpEntity<GalleryDto> entity = new HttpEntity<GalleryDto>(gallery, headers);
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/gallery/create", HttpMethod.POST, entity, String.class);
		// Check Status
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    String result = response.getBody().toString();
	    //check that gallery was returned
	    assertTrue(result.contains("\"name\":\"TestGallery\""));
	}
	
	@Test
	public void createDuplicateGallery() {
		
		Gallery gallery = new Gallery();
		gallery.setName("TestGallery");
		gallery.setCity("Montreal");
		gallery.setCommission(0.01);
		galleryRepository.save(gallery);
		
		GalleryDto duplicate = new GalleryDto();
		duplicate.setName("TestGallery");
		duplicate.setCity("Montreal");
		duplicate.setCommission(0.01);
		
		HttpEntity<GalleryDto> entity = new HttpEntity<GalleryDto>(duplicate, headers);
		//create response entity
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/gallery/create", HttpMethod.POST, entity, String.class);
		// Check Status
	    assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
	    String result = response.getBody().toString();
	    //check that gallery was returned
	    assertEquals(result, "");
	}

}
