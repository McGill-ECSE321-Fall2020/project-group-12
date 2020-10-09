package ca.mcgill.ecse321.smartart.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartart.model.Gallery;

/**
 * 
 * @author Group 12
 * Interface used as a template for a repository of galleries.
 */
public interface GalleryRepository extends CrudRepository<Gallery, String>{

	Gallery findGalleryByName(String name);

}
