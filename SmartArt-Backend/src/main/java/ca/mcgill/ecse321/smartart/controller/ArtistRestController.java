package ca.mcgill.ecse321.smartart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ca.mcgill.ecse321.smartart.dto.ArtistDto;
import ca.mcgill.ecse321.smartart.model.Artist;
import ca.mcgill.ecse321.smartart.service.ArtistService;

public class ArtistRestController {
	@Autowired
	private ArtistService artistService;
	@Autowired
	private RestControllerHelper controllerHelper;
	
	@GetMapping(value = {"/artists", "/artists/"})
	public List<ArtistDto> getAllArtists(){
		return artistService.getAllArtists().stream().map(a -> controllerHelper.convertToDto(a)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/artists/{email}", "/artists/{email}/" })
	public ArtistDto getArtistByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return controllerHelper.convertToDto(artistService.getArtist(email));
	}
	
	@PostMapping(value = { "/artist/create", "/artist/create/" })
	public ArtistDto createArtist(@RequestBody ArtistDto data) throws IllegalArgumentException {
		Artist artist = artistService.createArtist(data);
		return controllerHelper.convertToDto(artist);
	}
}
