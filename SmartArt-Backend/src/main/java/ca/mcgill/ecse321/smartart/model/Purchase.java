package ca.mcgill.ecse321.smartart.model;

import javax.persistence.OneToMany;
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
	private float totalPrice;
	
	@OneToMany
	private Set<Posting> postings;

	
	public Set<Posting> getPostings() {
		return this.postings;
	}

	public void setPostings(Set<Posting> postingss) {
		this.postings = postingss;
	}

	public boolean addPosting(Posting aPosting) {
		if (this.postings.add(aPosting))
			return true;

		return false;
	}

	public boolean removePosting(Posting aPosting) {
		if (this.postings.remove(aPosting))
			return true;
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
