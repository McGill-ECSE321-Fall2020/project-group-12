package ca.mcgill.ecse321.smartart.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartart.model.*;

@Repository
public class SmartArtRepository {

	@Autowired
	EntityManager entityManager;

	@Transactional
	public Gallery createGallery(String name) {
		Gallery g = new Gallery();
		g.setName(name);
		entityManager.persist(g);
		return g;
	}

	@Transactional
	public Gallery getGallery(String name) {
		Gallery g = entityManager.find(Gallery.class, name);
		return g;
	}
	
	@Transactional
	public Administrator createAdministrator(String name, int phone, String email, String password) {
		Administrator a = new Administrator();
		a.setName(name);
		a.setPhone(phone);
		a.setEmail(email);
		a.setPassword(password);
		entityManager.persist(a);
		return a;
	}

	@Transactional
	public Administrator getAdministrator(String email) {
		Administrator a = entityManager.find(Administrator.class, email);
		return a;
	}
	
	@Transactional
	public Artist createArtist(String name, int phone, String email, String password) {
		Artist a = new Artist();
		a.setName(name);
		a.setPhone(phone);
		a.setEmail(email);
		a.setPassword(password);
		entityManager.persist(a);
		return a;
	}

	@Transactional
	public Artist getArtist(String email) {
		Artist a = entityManager.find(Artist.class, email);
		return a;
	}
	
	@Transactional
	public Buyer createBuyer(String name, int phone, String email, String password) {
		Buyer b = new Buyer();
		b.setName(name);
		b.setPhone(phone);
		b.setEmail(email);
		b.setPassword(password);
		entityManager.persist(b);
		return b;
	}

	@Transactional
	public Buyer getBuyer(String email) {
		Buyer b = entityManager.find(Buyer.class, email);
		return b;
	}
	
	@Transactional
	public Posting createPosting(String title, float price, int postingID) {
		Posting p = new Posting();
		p.setTitle(title);
		p.setPrice(price);
		p.setPostingID(postingID);
		entityManager.persist(p);
		return p;
	}
	
	@Transactional
	public Posting getPosting(int postingID) {
		Posting p = entityManager.find(Posting.class, postingID);
		return p;
	}
	
	@Transactional
	public Purchase createPurchase(int purchaseID) {
		Purchase p = new Purchase();
		p.setPurchaseID(purchaseID);
		entityManager.persist(p);
		return p;
	}
	
	@Transactional
	public Purchase getPurchase(int purchaseID) {
		Purchase p = entityManager.find(Purchase.class, purchaseID);
		return p;
	}

	@Transactional
	public List<Posting> getPoatingsUnderPrice(int maxPrice) {
		TypedQuery<Posting> q = entityManager.createQuery("select p from Posting p where p.price < :maxPrice",Posting.class);
		q.setParameter("maxPrice", maxPrice);
		List<Posting> resultList = q.getResultList();
		return resultList;
	}
	
}
