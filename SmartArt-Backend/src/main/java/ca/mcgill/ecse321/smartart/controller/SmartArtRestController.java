package ca.mcgill.ecse321.smartart.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public List<GalleryDto> getAllGalleries(){
		return service.getAllGalleries().stream().map(g -> convertToDto(g)).collect(Collectors.toList());
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
	public List<ArtistDto> getAllArtists(){
		return service.getAllArtists().stream().map(a -> convertToDto(a)).collect(Collectors.toList());
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
	public List<AdministratorDto> getAllAdministrators(){
		return service.getAllAdministrators().stream().map(a -> convertToDto(a)).collect(Collectors.toList());
	}
	@GetMapping(value = { "/administrators/{email}", "/administrators/{email}/" })
	public AdministratorDto getAdministratorByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return convertToDto(service.getAdministrator(email));
	}
	
	@PostMapping(value = { "/administrator/create", "/administrator/create/"})
	public AdministratorDto createAdministrator(@RequestBody AdministratorDto data) throws IllegalArgumentException{
		Administrator administrator = service.createAdministrator(data);
		return convertToDto(administrator);
	}
	
	//////////////////////////////
	/////Buyer methods////////////
	//////////////////////////////
	
	@GetMapping(value = {"/buyers", "/buyers/"})
	public List<BuyerDto> getAllBuyers(){
		return service.getAllBuyers().stream().map(b -> convertToDto(b)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/buyers/{email}", "/buyers/{email}/" })
	public BuyerDto getBuyerByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return convertToDto(service.getBuyer(email));
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
	public ResponseEntity<?> getAllPostings(){
		List<PostingDto> postings = service.getAllPostings().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
		return new ResponseEntity<>(postings, HttpStatus.OK);
	}
	
	@GetMapping(value = { "/postings/{postingID}", "/postings/{postingID}/" })
	public ResponseEntity<?> getPostingByPostingID(@PathVariable("postingID") int postingID) {
		PostingDto posting = convertToDto(service.getPosting(postingID));
		if(posting != null)
			return new ResponseEntity<>(posting, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value = { "/postings/artist/{email}", "/postings/artist/{email}/" })
	public ResponseEntity<?> getPostingsByArtist(@PathVariable("email") String email) {
		List<PostingDto> postings = service.getPostingsByArtist(email).stream().map(p -> convertToDto(p)).collect(Collectors.toList());
		if(postings != null)
			return new ResponseEntity<>(postings, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value = {"/posting/create", "/posting/create/" })
	public ResponseEntity<?> createPosting(@RequestBody PostingDto data) {
		try {
			Posting posting = service.createPosting(data);
			PostingDto postingData = convertToDto(posting);
			return new ResponseEntity<>(postingData, HttpStatus.CREATED);
			
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = {"/posting/delete", "/posting/delete/"})
	public ResponseEntity<?> deletePosting(@RequestBody PostingDto posting) {
		try {
			service.deletePosting(posting);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NullPointerException n) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//////////////////////////////
	/////Purchase methods/////////
	//////////////////////////////
	
	@GetMapping(value = {"/purchases", "/puchases/" })
	public ResponseEntity<?> getAllPurchases(){
		List<PurchaseDto> purchases = service.getAllPurchases().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
		return new ResponseEntity<>(purchases, HttpStatus.OK);
	}
	
	@GetMapping(value = { "/purchases/{purchaseID}", "/purchases/{purchaseID}/" })
	public ResponseEntity<?> getPurchaseByPurchaseID(@PathVariable("purchaseID") int purchaseID) {
		Purchase purchase = service.getPurchase(purchaseID);
		if (purchase != null) {
			return new ResponseEntity<>(convertToDto(purchase), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value = { "/purchases/buyer/{email}", "/purchases/buyer/{email}/" })
	public ResponseEntity<?> getPurchasesByBuyer(@PathVariable("email") String email) {
		try {
			List<PurchaseDto> purchases = service.getPurchasesByBuyer(email).stream().map(p -> convertToDto(p)).collect(Collectors.toList());
			return new ResponseEntity<>(purchases, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = { "/purchases/cart/{email}", "/purchases/cart/{email}/" })
	public ResponseEntity<?> getCart(@PathVariable("email") String email) {
		Purchase cart = service.getCart(email);
		try {
			if(cart != null)
				return new ResponseEntity<>(convertToDto(cart), HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = {"/purchase/create", "/purchase/create/" })
	public ResponseEntity<?> createPurchase(@RequestBody PurchaseDto data) {
		try {
			Purchase purchase = service.createPurchase(data);
			return new ResponseEntity<>(convertToDto(purchase), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//////////////////////////////
	/////Action methods///////////
	//////////////////////////////
	
	@PostMapping(value = {"/purchase/make/{deliveryType}", "/purchase/make/{deliveryType}/"})
	public ResponseEntity<?> makePurchase(@RequestBody PurchaseDto data, @PathVariable("deliveryType") DeliveryType deliveryType) {
		try {
			Purchase purchase = service.makePurchase(data, deliveryType);
			return new ResponseEntity<>(convertToDto(purchase), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = {"/purchase/cancel", "/purchase/cancel/"})
	public ResponseEntity<?> cancelPurchase(@RequestBody PurchaseDto data) {
		try {
			boolean canceled = service.cancelPurchase(data);
			if(canceled)
				return new ResponseEntity<>(HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = {"/purchase/cart/add/{postingID}", "/purchase/cart/add/{postingID}/"})
	public ResponseEntity<?> addToCart(@RequestBody BuyerDto buyerData, @PathVariable(name = "postingID") int postingID) {
		try {
			Purchase cart = service.addToCart(buyerData, postingID);
			return new ResponseEntity<>(convertToDto(cart), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
	}
	
	@DeleteMapping(value = {"/purchase/cart/remove/{postingID}", "/purchase/cart/remove/{postingID}/"})
	public ResponseEntity<?> removeFromCart(@RequestBody BuyerDto buyerData, @PathVariable (name = "postingID") int postingID) {
		try {
			Purchase cart = service.removeFromCart(buyerData, postingID);
			return new ResponseEntity<>(convertToDto(cart), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
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
		ArtistDto artistDto = new ArtistDto(a.getEmail(), a.getName(), a.getPassword(), convertToDto(a.getGallery()));
		return artistDto;
	}
	
	private AdministratorDto convertToDto(Administrator a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Administrator.");
		}
		AdministratorDto administratorDto = new AdministratorDto(a.getEmail(), a.getName(), a.getPassword(), convertToDto(a.getGallery()));
		return administratorDto;
	}
	
	private BuyerDto convertToDto(Buyer b) {
		if (b == null) {
			throw new IllegalArgumentException("There is no such Buyer.");
		}
		BuyerDto buyerDto = new BuyerDto(b.getEmail(), b.getName(), b.getPassword(), convertToDto(b.getGallery()));
		return buyerDto;
	}
	
	private PostingDto convertToDto(Posting p) {
		if (p == null) {
			throw new IllegalArgumentException("There is no such Posting.");
		}
		PostingDto postingDto = new PostingDto(p.getPostingID(), convertToDto(p.getArtist()), convertToDto(p.getGallery()), p.getPrice(), p.getXDim(), p.getYDim(), p.getZDim(), p.getTitle(), p.getDescription(), p.getArtStatus(), p.getDate(), p.getImage());
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
