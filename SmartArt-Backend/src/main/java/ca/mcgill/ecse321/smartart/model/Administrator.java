package ca.mcgill.ecse321.smartart.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Administrator extends User {

	private Gallery gallery;

	public Administrator(String name, int phone, String email, String password, Gallery gallery) {
		super(name,phone, email,password);
		this.gallery = gallery;
	}

	@ManyToOne
	public Gallery getgallery() {
		return this.gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

}
