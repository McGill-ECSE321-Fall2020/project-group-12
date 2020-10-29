package ca.mcgill.ecse321.smartart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartart.dao.GalleryRepository;
import ca.mcgill.ecse321.smartart.dto.GalleryDto;
import ca.mcgill.ecse321.smartart.model.Gallery;

public class GalleryService {
	@Autowired
	private GalleryRepository galleryRepository;
	@Autowired
	private ServiceHelper helper;
	
	@Transactional
	public Gallery createGallery(String name, String city, double commission) {
		// Input validation
	    String error = "";
	    if (name == null || name.trim().length() == 0) {
	        error = error + "Gallery name cannot be empty. ";
	    }
	    if (city == null || city.trim().length() == 0) {
	        error = error + "Gallery city cannot be empty. ";
	    }
	    if (commission < 0) {
	        error = error + "Gallery commission cannot be less than 0. ";
	    }
	    
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
	    
		Gallery gallery = new Gallery();
		gallery.setName(name);
		gallery.setCity(city);
		gallery.setCommission(commission);
		galleryRepository.save(gallery);
		return gallery;
	}
	
	@Transactional
	public Gallery createGallery(GalleryDto data) {
		Gallery gallery = createGallery(data.getName(), data.getCity(), data.getCommission());
		return gallery;
	}
	
	@Transactional
	public Gallery getGallery(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Gallery name cannot be empty.");
		}
		Gallery gallery = galleryRepository.findGalleryByName(name);
		return gallery;
	}
	
	@Transactional
	public List<Gallery> getAllGalleries(){
		return toList(galleryRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
