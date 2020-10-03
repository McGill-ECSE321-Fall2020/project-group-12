package ca.mcgill.ecse321.smartart.model;

import javax.persistence.OneToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import ca.mcgill.ecse321.smartart.model.Posting;
import java.util.Set;
import javax.persistence.OneToMany;
import ca.mcgill.ecse321.smartart.model.User;

@Entity
public class Gallery {
	
	@OneToMany(mappedBy = "gallery")
	private Set<Buyer> buyers;
	@OneToMany(mappedBy = "gallery")
	private Set<Administrator> administrators;
	@OneToMany(mappedBy = "gallery")
	private Set<Artist> artists;
	@OneToMany(mappedBy = "gallery")
	private Set<Posting> postings;
	
	@Id
	private String galleryID;
	@Column(name = "name")
	private String name;
	@Column(name = "city")
	private String city;
	@Column(name = "commision")
	private float commision;
	
	public Set<Buyer> getBuyers() {
		return this.buyers;
	}

	public void setBuyers(Set<Buyer> buyers) {
		this.buyers = buyers;
	}

	public Set<Administrator> getAdministrators() {
		return this.administrators;
	}

	public void setAdministrators(Set<Administrator> administrators) {
		this.administrators = administrators;
	}

	public Set<Artist> getArtists() {
		return this.artists;
	}

	public void setArtists(Set<Artist> artists) {
		this.artists = artists;
	}

	public Set<Posting> getPostings() {
		return this.postings;
	}

	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
	}

	public void setGalleryID(String value) {
		this.galleryID = value;
	}

	public String getGalleryID() {
		return this.galleryID;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setCity(String value) {
		this.city = value;
	}

	public String getCity() {
		return this.city;
	}

	public void setCommision(float value) {
		this.commision = value;
	}

	public float getCommision() {
		return this.commision;
	}
}
