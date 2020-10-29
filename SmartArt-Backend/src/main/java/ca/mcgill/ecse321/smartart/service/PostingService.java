package ca.mcgill.ecse321.smartart.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartart.dao.ArtistRepository;
import ca.mcgill.ecse321.smartart.dao.GalleryRepository;
import ca.mcgill.ecse321.smartart.dao.PostingRepository;
import ca.mcgill.ecse321.smartart.dto.PostingDto;
import ca.mcgill.ecse321.smartart.model.ArtStatus;
import ca.mcgill.ecse321.smartart.model.Artist;
import ca.mcgill.ecse321.smartart.model.Gallery;
import ca.mcgill.ecse321.smartart.model.Posting;

public class PostingService {
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private GalleryRepository galleryRepository;
	@Autowired
	private PostingRepository postingRepository;
	@Autowired
	private ServiceHelper helper;
	
	@Transactional
	public Posting createPosting(int postingID, Artist artist, int price, double x, double y, double z, String title, String description, Date date, String image) {
		// Input validation
	    String error = "";
	    if (artist == null) {
	        error = error + "Posting artist cannot be empty. ";
	    }
	    if (title == null || title.trim().length() == 0) {
	        error = error + "Posting title cannot be empty. ";
	    }
	    if (description == null || description.trim().length() == 0) {
	        error = error + "Posting description cannot be empty. ";
	    }
	    if (price <= 0) {
	        error = error + "Posting price must be above 0. ";
	    }
	    if (x <= 0) {
	        error = error + "Posting xDim must be above 0. ";
	    }
	    if (y <= 0) {
	        error = error + "Posting yDim must be above 0. ";
	    }
	    if (z <= 0) {
	        error = error + "Posting zDim must be above 0. ";
	    }
	    
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
		
		Posting posting = new Posting();
		posting.setPostingID(postingID);
		posting.setArtStatus(ArtStatus.Available);
		posting.setPrice(price);
		posting.setXDim(x);
		posting.setYDim(y);
		posting.setZDim(z);
		posting.setTitle(title);
		posting.setDescription(description);
		posting.setDate(date);
		posting.setImage(image);
		
		artist.addPosting(posting);
		artist.getGallery().addPosting(posting);
		
		postingRepository.save(posting);
		artistRepository.save(artist);
		galleryRepository.save(artist.getGallery());
		return posting;
	}
	
	@Transactional
	public Posting createPosting(PostingDto data) {
		Posting posting = createPosting(data.getPostingID(), helper.convertToModel(data.getArtist()), data.getPrice(), data.getXDim(), data.getYDim(), data.getZDim(), data.getTitle(), data.getDescription(), data.getDate(), data.getImage());
		return posting;
	}
	
	@Transactional
	public Posting deletePosting(Posting posting) {
		if(posting == null) throw new NullPointerException("Cannot remove null posting, perhaps it has already been deleted");
		
		if(posting.getArtStatus() == ArtStatus.Purchased) throw new IllegalArgumentException("Cannot delete a posting that has been purchased");
		
		Gallery gallery = posting.getGallery();
		Artist artist = posting.getArtist();
		artist.removePosting(posting);
		gallery.removePosting(posting);
		
		postingRepository.delete(posting);
		artistRepository.save(artist);
		galleryRepository.save(gallery);
		
		return posting;
	}
	
	@Transactional
	public Posting deletePosting(PostingDto data) throws Exception {
		Posting posting = postingRepository.findPostingByPostingID(data.getPostingID());
		
		return deletePosting(posting);
		
	}
	
	@Transactional
	public Posting getPosting(int postingID) {
		Posting posting = postingRepository.findPostingByPostingID(postingID);
		return posting;
	}
	
	@Transactional
	public List<Posting> getPostingsByArtist(String email) {
		Artist artist = artistRepository.findArtistByEmail(email);
		if(artist == null)
			return null;
		return helper.toList(artist.getPostings());
	}
	
	@Transactional
	public List<Posting> getAllPostings(){
		return toList(postingRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
