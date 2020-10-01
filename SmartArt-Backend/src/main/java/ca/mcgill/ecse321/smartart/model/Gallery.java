package ca.mcgill.ecse321.smartart.model;

import javax.persistence.Entity;
import ca.mcgill.ecse321.smartart.model.Posting;
import java.util.Set;
import javax.persistence.OneToMany;
import ca.mcgill.ecse321.smartart.model.User;

@Entity
public class Gallery {
	private Set<User> users;

	@OneToMany(mappedBy = "gallery")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	private Set<Posting> postings;

	@OneToMany(mappedBy = "gallery")
	public Set<Posting> getPostings() {
		return this.postings;
	}

	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
	}

	private String name;

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	private String city;

	public void setCity(String value) {
		this.city = value;
	}

	public String getCity() {
		return this.city;
	}

	private float commision;

	public void setCommision(float value) {
		this.commision = value;
	}

	public float getCommision() {
		return this.commision;
	}
}
