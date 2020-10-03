package ca.mcgill.ecse321.smartart.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Artist extends User {

	private Set<Posting> postings;

	@OneToMany(mappedBy = "artist")
	public Set<Posting> getPostings() {
		return this.postings;
	}

	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
	}

}
