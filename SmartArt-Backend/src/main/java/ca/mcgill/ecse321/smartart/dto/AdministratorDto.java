package ca.mcgill.ecse321.smartart.dto;

import java.util.Collections;
import java.util.List;

public class AdministratorDto {
	
	private String email;
	private String name;
	private String password;
	private int phone;
	private GalleryDto gallery;
	
	public AdministratorDto() {
	}
	
	public AdministratorDto(String email, GalleryDto gallery) {
		this(email, "user", "abc123", 1234, gallery);
	}
	
	public AdministratorDto(String email, String name, String password, int phone, GalleryDto gallery) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.phone = phone;
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
	
	public GalleryDto getGallery() {
		return this.gallery;
	}
	
	public void setGallery(GalleryDto gallery) {
		this.gallery = gallery;
	}
	
	
}
