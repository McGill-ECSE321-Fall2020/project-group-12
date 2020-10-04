package ca.mcgill.ecse321.smartart.model;


import javax.persistence.Id;

import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Buyer extends User {
	
	@ManyToOne(optional = false)
	private Gallery gallery;
	@OneToOne
	private Purchase cart;
	@OneToMany(mappedBy = "buyer")
	private Set<Purchase> purchases;

	public Buyer() {
		super();
	}
	
	public Gallery getGallery() {
		return this.gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

	public Purchase getCart() {
		return this.cart;
	}

	public void setCart(Purchase cart) {
		this.cart = cart;
	}

	public boolean addToCart(Posting p) {
		if (this.cart.addPosting(p))
			return true;
		return false;
	}

	public boolean removeFromCart(Posting p) {
		if (this.cart.removePosting(p))
			return true;
		return false;
	}

	public boolean makePurchase(Purchase cart) {
		if (cart != null) {
			this.purchases.add(cart);
			this.setCart(null);
			return true;
		}
		return false;
	}

	public Set<Purchase> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}

}
