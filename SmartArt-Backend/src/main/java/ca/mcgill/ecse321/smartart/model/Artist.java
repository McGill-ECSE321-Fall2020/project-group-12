package ca.mcgill.ecse321.smartart.model;

import javax.persistence.ManyToOne;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Artist extends User {
	
	@OneToMany(mappedBy = "artist")
	private Set<Posting> postings;

	@ManyToOne(optional = false)
	@Column(name = "galleryID")
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

	public Gallery getGallery() {
		return this.gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

}
