package ca.mcgill.ecse321.smartart.model;

import javax.persistence.Entity;
import ca.mcgill.ecse321.smartart.model.Gallery;
import javax.persistence.ManyToOne;

@Entity
public abstract class User {
	
	private String password;
	private Gallery gallery;
	private String name;
	private int phone;
	private String email;
	
	public User(String name, int phone, String email, String password) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
	}

	@ManyToOne(optional = false)
	public Gallery getGallery() {
		return this.gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setPhone(int value) {
		this.phone = value;
	}

	public int getPhone() {
		return this.phone;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public String getEmail() {
		return this.email;
	}
}
