package ca.mcgill.ecse321.smartart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartart.dto.ArtistDto;
import ca.mcgill.ecse321.smartart.model.Artist;
import ca.mcgill.ecse321.smartart.service.ArtistService;

@CrossOrigin(origins = "*")
@RestController
public class ArtistRestController {
	@Autowired
	private ArtistService artistService;
	@Autowired
	private RestControllerHelper controllerHelper;
	
	@GetMapping(value = {"/artists", "/artists/"})
	public ResponseEntity<?> getAllArtists(){
		return new ResponseEntity<>(artistService.getAllArtists().stream().map(a -> controllerHelper.convertToDto(a)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping(value = { "/artists/{email}", "/artists/{email}/" })
	public ResponseEntity<?> getArtistByEmail(@PathVariable("email") String email) {
		try {
			Artist artist = artistService.getArtist(email);
			return new ResponseEntity<>(controllerHelper.convertToDto(artist), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = { "/artist/create", "/artist/create/" })
	public ResponseEntity<?> createArtist(@RequestBody ArtistDto data) {
		try {
			Artist artist = artistService.createArtist(data);
			return new ResponseEntity<>(controllerHelper.convertToDto(artist), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}
}
