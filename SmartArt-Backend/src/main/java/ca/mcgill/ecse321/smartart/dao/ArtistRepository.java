package ca.mcgill.ecse321.smartart.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartart.model.Artist;

public interface ArtistRepository extends CrudRepository<Artist, String>{

	Artist findArtistByEmail(String email);

}