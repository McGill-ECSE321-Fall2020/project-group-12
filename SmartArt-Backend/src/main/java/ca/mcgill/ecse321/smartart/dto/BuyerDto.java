package ca.mcgill.ecse321.smartart.dto;

import java.util.Collections;
import java.util.List;

public class BuyerDto {
	
	private String email;
	private String name;
	private String password;
	private int phone;
	private List<PurchaseDto> purchases;
	private GalleryDto gallery;
	private PurchaseDto cart;
	
	public BuyerDto() {
	}
	
	public BuyerDto(String email, GalleryDto gallery) {
		this(email, "user", "abc123", 1234, Collections.emptyList(), gallery);
	}
	
	public BuyerDto(String email, String name, String password, int phone, List<PurchaseDto> purchases, GalleryDto gallery) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.purchases = purchases;
		this.gallery = gallery;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getPhone() {
		return this.phone;
	}
	
	public void setPhone(int phone) {
		this.phone = phone;
	}
	
	public List<PurchaseDto> getPurchases(){
		return this.purchases;
	}
	
	public void setPurchases(List<PurchaseDto> purchases) {
		this.purchases = purchases;
	}
	
	public GalleryDto getGallery() {
		return this.gallery;
	}
	
	public void setGallery(GalleryDto gallery) {
		this.gallery = gallery;
	}
	
	public PurchaseDto getCart() {
		return this.cart;
	}
	
	public void setCart(PurchaseDto cart) {
		this.cart = cart;
	}
	
}