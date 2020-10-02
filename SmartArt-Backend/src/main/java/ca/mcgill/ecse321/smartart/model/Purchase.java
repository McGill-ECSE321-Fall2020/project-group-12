package ca.mcgill.ecse321.smartart.model;

import javax.persistence.OneToMany;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

@Entity
public class Purchase {
	private Set<Posting> postings;
	private int purchaseID;
	private Buyer buyer;
	private float totalPrice;

	public Purchase(float totalPrice, int purchaseID) {
		this.totalPrice = totalPrice;
		this.purchaseID = purchaseID;
	}
	
	@OneToMany(mappedBy = "gallery")
	public Set<Posting> getPostings() {
		return this.postings;
	}

	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
	}

	public boolean addPosting(Posting aPosting) {
		if(this.postings.add(aPosting))
			return true;
		
		return false;
	}
	
	public boolean removePosting(Posting aPosting) {
		if(this.postings.remove(aPosting))
			return true;
		return false;
	}
	
	public boolean hasPostings() {
		if(this.postings == null)
			return false;
		return true;
	}
	
	@ManyToOne(optional = false)
	public Buyer getBuyer() {
		return this.buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public void setTotalPrice(float value) {
		this.totalPrice = value;
	}

	public float getTotalPrice() {
		return this.totalPrice;
	}

	public void setPurchaseID(int value) {
		this.purchaseID = value;
	}

	public int getPurchaseID() {
		return this.purchaseID;
	}
}
