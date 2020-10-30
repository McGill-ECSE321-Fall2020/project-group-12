package ca.mcgill.ecse321.smartart.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartart.dao.AdministratorRepository;
import ca.mcgill.ecse321.smartart.dao.ArtistRepository;
import ca.mcgill.ecse321.smartart.dao.GalleryRepository;
import ca.mcgill.ecse321.smartart.dao.PostingRepository;
import ca.mcgill.ecse321.smartart.dto.ArtistDto;
import ca.mcgill.ecse321.smartart.dto.GalleryDto;
import ca.mcgill.ecse321.smartart.dto.PostingDto;
import ca.mcgill.ecse321.smartart.model.Administrator;
import ca.mcgill.ecse321.smartart.model.ArtStatus;
import ca.mcgill.ecse321.smartart.model.Artist;
import ca.mcgill.ecse321.smartart.model.Gallery;
import ca.mcgill.ecse321.smartart.model.Posting;

@Service
public class PostingService {
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private GalleryRepository galleryRepository;
	@Autowired
	private PostingRepository postingRepository;
	@Autowired
	private AdministratorRepository administratorRepository;
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
		posting.setArtStatus(ArtStatus.Available);
		posting.setPrice(price);
		posting.setXDim(x);
		posting.setYDim(y);
		posting.setZDim(z);
		posting.setTitle(title);
		posting.setDescription(description);
		posting.setDate(date);
		posting.setImage(image);
		posting.setPostingID(postingID);
		
		artist.addPosting(posting);
		artist.getGallery().addPosting(posting);
		
		postingRepository.save(posting);
		artistRepository.save(artist);
		galleryRepository.save(artist.getGallery());
		return posting;
	}
	
	@Transactional
	public Posting createPosting(PostingDto data) {
		Posting posting = createPosting(generatePostingID(data.getTitle(), data.getDescription()), helper.convertToModel(data.getArtist()), data.getPrice(), data.getXDim(), data.getYDim(), data.getZDim(), data.getTitle(), data.getDescription(), data.getDate(), data.getImage());
		return posting;
	}
	
	@Transactional
	public Posting adminCreatePosting(String administratorEmail, String artistName, PostingDto posting) {
		String artistEmail = "admin_";
		String name = artistName;
		artistName.replaceAll("\\s+","");
		artistEmail = artistEmail + artistName + "@mail.com";
		Administrator administrator = administratorRepository.findAdministratorByEmail(administratorEmail);
		if (administrator == null) {
	        throw new IllegalArgumentException("Invalid administrator credentials.");
	    }
		Artist artist = artistRepository.findArtistByEmail(artistEmail);
		if(artist == null) {
			artist = new Artist();
			artist.setEmail(artistEmail);
			artist.setName(name);
			artist.setPassword(administrator.getPassword());
			artist.setGallery(administrator.getGallery());
			artistRepository.save(artist);
			galleryRepository.save(artist.getGallery());
		}
		posting.setArtist(convertToDto(artist));
		return createPosting(posting);
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
	
	private int generatePostingID(String title, String description) {
		int postingID = title.hashCode() + description.hashCode();
		Random r = new Random();
		while(postingRepository.findPostingByPostingID(postingID) != null) {
			postingID += r.nextInt();
		}
		return postingID;
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	private ArtistDto convertToDto(Artist a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Artist.");
		}
		ArtistDto artistDto = new ArtistDto(a.getEmail(), a.getName(), a.getPassword(), convertToDto(a.getGallery()));
		return artistDto;
	}
	
	private GalleryDto convertToDto(Gallery g) {
		if (g == null) {
			throw new IllegalArgumentException("There is no such Gallery.");
		}
		GalleryDto galleryDto = new GalleryDto(g.getName(), g.getCity(), g.getCommission());
		return galleryDto;
	}
}
