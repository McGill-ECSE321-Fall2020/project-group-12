package ca.mcgill.ecse321.smartart.model;

import javax.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

@Entity
public class Purchase {
	
	@Id
	@Column(name = "purchaseID")
	private int purchaseID;
	
	@ManyToOne(optional = false)
	private Buyer buyer;
	
	@Column(name = "totalPrice")
	private double totalPrice;
	
	@OneToMany
	private Set<Posting> postings;

	
	public Set<Posting> getPostings() {
		return this.postings;
	}

	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
	}

	public void addPosting(Posting posting) {
		if(this.postings == null) this.postings = new HashSet<Posting>();
		this.postings.add(posting);
	}

	public boolean removePosting(Posting posting) {
		if (this.postings.remove(posting))return true;
		return false;
	}

	public boolean hasPostings() {
		if (this.postings == null)
			return false;
		return true;
	}

	public Buyer getBuyer() {
		return this.buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public void setTotalPrice(double value) {
		this.totalPrice = value;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public void setPurchaseID(int value) {
		this.purchaseID = value;
	}

	public int getPurchaseID() {
		return this.purchaseID;
	}
}
