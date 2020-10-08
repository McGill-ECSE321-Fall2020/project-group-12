package ca.mcgill.ecse321.smartart.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartart.model.Posting;

/**
 * 
 * @author Group 12
 * Interface used as a template for a repository of postings.
 */
public interface PostingRepository extends CrudRepository<Posting, Integer>{

	Posting findPostingByPostingID(int postingID);

}
