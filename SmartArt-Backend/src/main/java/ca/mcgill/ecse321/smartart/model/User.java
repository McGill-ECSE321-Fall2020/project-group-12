package ca.mcgill.ecse321.smartart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import ca.mcgill.ecse321.smartart.model.Gallery;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User {
	@Id
	@Column(name = "email")
	private String email;
	@Column(name = "name")
	private String name;
	@Column(name = "password")
	private String password;
	@Column(name = "phone")
	private int phone;
	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
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
