package ca.mcgill.ecse321.smartart.model;


import javax.persistence.OneToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import ca.mcgill.ecse321.smartart.model.Posting;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.OneToMany;
import ca.mcgill.ecse321.smartart.model.User;

@Entity
public class Gallery {
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "gallery", cascade = CascadeType.ALL)
	private Set<Buyer> buyers;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "gallery", cascade = CascadeType.ALL)
	private Set<Administrator> administrators;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "gallery", cascade = CascadeType.ALL)
	private Set<Artist> artists;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "gallery", cascade = CascadeType.ALL)
	private Set<Posting> postings;
	
	@Id
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
		if(this.buyers == null) this.buyers = new HashSet<Buyer>();
		this.buyers.add(buyer);
		buyer.setGallery(this);
	}

	public Set<Administrator> getAdministrators() {
		return this.administrators;
	}

	public void setAdministrators(Set<Administrator> administrators) {
		this.administrators = administrators;
	}
	
	public void addAdministrator(Administrator administrator) {
		if(this.administrators == null) this.administrators = new HashSet<Administrator>();
		this.administrators.add(administrator);
		administrator.setGallery(this);
	}

	public Set<Artist> getArtists() {
		return this.artists;
	}

	public void setArtists(Set<Artist> artists) {
		this.artists = artists;
	}
	
	public void addArtist(Artist artist) {
		if(this.artists == null) this.artists = new HashSet<Artist>();
		this.artists.add(artist);
		artist.setGallery(this);
	}

	public Set<Posting> getPostings() {
		return this.postings;
	}

	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
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

	public void addPosting(Posting posting) {
		if(this.postings == null) this.postings = new HashSet<Posting>();
		this.postings.add(posting);
		posting.setGallery(this);
	}
}
