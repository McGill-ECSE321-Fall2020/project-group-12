package ca.mcgill.ecse321.smartart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartart.dto.PostingDto;
import ca.mcgill.ecse321.smartart.model.Posting;
import ca.mcgill.ecse321.smartart.service.PostingService;

@CrossOrigin(origins = "*")
@RestController
public class PostingRestController {
	@Autowired
	private PostingService postingService;
	@Autowired
	private RestControllerHelper controllerHelper;

	@GetMapping(value = {"/postings", "/postings/" })
	public ResponseEntity<?> getAllPostings(){
		List<PostingDto> postings = postingService.getAllPostings().stream().map(p -> controllerHelper.convertToDto(p)).collect(Collectors.toList());
		return new ResponseEntity<>(postings, HttpStatus.OK);
	}

	@GetMapping(value = { "/postings/{postingID}", "/postings/{postingID}/" })
	public ResponseEntity<?> getPostingByPostingID(@PathVariable("postingID") int postingID) {
		PostingDto posting = controllerHelper.convertToDto(postingService.getPosting(postingID));
		if(posting != null)
			return new ResponseEntity<>(posting, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = { "/postings/artist/{email}", "/postings/artist/{email}/" })
	public ResponseEntity<?> getPostingsByArtist(@PathVariable("email") String email) {
		List<PostingDto> postings = postingService.getPostingsByArtist(email).stream().map(p -> controllerHelper.convertToDto(p)).collect(Collectors.toList());
		if(postings != null)
			return new ResponseEntity<>(postings, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = {"/posting/create", "/posting/create/" })
	public ResponseEntity<?> createPosting(@RequestBody PostingDto data) {
		try {
			Posting posting = postingService.createPosting(data);
			PostingDto postingData = controllerHelper.convertToDto(posting);
			return new ResponseEntity<>(postingData, HttpStatus.CREATED);

		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = {"/posting/admin/create/{email}/{name}", "/posting/admin/create/{email}/{name}/" })
	public ResponseEntity<?> adminCreatePosting(@RequestBody PostingDto data, @PathVariable("email") String adminEmail, @PathVariable("name") String artistName) {
		try {
			Posting posting = postingService.adminCreatePosting(adminEmail, artistName, data);
			PostingDto postingData = controllerHelper.convertToDto(posting);
			return new ResponseEntity<>(postingData, HttpStatus.CREATED);

		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = {"/posting/delete", "/posting/delete/"})
	public ResponseEntity<?> deletePosting(@RequestBody PostingDto posting) {
		try {
			postingService.deletePosting(posting);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
