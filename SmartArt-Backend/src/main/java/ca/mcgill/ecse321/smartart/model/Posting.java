package ca.mcgill.ecse321.smartart.model;

import ca.mcgill.ecse321.smartart.model.DeliveryType;
import ca.mcgill.ecse321.smartart.model.ArtStatus;

import javax.persistence.Entity;
import javax.persistence.Id;

import ca.mcgill.ecse321.smartart.model.Gallery;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Posting {
	
	private Gallery gallery;
	private int postingID;
	private Artist artist;
	private float price;
	private float xDim;
	private float yDim;
	private float zDim;
	private String title;
	private ArtStatus artStatus;
	private DeliveryType deliveryType;
	
	public Posting(String title, float price, int postingID) {
		this.title = title;
		this.price = price;
		this.postingID = postingID;
	}


	@ManyToOne(optional = false)
	public Gallery getGallery() {
		return this.gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

	public void setPostingID(int value) {
		this.postingID = value;
	}

	@Id
	public int getPostingID() {
		return this.postingID;
	}

	@ManyToOne(optional = false)
	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public String getTitle() {
		return this.title;
	}

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

	public void setXDim(float value) {
		this.xDim = value;
	}

	public float getXDim() {
		return this.xDim;
	}

	public void setYDim(float value) {
		this.yDim = value;
	}

	public float getYDim() {
		return this.yDim;
	}

	public void setZDim(float value) {
		this.zDim = value;
	}

	public float getZDim() {
		return this.zDim;
	}

	public void setArtStatus(ArtStatus value) {
		this.artStatus = value;
	}

	public ArtStatus getArtStatus() {
		return this.artStatus;
	}

	public void setDeliveryType(DeliveryType value) {
		this.deliveryType = value;
	}

	public DeliveryType getDeliveryType() {
		return this.deliveryType;
	}
}
