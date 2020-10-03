package ca.mcgill.ecse321.smartart.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Administrator extends User {

	private Gallery gallery;

	@ManyToOne
	public Gallery getgallery() {
		return this.gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

}
