package ca.mcgill.ecse321.smartart.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartart.dto.*;
import ca.mcgill.ecse321.smartart.model.*;
import ca.mcgill.ecse321.smartart.service.SmartArtService;

@CrossOrigin(origins = "*")
@RestController
public class SmartArtRestController {

	@Autowired
	private SmartArtService service;
	
	//Clear database
	@PostMapping(value = {"/clearDatabase", "/clearDatabase/"})
    public void clearDatabase() {
        service.clearDatabase();
    }
	
	//////////////////////////////
	/////Gallery methods//////////
	//////////////////////////////
	
	@GetMapping(value = {"/galleries", "/galleries/"})
	public List<Gallery> getAllGalleries(){
		return service.getAllGalleries();
	}
	
	@GetMapping(value = { "/galleries/{name}", "/galleries/{name}/" })
	public Gallery getGalleryByName(@PathVariable("name") String name) throws IllegalArgumentException {
		return service.getGallery(name);
	}
	
	@PostMapping(value = { "/gallery/{name}/{city}/{commission}", "/gallery/{name}/{city}/{commission}/" })
	public Gallery createGallery(@PathVariable("name") String name, @PathVariable("city") String city, @PathVariable("commission") double commission) {
		Gallery gallery = service.createGallery(name, city, commission);
		return gallery;
	}
	
	@PostMapping(value = { "/gallery/create", "/gallery/create/" })
	public Gallery createGallery(@RequestBody Gallery data) {
		Gallery gallery = service.createGallery(data);
		return gallery;
	}
	
	//////////////////////////////
	/////Artist methods///////////
	//////////////////////////////
	
	@GetMapping(value = {"/artists", "/artists/"})
	public List<Artist> getAllArtists(){
		return service.getAllArtists();
	}
	
	@GetMapping(value = { "/artists/{email}", "/artists/{email}/" })
	public Artist getArtistByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return service.getArtist(email);
	}
	
	@PostMapping(value = { "/artist/{email}/{name}/{password}", "/artist/{email}/{name}/{password}/" })
	public Artist createArtist(@PathVariable("email") String email, @PathVariable("name") String name, @PathVariable("password")String password) throws IllegalArgumentException {
		Gallery gallery = service.getAllGalleries().get(0);
		Artist artist = service.createArtist(email, name, password, gallery);
		return artist;
	}
	
	//////////////////////////////
	/////Administrator methods////
	//////////////////////////////
	
	@GetMapping(value = {"/administrators", "/administrators/"})
	public List<Administrator> getAllAdministrators(){
		return service.getAllAdministrators();
	}
	@GetMapping(value = { "/administrators/{email}", "/administrators/{email}/" })
	public Administrator getAdministratorByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return service.getAdministrator(email);
	}
	
	@PostMapping(value = { "/administrator/{email}/{name}/{password}", "/administrator/{email}/{name}/{password}/" })
	public Administrator createAdministrator(@PathVariable("email") String email, @PathVariable("name") String name, @PathVariable("password")String password) throws IllegalArgumentException {
		Gallery gallery = service.getAllGalleries().get(0);
		Administrator administrator = service.createAdministrator(email, name, password, gallery);
		return administrator;
	}
	
	//////////////////////////////
	/////Buyer methods////////////
	//////////////////////////////
	
	@GetMapping(value = {"/buyers", "/buyers/"})
	public List<Buyer> getAllBuyers(){
		return service.getAllBuyers();
	}
	
	@GetMapping(value = { "/buyers/{email}", "/buyers/{email}/" })
	public Buyer getBuyerByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return service.getBuyer(email);
	}
	
	@PostMapping(value = { "/buyer/{email}/{name}/{password}", "/buyer/{email}/{name}/{password}/" })
	public Buyer createBuyer(@PathVariable("email") String email, @PathVariable("name") String name, @PathVariable("password")String password) throws IllegalArgumentException {
		Gallery gallery = service.getAllGalleries().get(0);
		Buyer buyer = service.createBuyer(email, name, password, gallery);
		return buyer;
	}
	
	//////////////////////////////
	/////Posting methods//////////
	//////////////////////////////
	
	@GetMapping(value = {"/postings", "/postings/" })
	public List<Posting> getAllPostings(){
		return service.getAllPostings();
	}
	
	@GetMapping(value = { "/postings/{postingID}}", "/postings/{postingID}/" })
	public Posting getPostingByPostingID(@PathVariable("postingID") int postingID)  throws IllegalArgumentException{
		return service.getPosting(postingID);
	}
	
	@PostMapping(value = {"/posting/{id}/{artist}/{price}/{x}/{y}/{z}/{title}/{description}/{date}", "/posting/{id}/{artist}/{price}/{x}/{y}/{z}/{title}/{description}/{date}/" })
	public Posting createPosting(@PathVariable("id") int postingID, @PathVariable("artist") Artist artist, @PathVariable("price") double price, @PathVariable("x") double xDim, @PathVariable("y")double yDim, @PathVariable("z") double zDim, @PathVariable("title") String title, @PathVariable("description") String description, @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy.MM.dd") Date date) {
		Posting posting = service.createPosting(postingID, artist, price, xDim, yDim, zDim, title, description, date);
		return posting;
	}
	
	//////////////////////////////
	/////Purchase methods/////////
	//////////////////////////////
	
	@GetMapping(value = {"/purchases", "/puchases/" })
	public List<Purchase> getAllPurchases(){
		return service.getAllPurchases();
	}
	
	@GetMapping(value = { "/purchases/{purchaseID}}", "/purchases/{purchaseID}/" })
	public Purchase getPurchaseByPurchaseID(@PathVariable("purchaseID") int purchaseID)  throws IllegalArgumentException{
		return service.getPurchase(purchaseID);
	}
	

	@PostMapping(value = {"/purchase/{id}/{buyer}", "/purchase/{id}/{buyer}/" })
	public Purchase createPurchase(@PathVariable("id") int purchaseID, @PathVariable("buyer") Buyer buyer) {
		Purchase purchase = service.createPurchase(purchaseID, buyer);
		return purchase;
	}
	
	//////////////////////////////
	/////Action methods///////////
	//////////////////////////////
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	////////Private methods to convert model instances into corresponding Data Transfer Objects////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	private GalleryDto convertToDto(Gallery g) {
		if (g == null) {
			throw new IllegalArgumentException("There is no such Gallery.");
		}
		GalleryDto galleryDto = new GalleryDto(g.getName(), g.getCity(), g.getCommission());
		return galleryDto;
	}
	
	private ArtistDto convertToDto(Artist a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Artist.");
		}
		ArtistDto artistDto = new ArtistDto(a.getEmail(), a.getName(), a.getPassword(), a.getPhone(), convertToDto(a.getGallery()));
		return artistDto;
	}
	
	private AdministratorDto convertToDto(Administrator a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Administrator.");
		}
		AdministratorDto administratorDto = new AdministratorDto(a.getEmail(), a.getName(), a.getPassword(), a.getPhone(), convertToDto(a.getGallery()));
		return administratorDto;
	}
	
	private BuyerDto convertToDto(Buyer b) {
		if (b == null) {
			throw new IllegalArgumentException("There is no such Buyer.");
		}
		BuyerDto buyerDto = new BuyerDto(b.getEmail(), b.getName(), b.getPassword(), b.getPhone(), convertToDto(b.getGallery()));
		return buyerDto;
	}
	
	private ArtStatusDto convertToDto(ArtStatus a) {
		switch(a) {
			case Available: return ArtStatusDto.Available;
			case OnHold: return ArtStatusDto.OnHold;
			case Purchased: return ArtStatusDto.Purchased;
			default: return null;
		}
	}
	
	private DeliveryTypeDto convertToDto(DeliveryType d) {
		switch(d) {
			case PickUp: return DeliveryTypeDto.PickUp;
			case Shipped: return DeliveryTypeDto.Shipped;
			default: return null;
		}
	}
	
	private PostingDto convertToDto(Posting p) {
		if (p == null) {
			throw new IllegalArgumentException("There is no such Posting.");
		}
		PostingDto postingDto = new PostingDto(p.getPostingID(), convertToDto(p.getArtist()), convertToDto(p.getGallery()), p.getPrice(), p.getXDim(), p.getYDim(), p.getZDim(), p.getTitle(), p.getDescription(), convertToDto(p.getArtStatus()), p.getDate());
		return postingDto;
	}
	
	private PurchaseDto convertToDto(Purchase p) {
		if (p == null) {
			throw new IllegalArgumentException("There is no such Purchase.");
		}
		PurchaseDto purchaseDto = new PurchaseDto(p.getPurchaseID(), convertToDto(p.getBuyer()));
		return purchaseDto;
	}

}
