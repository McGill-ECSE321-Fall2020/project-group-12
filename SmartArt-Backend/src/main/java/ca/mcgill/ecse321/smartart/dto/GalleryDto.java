package ca.mcgill.ecse321.smartart.dto;

import java.util.Collections;
import java.util.List;

public class GalleryDto {

	private String name;
	private String city;
	private double commission;
	private List<ArtistDto> artists;
	private List<AdministratorDto> administrators;
	private List<BuyerDto> buyers;
	private List<PostingDto> postings;
	
	public GalleryDto() {
	}
	
	public GalleryDto(String name) {
		this(name, "mtl", 0.1, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
	}
	
	public GalleryDto(String name, String city, double commission) {
		this(name, city, commission, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
	}
	
	public GalleryDto(String name, String city, double commission, List<ArtistDto> artists, List<AdministratorDto> administrators, List<BuyerDto> buyers, List<PostingDto> postings) {
		this.name = name;
		this.city = city;
		this.commission = commission;
		this.artists = artists;
		this.administrators = administrators;
		this.buyers = buyers;
		this.postings = postings;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public double getCommission() {
		return this.commission;
	}
	
	public void setCommission(double commission) {
		this.commission = commission;
	}
	
	public List<ArtistDto> getArtists(){
		return this.artists;
	}
	
	public void setArtists(List<ArtistDto> artists) {
		this.artists = artists;
	}
	
	public List<AdministratorDto> getAdministrators(){
		return this.administrators;
	}
	
	public void setAdministrators(List<AdministratorDto> administrators) {
		this.administrators = administrators;
	}
	
	public List<BuyerDto> getBuyers(){
		return this.buyers;
	}
	
	public void setBuyers(List<BuyerDto> buyers) {
		this.buyers = buyers;
	}
	
	public List<PostingDto> getPostings(){
		return this.postings;
	}
	
	public void setPostings(List<PostingDto> postings) {
		this.postings = postings;
	}
	
}
