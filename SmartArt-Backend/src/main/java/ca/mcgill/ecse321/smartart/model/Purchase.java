package ca.mcgill.ecse321.smartart.model;

import javax.persistence.OneToMany;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

@Entity
public class Purchase {
	private Set<Posting> postings;

	@OneToMany(mappedBy = "purchase")
	public Set<Posting> getPostings() {
		return this.postings;
	}

	public void setPostings(Set<Posting> postingss) {
		this.postings = postingss;
	}

	private int purchaseID;
	private Buyer buyer;
	private float totalPrice;

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
