package ca.mcgill.ecse321.smartart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartart.dto.GalleryDto;
import ca.mcgill.ecse321.smartart.model.Gallery;
import ca.mcgill.ecse321.smartart.service.GalleryService;

@CrossOrigin(origins = "*")
@RestController
public class GalleryRestController {
	@Autowired
	private GalleryService galleryService;
	@Autowired
	private RestControllerHelper controllerHelper;
	
	@GetMapping(value = {"/galleries", "/galleries/"})
	public List<GalleryDto> getAllGalleries(){
		return galleryService.getAllGalleries().stream().map(g -> controllerHelper.convertToDto(g)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/galleries/{name}", "/galleries/{name}/" })
	public GalleryDto getGalleryByName(@PathVariable("name") String name) throws IllegalArgumentException {
		return controllerHelper.convertToDto(galleryService.getGallery(name));
	}
	
	@PostMapping(value = { "/gallery/create", "/gallery/create/" })
	public GalleryDto createGallery(@RequestBody GalleryDto data) {
		Gallery gallery = galleryService.createGallery(data);
		return controllerHelper.convertToDto(gallery);
	}
}
