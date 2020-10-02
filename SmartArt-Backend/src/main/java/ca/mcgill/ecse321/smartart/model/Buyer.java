package ca.mcgill.ecse321.smartart.model;

import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Buyer extends User {

	private Purchase cart;
	private Set<Purchase> purchases;

	public Buyer(String name, int phone, String email, String password) {
		super(name,phone, email,password);
	}

	@ManyToOne
	public Purchase getCart() {
		return this.cart;
	}

	public void setCart(Purchase cart) {
		this.cart = cart;
	}

	// TODO implement this operation
	public boolean addToCart(Posting p) {
		if(this.cart.addPosting(p))
			return true;
		return false;
	}

	// TODO implement this operation
	public boolean removeFromCart(Posting p) {
		if(this.cart.removePosting(p))
			return true;
		return false;
	}

	// TODO implement this operation
	public boolean makePurchase(Purchase cart) {
		if(cart != null) {
			this.purchases.add(cart);
			this.setCart(null);
			return true;
		}
		return false;
	}

	@OneToMany(mappedBy = "buyer")
	public Set<Purchase> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}

}
