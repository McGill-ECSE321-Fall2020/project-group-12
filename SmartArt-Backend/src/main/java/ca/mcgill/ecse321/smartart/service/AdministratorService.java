package ca.mcgill.ecse321.smartart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartart.dao.AdministratorRepository;
import ca.mcgill.ecse321.smartart.dao.GalleryRepository;
import ca.mcgill.ecse321.smartart.dto.AdministratorDto;
import ca.mcgill.ecse321.smartart.model.Administrator;
import ca.mcgill.ecse321.smartart.model.Gallery;

@Service
public class AdministratorService {
	@Autowired
	private AdministratorRepository administratorRepository;
	@Autowired
	private GalleryRepository galleryRepository;
	@Autowired
	private ServiceHelper helper;
	
	@Transactional
	public Administrator createAdministrator(String email, String name, String password, Gallery gallery) {
		// Input validation
	    String error = "";
	    if (email == null || email.trim().length() == 0) {
	        error = error + "Administrator email cannot be empty. ";
	    }
	    if (name == null || name.trim().length() == 0) {
	        error = error + "Administrator name cannot be empty. ";
	    }
	    if (password == null || password.trim().length() == 0) {
	        error = error + "Administrator password cannot be empty. ";
	    }
	    if (gallery == null) {
	        error = error + "Administrator gallery cannot be empty. ";
	    }
	    if (administratorRepository.findAdministratorByEmail(email) != null) {
	    	error = error + "An Administrator with this email already exists";
	    }
	    
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
	    
	    
		Administrator administrator = new Administrator();
		administrator.setEmail(email);
		administrator.setName(name);
		administrator.setPassword(password);
		gallery.addAdministrator(administrator);
		administratorRepository.save(administrator);
		galleryRepository.save(gallery);
		return administrator;
	}
	
	@Transactional
	public Administrator createAdministrator(AdministratorDto data) {
		Administrator admin = createAdministrator(data.getEmail(), data.getName(), data.getPassword(), helper.convertToModel(data.getGallery()));
		return admin;
	}
	
	@Transactional
	public Administrator getAdministrator(String email) {
		if (email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Administrator email cannot be empty.");
		}
		
		Administrator administrator = administratorRepository.findAdministratorByEmail(email);
		if (administrator == null) {
			throw new IllegalArgumentException("Administrator does not exist.");
		}
		return administrator;
	}
	
	@Transactional
	public List<Administrator> getAllAdministrators(){
		return toList(administratorRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
