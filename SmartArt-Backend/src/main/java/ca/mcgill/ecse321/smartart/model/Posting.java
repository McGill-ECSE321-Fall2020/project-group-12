package ca.mcgill.ecse321.smartart.model;

import javax.persistence.ManyToMany;
import java.util.Set;

import ca.mcgill.ecse321.smartart.model.DeliveryType;
import ca.mcgill.ecse321.smartart.model.ArtStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import ca.mcgill.ecse321.smartart.model.Gallery;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Posting {

	@Id
	@Column(name = "postingID")
	private int postingID;
	@ManyToOne(optional = false)
	private Artist artist;
	@ManyToOne(optional = false)
	private Gallery gallery;
	@Column(name = "price")
	private float price;
	@Column(name = "xDim")
	private float xDim;
	@Column(name = "yDim")
	private float yDim;
	@Column(name = "zDim")
	private float zDim;
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "artStatus")
	private ArtStatus artStatus;
	@Column(name = "deliveryType")
	private DeliveryType deliveryType;
	
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

	public void setPostingID(int value) {
		this.postingID = value;
	}

	public int getPostingID() {
		return this.postingID;
	}

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
}
