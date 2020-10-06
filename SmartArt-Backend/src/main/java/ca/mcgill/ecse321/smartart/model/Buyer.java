package ca.mcgill.ecse321.smartart.model;


import javax.persistence.Id;

import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Buyer extends User {
	
	@ManyToOne(optional = false)
	private Gallery gallery;
	@OneToOne
	private Purchase cart;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "buyer", cascade = CascadeType.ALL)
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
		this.addPurchase(cart);
		cart.setBuyer(this);
	}

	public void addToCart(Posting posting) {
		this.cart.addPosting(posting);
		this.cart.setTotalPrice(this.cart.getTotalPrice() + posting.getPrice());
	}

	public boolean removeFromCart(Posting posting) {
		if (this.cart.removePosting(posting))
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
	
	public void addPurchase(Purchase purchase) {
		if(this.purchases == null) this.purchases = new HashSet<Purchase>();
		this.purchases.add(purchase);
		purchase.setBuyer(this);
	}

}
