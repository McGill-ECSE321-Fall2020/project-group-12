package ca.mcgill.ecse321.smartart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartart.dao.ArtistRepository;
import ca.mcgill.ecse321.smartart.dao.GalleryRepository;
import ca.mcgill.ecse321.smartart.dto.ArtistDto;
import ca.mcgill.ecse321.smartart.model.Artist;
import ca.mcgill.ecse321.smartart.model.Gallery;

@Service
public class ArtistService {
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private GalleryRepository galleryRepository;
	@Autowired
	private ServiceHelper helper;
	
	@Transactional
	public Artist createArtist(String email, String name, String password, Gallery gallery) {
		// Input validation
	    String error = "";
	    if (email == null || email.trim().length() == 0) {
	        error = error + "Artist email cannot be empty. ";
	    }
	    if (name == null || name.trim().length() == 0) {
	        error = error + "Artist name cannot be empty. ";
	    }
	    if (password == null || password.trim().length() == 0) {
	        error = error + "Artist password cannot be empty. ";
	    }
	    if (gallery == null) {
	        error = error + "Artist gallery cannot be empty. ";
	    }
	    if (artistRepository.findArtistByEmail(email) != null) {
	    	error = error + "An Artist with this email already exists";
	    }
	    
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
		
	    
		Artist artist = new Artist();
		artist.setEmail(email);
		artist.setName(name);
		artist.setPassword(password);
		gallery.addArtist(artist);
		artistRepository.save(artist);
		galleryRepository.save(gallery);
		return artist;
	}
	
	@Transactional
	public Artist createArtist(ArtistDto data) {
		Artist artist = createArtist(data.getEmail(), data.getName(), data.getPassword(), helper.convertToModel(data.getGallery()));
		return artist;
	}
	
	@Transactional
	public Artist getArtist(String email) {
		if (email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Artist email cannot be empty.");
		}
		
		Artist artist = artistRepository.findArtistByEmail(email);
		return artist;
	}
	
	@Transactional
	public List<Artist> getAllArtists(){
		return toList(artistRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
