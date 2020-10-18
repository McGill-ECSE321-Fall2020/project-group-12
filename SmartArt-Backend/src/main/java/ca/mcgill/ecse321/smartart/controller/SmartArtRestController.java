package ca.mcgill.ecse321.smartart.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public List<GalleryDto> getAllGalleries(){
		return service.getAllGalleries().stream().map(g -> convertToDto(g)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/galleries/{name}", "/galleries/{name}/" })
	public GalleryDto getGalleryByName(@PathVariable("name") String name) throws IllegalArgumentException {
		return convertToDto(service.getGallery(name));
	}
	
	@PostMapping(value = { "/gallery/{name}/{city}/{commission}", "/gallery/{name}/{city}/{commission}/" })
	public GalleryDto createGallery(@PathVariable("name") String name, @PathVariable("city") String city, @PathVariable("commission") double commission) {
		Gallery gallery = service.createGallery(name, city, commission);
		return convertToDto(gallery);
	}
	
	//////////////////////////////
	/////Artist methods///////////
	//////////////////////////////
	
	@GetMapping(value = {"/artists", "/artists/"})
	public List<ArtistDto> getAllArtists(){
		return service.getAllArtists().stream().map(a -> convertToDto(a)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/artists/{email}", "/artists/{email}/" })
	public ArtistDto getArtistByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return convertToDto(service.getArtist(email));
	}
	
	@PostMapping(value = { "/artist/{email}/{name}/{password}", "/artist/{email}/{name}/{password}/" })
	public ArtistDto createArtist(@PathVariable("email") String email, @PathVariable("name") String name, @PathVariable("password")String password) throws IllegalArgumentException {
		Gallery gallery = service.getAllGalleries().get(0);
		Artist artist = service.createArtist(email, name, password, gallery);
		return convertToDto(artist);
	}
	
	//////////////////////////////
	/////Administrator methods////
	//////////////////////////////
	
	@GetMapping(value = {"/administrators", "/administrators/"})
	public List<AdministratorDto> getAllAdministrators(){
		return service.getAllAdministrators().stream().map(a -> convertToDto(a)).collect(Collectors.toList());
	}
	@GetMapping(value = { "/administrators/{email}", "/administrators/{email}/" })
	public AdministratorDto getAdministratorByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return convertToDto(service.getAdministrator(email));
	}
	
	@PostMapping(value = { "/administrator/{email}/{name}/{password}", "/administrator/{email}/{name}/{password}/" })
	public AdministratorDto createAdministrator(@PathVariable("email") String email, @PathVariable("name") String name, @PathVariable("password")String password) throws IllegalArgumentException {
		Gallery gallery = service.getAllGalleries().get(0);
		Administrator administrator = service.createAdministrator(email, name, password, gallery);
		return convertToDto(administrator);
	}
	
	//////////////////////////////
	/////Buyer methods////////////
	//////////////////////////////
	
	@GetMapping(value = {"/buyers", "/buyers/"})
	public List<BuyerDto> getAllBuyers(){
		return service.getAllBuyers().stream().map(a -> convertToDto(a)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/buyers/{email}", "/buyers/{email}/" })
	public BuyerDto getBuyerByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return convertToDto(service.getBuyer(email));
	}
	
	@PostMapping(value = { "/buyer/{email}/{name}/{password}", "/buyer/{email}/{name}/{password}/" })
	public BuyerDto createBuyer(@PathVariable("email") String email, @PathVariable("name") String name, @PathVariable("password")String password) throws IllegalArgumentException {
		Gallery gallery = service.getAllGalleries().get(0);
		Buyer buyer = service.createBuyer(email, name, password, gallery);
		return convertToDto(buyer);
	}
	
	//////////////////////////////
	/////Posting methods//////////
	//////////////////////////////
	
	@GetMapping(value = {"/postings", "/postings/" })
	public List<PostingDto> getAllPostings(){
		return service.getAllPostings().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/postings/{postingID}}", "/postings/{postingID}/" })
	public PostingDto getPostingByPostingID(@PathVariable("postingID") int postingID)  throws IllegalArgumentException{
		return convertToDto(service.getPosting(postingID));
	}
	
	@PostMapping(value = {"/posting/{id}/{artist}/{price}/{x}/{y}/{z}/{title}/{description}/{date}", "/posting/{id}/{artist}/{price}/{x}/{y}/{z}/{title}/{description}/{date}/" })
	public PostingDto createPosting(@PathVariable("id") int postingID, @PathVariable("artist") Artist artist, @PathVariable("price") double price, @PathVariable("x") double xDim, @PathVariable("y")double yDim, @PathVariable("z") double zDim, @PathVariable("title") String title, @PathVariable("description") String description, @PathVariable("date") Date date) {
		Posting posting = service.createPosting(postingID, artist, price, xDim, yDim, zDim, title, description, date);
		return convertToDto(posting);
	}
	
	//////////////////////////////
	/////Purchase methods/////////
	//////////////////////////////
	
	@GetMapping(value = {"/purchases", "/puchases/" })
	public List<PurchaseDto> getAllPurchases(){
		return service.getAllPurchases().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/purchases/{purchaseID}}", "/purchases/{purchaseID}/" })
	public PurchaseDto getPurchaseByPurchaseID(@PathVariable("purchaseID") int purchaseID)  throws IllegalArgumentException{
		return convertToDto(service.getPurchase(purchaseID));
	}
	
	@PostMapping(value = {"/purchase/{id}/{buyer}", "/purchase/{id}/{buyer}/" })
	public PurchaseDto createPurchase(@PathVariable("id") int purchaseID, @PathVariable("buyer") Buyer buyer) {
		Purchase purchase = service.createPurchase(purchaseID, buyer);
		return convertToDto(purchase);
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
