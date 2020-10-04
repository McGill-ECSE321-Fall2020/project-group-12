package ca.mcgill.ecse321.smartart.model;


import javax.persistence.ManyToOne;

import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class Administrator extends User {
	
	@ManyToOne(optional = false)
	private Gallery gallery;

	public Administrator() {
		super();
	}
	
	public Gallery getGallery() {
		return this.gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}
}
