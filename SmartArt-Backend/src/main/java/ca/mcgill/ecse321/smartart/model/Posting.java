package ca.mcgill.ecse321.smartart.model;

import DeliveryType;
import ArtStatus;

import javax.persistence.Entity;
import ca.mcgill.ecse321.smartart.model.Gallery;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Posting {
	// TODO implement this operation
	public Posting(String title, float price, int postingID) {
		this.title = title;
		this.price = price;
		this.postingID = postingID;
	}

	private Gallery gallery;

	@ManyToOne(optional = false)
	public Gallery getGallery() {
		return this.gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

	private int postingID;

	public void setPostingID(int value) {
		this.postingID = value;
	}

	public int getPostingID() {
		return this.postingID;
	}

	private Purchase purchase;

	@ManyToOne
	public Purchase getPurchase() {
		return this.purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	private Artist artist;

	@ManyToOne(optional = false)
	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	private String title;

	public void setTitle(String value) {
		this.title = value;
	}

	public String getTitle() {
		return this.title;
	}

	private float price;

	public void setPrice(float value) {
		this.price = value;
	}

	public float getPrice() {
		return this.price;
	}

	private String description;

	public void setDescription(String value) {
		this.description = value;
	}

	public String getDescription() {
		return this.description;
	}

	private float xDim;

	public void setXDim(float value) {
		this.xDim = value;
	}

	public float getXDim() {
		return this.xDim;
	}

	private float yDim;

	public void setYDim(float value) {
		this.yDim = value;
	}

	public float getYDim() {
		return this.yDim;
	}

	private float zDim;

	public void setZDim(float value) {
		this.zDim = value;
	}

	public float getZDim() {
		return this.zDim;
	}

	private ArtStatus artStatus;

	public void setArtStatus(ArtStatus value) {
		this.artStatus = value;
	}

	public ArtStatus getArtStatus() {
		return this.artStatus;
	}

	private DeliveryType deliveryType;

	public void setDeliveryType(DeliveryType value) {
		this.deliveryType = value;
	}

	public DeliveryType getDeliveryType() {
		return this.deliveryType;
	}
}
