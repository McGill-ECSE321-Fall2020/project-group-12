package ca.mcgill.ecse321.smartart.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartart.model.Gallery;

public interface GalleryRepository extends CrudRepository<Gallery, String>{

	Gallery findGalleryByName(String name);

}
