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
	public GalleryDto getGalleryByName(@PathVariable("name") String name) throws IllegalArgumentException {
		return convertToDto(service.getGallery(name));
	}
	
	@PostMapping(value = { "/gallery/create", "/gallery/create/" })
	public GalleryDto createGallery(@RequestBody GalleryDto data) {
		Gallery gallery = service.createGallery(data);
		return convertToDto(gallery);
	}
	
	//////////////////////////////
	/////Artist methods///////////
	//////////////////////////////
	
	@GetMapping(value = {"/artists", "/artists/"})
	public List<Artist> getAllArtists(){
		return service.getAllArtists();
	}
	
	@GetMapping(value = { "/artists/{email}", "/artists/{email}/" })
	public ArtistDto getArtistByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return convertToDto(service.getArtist(email));
	}
	
	@PostMapping(value = { "/artist/create", "/artist/create/" })
	public ArtistDto createArtist(@RequestBody ArtistDto data) throws IllegalArgumentException {
		Artist artist = service.createArtist(data);
		return convertToDto(artist);
	}
	
	//////////////////////////////
	/////Administrator methods////
	//////////////////////////////
	
	@GetMapping(value = {"/administrators", "/administrators/"})
	public List<Administrator> getAllAdministrators(){
		return service.getAllAdministrators();
	}
	@GetMapping(value = { "/administrators/{email}", "/administrators/{email}/" })
	public AdministratorDto getAdministratorByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return convertToDto(service.getAdministrator(email));
	}
	
	@PostMapping(value = { "/adminstrator/create", "/adminstrator/create/"})
	public AdministratorDto createAdministrator(@RequestBody AdministratorDto data) throws IllegalArgumentException{
		Administrator administrator = service.createAdministrator(data);
		return convertToDto(administrator);
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
	
	@PostMapping(value = { "/buyer/create", "/buyer/create/" })
	public BuyerDto createBuyer(@RequestBody BuyerDto data) throws IllegalArgumentException {
		Buyer buyer = service.createBuyer(data);
		return convertToDto(buyer);
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
	
	@PostMapping(value = {"/posting/create", "/posting/create/" })
	public PostingDto createPosting(@RequestBody PostingDto data) {
		Posting posting = service.createPosting(data);
		return convertToDto(posting);
	}
	
	@PostMapping(value = {"/posting/delete", "/posting/delete/"})
	public Posting deletePosting(@RequestBody Posting posting) {
		Posting deletedPost = service.deletePosting(posting);
		return deletedPost;
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
	
	@PostMapping(value = {"/purchase/create", "/purchase/create/" })
	public PurchaseDto createPurchase(@RequestBody PurchaseDto data) {
		Purchase purchase = service.createPurchase(data);
		return convertToDto(purchase);
	}
	//////////////////////////////
	/////Action methods///////////
	//////////////////////////////
	
	@PostMapping(value = {"/purchase/make/{deliveryType}", "/purchase/make/{deliveryType}/"})
	public PurchaseDto makePurchase(@RequestBody PurchaseDto data, @PathVariable("deliveryType") DeliveryType deliveryType) {
		Purchase purchase = service.makePurchase(data, deliveryType);
		return convertToDto(purchase);
	}
	
	@PostMapping(value = {"/purchase/cancel", "/purchase/cancel/"})
	public boolean cancelPurchase(@RequestBody PurchaseDto data) {
		boolean canceled = cancelPurchase(data);
		return canceled;
	}
	
	@PostMapping(value = {"/purchase/cart/add", "/purchase/cart/add/"})
	public PurchaseDto addToCart(@RequestBody BuyerDto buyerData, @RequestBody PostingDto postingData) {
		Purchase cart = service.addToCart(buyerData, postingData);
		return convertToDto(cart);
	}
	
	@PostMapping(value = {"/purchase/cart/remove", "/purchase/cart/remove/"})
	public PurchaseDto removeFromCart(@RequestBody BuyerDto buyerData, @RequestBody PostingDto postingData) {
		Purchase cart = service.removeFromCart(buyerData, postingData);
		return convertToDto(cart);
	}
	
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
	
	private PostingDto convertToDto(Posting p) {
		if (p == null) {
			throw new IllegalArgumentException("There is no such Posting.");
		}
		PostingDto postingDto = new PostingDto(p.getPostingID(), convertToDto(p.getArtist()), convertToDto(p.getGallery()), p.getPrice(), p.getXDim(), p.getYDim(), p.getZDim(), p.getTitle(), p.getDescription(), p.getArtStatus(), p.getDate());
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
