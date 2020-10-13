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

/**
 * 
 * @author Group 12
 * The Buyer class represents the buyers of art pieces in our system.
 * They have a gallery in which they go to, a cart with the current
 * purchase and a set of all purchases.
 */
@Entity
public class Buyer extends User {
	
	@ManyToOne(optional = false)
	private Gallery gallery;
	@OneToOne
	private Purchase cart;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "buyer", cascade = CascadeType.ALL)
	private Set<Purchase> purchases;
	
	/**
	 * The default class.
	 */
	public Buyer() {
		super();
	}
	
	/**
	 * Gets the Gallery of this Buyer.
	 * @return the Gallery of this Buyer.
	 */
	public Gallery getGallery() {
		return this.gallery;
	}
	
	/**
	 * Sets the Gallery of this Buyer.
	 * @param gallery: the Gallery that will be set to this Buyer.
	 */
	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
		gallery.addBuyer(this);
	}
	
	/**
	 * Gets the cart of the Buyer
	 * @return the cart of the Buyer.
	 */
	public Purchase getCart() {
		return this.cart;
	}
	
	/**
	 * Sets the cart of the Buyer.
	 * @param cart: the cart that will be set to this Buyer.
	 */
	public void setCart(Purchase cart) {
		this.cart = cart;
		this.addPurchase(cart);
		cart.setBuyer(this);
	}
	
	/**
	 * Adds a Posting to the cart and updates the total price.
	 * @param posting: the Posting to be added.
	 */
	public void addToCart(Posting posting) {
		this.cart.addPosting(posting);
		this.cart.setTotalPrice(this.cart.getTotalPrice() + posting.getPrice());
	}

	/**
	 * Removes a Posting from the cart.
	 * @param posting: the Posting to be removed.
	 * @return true if successfully removed, false if not.
	 */
	public boolean removeFromCart(Posting posting) {
		if (this.cart.removePosting(posting))
			return true;
		return false;
	}
	
	/**
	 * Makes a Purchase and clear cart.
	 * @param cart: cart of the Buyer.
	 * @return true if successful, false if not.
	 */
	public boolean makePurchase(Purchase cart) {
		if (cart != null) {
			this.purchases.add(cart);
			this.setCart(null);
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the Purchases of the Buyer.
	 * @return the Purchases of the Buyer.
	 */
	public Set<Purchase> getPurchases() {
		return this.purchases;
	}

	/**
	 * Sets the Purchases of the Buyer.
	 * @param purchases: the new Purchases of the buyer.
	 */
	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}
	
	/**
	 * Adds a Purchase to the Buyer's set of Purchases.
	 * @param purchase: the Purchase to be added.
	 */
	public void addPurchase(Purchase purchase) {
		if(this.purchases == null) this.purchases = new HashSet<Purchase>();
		this.purchases.add(purchase);
		purchase.setBuyer(this);
	}

}
