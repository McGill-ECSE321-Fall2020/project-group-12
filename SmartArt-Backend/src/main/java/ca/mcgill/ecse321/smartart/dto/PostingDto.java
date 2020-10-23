package ca.mcgill.ecse321.smartart.dto;

import java.sql.Date;

import ca.mcgill.ecse321.smartart.model.ArtStatus;

public class PostingDto {

	private int postingID;
	private ArtistDto artist;
	private GalleryDto gallery;
	private int price;
	private double xDim;
	private double yDim;
	private double zDim;
	private String title;
	private String description;
	private ArtStatus artStatus;
	private Date date;
	
	public PostingDto(){
	}
	
	public PostingDto(int postingID, ArtistDto artist, GalleryDto gallery) {
		this(postingID, artist, gallery, 10000, 5, 5, 5, "Dog", "This is a dog", ArtStatus.Available, new Date(0));
	}
	
	public PostingDto(int postingID, ArtistDto artist, GalleryDto gallery, int price, double xDim, double yDim, double zDim, String title, String description, ArtStatus artStatus, Date date) {
		this.postingID = postingID;
		this.artist = artist;
		this.gallery = gallery;
		this.price = price;
		this.xDim = xDim;
		this.yDim = yDim;
		this.zDim = zDim;
		this.title = title;
		this.description = description;
		this.artStatus = artStatus;
		this.date = date;
	}
	
	
	
	public void setArtStatus(ArtStatus value) {
		this.artStatus = value;
	}

	public ArtStatus getArtStatus() {
		return this.artStatus;
	}

	public void setPostingID(int value) {
		this.postingID = value;
	}

	public int getPostingID() {
		return this.postingID;
	}
	
	public ArtistDto getArtist() {
		return this.artist;
	}

	public void setArtist(ArtistDto artist) {
		this.artist = artist;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public String getTitle() {
		return this.title;
	}

	public void setPrice(int value) {
		this.price = value;
	}

	public int getPrice() {
		return this.price;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public String getDescription() {
		return this.description;
	}

	public void setXDim(double value) {
		this.xDim = value;
	}

	public double getXDim() {
		return this.xDim;
	}

	public void setYDim(double value) {
		this.yDim = value;
	}

	public double getYDim() {
		return this.yDim;
	}

	public void setZDim(double value) {
		this.zDim = value;
	}

	public double getZDim() {
		return this.zDim;
	}

	public void setGallery(GalleryDto gallery) {
		this.gallery = gallery;
	}

	public GalleryDto getGallery() {
		return this.gallery;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	
}
