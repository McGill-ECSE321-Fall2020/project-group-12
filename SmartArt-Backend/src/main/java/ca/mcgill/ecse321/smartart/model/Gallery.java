package ca.mcgill.ecse321.smartart.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import ca.mcgill.ecse321.smartart.model.Posting;
import java.util.Set;
import javax.persistence.OneToMany;
import ca.mcgill.ecse321.smartart.model.User;

@Entity
public class Gallery {
	private Set<User> users;
	private Set<Posting> postings;

	private String name;
	private String city;
	private float commision;

	@OneToMany(mappedBy = "gallery")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void setPostings(Set<Posting> value) {
		this.postings = value;
	}

	public Set<Posting> getPostings() {
		return this.postings;
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
