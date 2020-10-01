package ca.mcgill.ecse321.smartart.model;
import javax.persistence.OneToMany;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

@Entity
publicclass Purchase {
public Purchase (float totalPrice, int purchaseID) {
   // TODO implement this operation
   throw new UnsupportedOperationException("not implemented");
}

private Set<Posting> postings;

	@OneToMany(mappedBy="purchase")
public Set<Posting> getPostings() {
   return this.postings;
}

public void setPostings(Set<Posting> postingss) {
   this.postings = postingss;
}


	private Buyer buyer;

	@ManyToOne(optional = false)
	public Buyer getBuyer() {
		return this.buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	private float totalPrice;
publicvoid setTotalPrice(float value) {
		this.totalPrice = value;
	}
publicfloat getTotalPrice() {
		return this.totalPrice;
	}

	private int purchaseID;
publicvoid setPurchaseID(int value) {
		this.purchaseID = value;
	}
publicint getPurchaseID() {
		return this.purchaseID;
	}
}
