package ca.mcgill.ecse321.smartart.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartart.model.Gallery;

public interface GalleryRepository extends CrudRepository<Gallery, Integer>{

	Gallery findGalleryByGalleryID(int galleryID);

}
