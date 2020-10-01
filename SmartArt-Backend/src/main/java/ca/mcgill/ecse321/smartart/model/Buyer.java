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
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	// TODO implement this operation
	public boolean removeFromCart(Posting p) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	// TODO implement this operation
	public boolean makePurchase(Purchase cart) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	@OneToMany(mappedBy = "buyer")
	public Set<Purchase> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}

}
