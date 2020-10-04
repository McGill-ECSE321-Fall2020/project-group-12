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
	
	@OneToMany
	private Set<Buyer> buyers;
	@OneToMany
	private Set<Administrator> administrators;
	@OneToMany
	private Set<Artist> artists;
	@OneToMany
	private Set<Posting> postings;
	
	@Id
	private int galleryID;
	@Column(name = "name")
	private String name;
	@Column(name = "city")
	private String city;
	@Column(name = "commission")
	private double commission;
	
	public Set<Buyer> getBuyers() {
		return this.buyers;
	}

	public void setBuyers(Set<Buyer> buyers) {
		this.buyers = buyers;
	}
	
	public void addBuyer(Buyer buyer) {
		this.buyers.add(buyer);
	}

	public Set<Administrator> getAdministrators() {
		return this.administrators;
	}

	public void setAdministrators(Set<Administrator> administrators) {
		this.administrators = administrators;
	}
	
	public void addAdministrator(Administrator administrator) {
		this.administrators.add(administrator);
	}

	public Set<Artist> getArtists() {
		return this.artists;
	}

	public void setArtists(Set<Artist> artists) {
		this.artists = artists;
	}
	
	public void addArtist(Artist artist) {
		this.artists.add(artist);
	}

	public Set<Posting> getPostings() {
		return this.postings;
	}

	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
	}

	public void setGalleryID(int value) {
		this.galleryID = value;
	}

	public int getGalleryID() {
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

	public void setCommission(double value) {
		this.commission = value;
	}

	public double getCommission() {
		return this.commission;
	}
}
