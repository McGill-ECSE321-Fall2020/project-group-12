package ca.mcgill.ecse321.smartart.model;


import javax.persistence.ManyToOne;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Artist extends User {
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "artist", cascade = CascadeType.ALL)
	private Set<Posting> postings;

	@ManyToOne(optional = false)
	private Gallery gallery;
	
	public Artist() {
		super();
	}
	
	public void setPostings(Set<Posting> value) {
		this.postings = value;
	}

	public Set<Posting> getPostings() {
		return this.postings;
	}
	
	public void addPosting(Posting posting) {
		if(this.postings == null) this.postings = new HashSet<Posting>();
		this.postings.add(posting);
		posting.setArtist(this);
	}

	public Gallery getGallery() {
		return this.gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

}
