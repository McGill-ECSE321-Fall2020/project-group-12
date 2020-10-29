package ca.mcgill.ecse321.smartart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import ca.mcgill.ecse321.smartart.model.*;
import ca.mcgill.ecse321.smartart.dao.*;


@ExtendWith(MockitoExtension.class)
public class TestSmartArtService {

	@Mock
	private ArtistRepository artistDao;
	@Mock
	private AdministratorRepository administratorDao;
	@Mock
	private BuyerRepository buyerDao;
	@Mock
	private GalleryRepository galleryDao;
	@Mock
	private PostingRepository postingDao;
	@Mock
	private PurchaseRepository purchaseDao;
	
	@InjectMocks
	private AdministratorService adminService;
	@InjectMocks
	private ArtistService artistService;
	@InjectMocks
	private BuyerService buyerService;
	@InjectMocks
	private GalleryService galleryService;
	@InjectMocks
	private PostingService postingService;
	@InjectMocks
	private PurchaseService purchaseService;
	
	private static final String GALLERY_KEY = "TestGallery";
	private static final String NONEXISTING_GALLERY = "NotAGallery";
	private static final String ARTIST_KEY = "TestArtist";
	private static final String NONEXISTING_ARTIST = "NotAnArtist";
	private static final String ADMINISTRATOR_KEY = "TestAdministrator";
	private static final String NONEXISTING_ADMINISTRATOR = "NotAnAdministrator";
	private static final String BUYER_KEY = "TestBuyer";
	private static final String NONEXISTING_BUYER = "NotABuyer";
	private static final int POSTING_KEY = 100;
	private static final int NONEXISTING_POSTING = -1;
	private static final int PURCHASE_KEY = 200;
	private static final int NONEXISTING_PURCHASE = -1;
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(galleryDao.findGalleryByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(GALLERY_KEY)) {
				Gallery gallery = new Gallery();
				gallery.setName(GALLERY_KEY);
				return gallery;
			} else {
				return null;
			}
		});
		lenient().when(artistDao.findArtistByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ARTIST_KEY)) {
				Artist artist = new Artist();
				artist.setEmail(ARTIST_KEY);
				return artist;
			} else {
				return null;
			}
		});
		lenient().when(administratorDao.findAdministratorByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ADMINISTRATOR_KEY)) {
				Administrator administrator = new Administrator();
				administrator.setEmail(ADMINISTRATOR_KEY);
				return administrator;
			} else {
				return null;
			}
		});
		lenient().when(buyerDao.findBuyerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(BUYER_KEY)) {
				Buyer buyer = new Buyer();
				buyer.setEmail(BUYER_KEY);
				return buyer;
			} else {
				return null;
			}
		});
		lenient().when(postingDao.findPostingByPostingID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(POSTING_KEY)) {
				Posting posting = new Posting();
				posting.setPostingID(POSTING_KEY);
				return posting;
			} else {
				return null;
			}
		});
		lenient().when(purchaseDao.findPurchaseByPurchaseID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PURCHASE_KEY)) {
				Purchase purchase = new Purchase();
				purchase.setPurchaseID(PURCHASE_KEY);
				return purchase;
			} else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(galleryDao.save(any(Gallery.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(artistDao.save(any(Artist.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(administratorDao.save(any(Administrator.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(buyerDao.save(any(Buyer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(postingDao.save(any(Posting.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(purchaseDao.save(any(Purchase.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	////////////////////////////
	//////Gallery tests/////////
	////////////////////////////
	
	@Test
	public void testCreateGallery() {
		assertEquals(0,galleryService.getAllGalleries().size());

		String name = "Gugenhiem";
		String city = "Bilbao";
		double commission = 0.1;
		Gallery gallery = null;
		try {
			gallery = galleryService.createGallery(name, city, commission);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(gallery);
		assertEquals(name, gallery.getName());
	}
	
	@Test
	public void testCreateGalleryNull() {
		String name = null;
		String error = null;
		Gallery gallery = null;
		try {
			gallery = galleryService.createGallery(name, "Montreal", 0.05);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(gallery);
		// check error
		assertEquals("Gallery name cannot be empty.", error);
	}
	
	@Test
	public void testCreateGalleryEmpty() {
		String name = "";
		String error = null;
		Gallery gallery = null;
		try {
			gallery = galleryService.createGallery(name, "Montreal", 0.05);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(gallery);
		// check error
		assertEquals("Gallery name cannot be empty.", error);
	}

	
	@Test
	public void testCreateGallerySpaces() {
		String city = " ";
		String error = null;
		Gallery gallery = null;
		try {
			gallery = galleryService.createGallery("Gallery", city, 0.05);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(gallery);
		// check error
		assertEquals("Gallery city cannot be empty.", error);
	}
	
	@Test
	public void testGetExistingGallery() {
		assertEquals(GALLERY_KEY, galleryService.getGallery(GALLERY_KEY).getName());
	}
	
	@Test
	public void testGetNonExistingGallery() {
		assertNull(galleryService.getGallery(NONEXISTING_GALLERY));
	}
	
	@Test
	public void testGetAllGalleries() {
	  String[] names = {"name1", "name2", "name3"};
      String[] cities = {"city1", "city2", "city3"};
      ArrayList<Gallery> galleryList = new ArrayList<Gallery>();
      ArrayList<Gallery> savedGalleries = new ArrayList<Gallery>();
      for (int i = 0; i < names.length; i++) {
          galleryList.add(galleryService.createGallery(names[i], cities[i], 0));
      }
      try {
          savedGalleries = (ArrayList<Gallery>) galleryService.getAllGalleries();
      } catch (IllegalArgumentException e) {
          // Check that no error occurred
          fail();
      }
      assertFalse(savedGalleries.size()==0);
      assertTrue(galleryList.equals(savedGalleries));
	}
	
	////////////////////////////
	//////Artists tests/////////
	////////////////////////////
	
	@Test
	public void testCreateArtist() {
		assertEquals(0, artistService.getAllArtists().size());

		String email = "bob@mail.com";
		String name = "bob";
		String password = "123";
		Gallery gallery = new Gallery();
		Artist artist = null;
		try {
			artist = artistService.createArtist(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(artist);
		assertEquals(name, artist.getName());
	}
	
	@Test
	public void testCreateArtistNull() {
		String email = "bob@mail.com";
		String name = "bob";
		String password = "123";
		String error = null;
		Gallery gallery = null;
		Artist artist = null;
		try {
			artist = artistService.createArtist(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(artist);
		// check error
		assertEquals("Artist gallery cannot be empty.", error);
	}
	
	@Test
	public void testCreateArtistEmpty() {
		String email = "";
		String name = "bob";
		String password = "123";
		String error = null;
		Gallery gallery = new Gallery();
		Artist artist = null;
		try {
			artist = artistService.createArtist(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(artist);
		// check error
		assertEquals("Artist email cannot be empty.", error);
	}
	
	@Test
	public void testCreateArtistSpaces() {
		String email = "  ";
		String name = "bob";
		String password = "123";
		String error = null;
		Gallery gallery = new Gallery();
		Artist artist = null;
		try {
			artist = artistService.createArtist(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(artist);
		// check error
		assertEquals("Artist email cannot be empty.", error);
	}
	
	@Test
	public void testGetExistingArtist() {
		assertEquals(ARTIST_KEY, artistService.getArtist(ARTIST_KEY).getEmail());
	}
	
	@Test
	public void testGetNonExistingArtist() {
		assertNull(artistService.getArtist(NONEXISTING_ARTIST));
	}
	
	@Test
	public void testGetAllArtists() {
	  String[] names = {"name1", "name2", "name3"};
      String[] cities = {"city1", "city2", "city3"};
      String[] email = {"a@mail.com", "b@mail.com", "c@mail.com"};
      String[] pwds = {"pwd1", "pwd2", "pwd3"};
      ArrayList<Artist> artistList = new ArrayList<Artist>();
      ArrayList<Artist> savedArtists = new ArrayList<Artist>();
      for (int i = 0; i < names.length; i++) {
          artistList.add(artistService.createArtist(email[i], names[i], pwds[i], galleryService.createGallery(names[i], cities[i], 0)));
      }
      try {
          savedArtists = (ArrayList<Artist>) artistService.getAllArtists();
      } catch (IllegalArgumentException e) {
          // Check that no error occurred
          fail();
      }
      assertFalse(savedArtists.size()==0);
      assertTrue(artistList.equals(savedArtists));
	}
	
	////////////////////////////
	//////Administrator tests///
	////////////////////////////
	
	@Test
	public void testCreateAdministrator() {
		assertEquals(0, artistService.getAllArtists().size());

		String email = "bob@mail.com";
		String name = "bob";
		String password = "123";
		Gallery gallery = new Gallery();
		Administrator administrator = null;
		try {
			administrator = adminService.createAdministrator(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(administrator);
		assertEquals(name, administrator.getName());
	}
	
	@Test
	public void testCreateAdministratortNull() {
		String email = "bob@mail.com";
		String name = "bob";
		String password = "123";
		String error = null;
		Gallery gallery = null;
		Administrator administrator = null;
		try {
			administrator = adminService.createAdministrator(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(administrator);
		// check error
		assertEquals("Administrator gallery cannot be empty.", error);
	}
	
	@Test
	public void testCreateAdministratorEmpty() {
		String email = "";
		String name = "bob";
		String password = "123";
		String error = null;
		Gallery gallery = new Gallery();
		Administrator administrator = null;
		try {
			administrator = adminService.createAdministrator(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(administrator);
		// check error
		assertEquals("Administrator email cannot be empty.", error);
	}
	
	@Test
	public void testCreateAdministratorSpaces() {
		String email = "  ";
		String name = "bob";
		String password = "123";
		String error = null;
		Gallery gallery = new Gallery();
		Administrator administrator = null;
		try {
			administrator = adminService.createAdministrator(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(administrator);
		// check error
		assertEquals("Administrator email cannot be empty.", error);
	}
	
	@Test
	public void testGetExistingAdministrator() {
		assertEquals(ADMINISTRATOR_KEY, adminService.getAdministrator(ADMINISTRATOR_KEY).getEmail());
	}
	
	@Test
	public void testGetNonExistingAdministrator() {
		assertNull(artistService.getArtist(NONEXISTING_ADMINISTRATOR));
	}
	
	@Test
	public void testGetAllAdministrators() {
	  String[] names = {"name1", "name2", "name3"};
      String[] cities = {"city1", "city2", "city3"};
      String[] email = {"a@mail.com", "b@mail.com", "c@mail.com"};
      String[] pwds = {"pwd1", "pwd2", "pwd3"};
      ArrayList<Administrator> administratorList = new ArrayList<Administrator>();
      ArrayList<Administrator> savedAdministrator = new ArrayList<Administrator>();
      for (int i = 0; i < names.length; i++) {
          administratorList.add(adminService.createAdministrator(email[i], names[i], pwds[i], galleryService.createGallery(names[i], cities[i], 0)));
      }
      try {
          savedAdministrator = (ArrayList<Administrator>) adminService.getAllAdministrators();
      } catch (IllegalArgumentException e) {
          // Check that no error occurred
          fail();
      }
      assertFalse(savedAdministrator.size()==0);
      assertTrue(administratorList.equals(savedAdministrator));
	}
	
	////////////////////////////
	//////Buyer tests///////////
	////////////////////////////
	
	@Test
	public void testCreateBuyer() {
		assertEquals(0, buyerService.getAllBuyers().size());

		String email = "bob@mail.com";
		String name = "bob";
		String password = "123";
		Gallery gallery = new Gallery();
		Buyer buyer = null;
		try {
			buyer = buyerService.createBuyer(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(buyer);
		assertEquals(name, buyer.getName());
	}
	
	@Test
	public void testCreateBuyerNull() {
		String email = "bob@mail.com";
		String name = "bob";
		String password = "123";
		String error = null;
		Gallery gallery = null;
		Buyer buyer = null;
		try {
			buyer = buyerService.createBuyer(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(buyer);
		// check error
		assertEquals("Buyer gallery cannot be empty.", error);
	}
	
	@Test
	public void testCreateBuyerEmpty() {
		String email = "";
		String name = "bob";
		String password = "123";
		String error = null;
		Gallery gallery = new Gallery();
		Buyer buyer = null;
		try {
			buyer = buyerService.createBuyer(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(buyer);
		// check error
		assertEquals("Buyer email cannot be empty.", error);
	}
	
	@Test
	public void testCreateBuyerSpaces() {
		String email = "  ";
		String name = "bob";
		String password = "123";
		String error = null;
		Gallery gallery = new Gallery();
		Buyer buyer = null;
		try {
			buyer = buyerService.createBuyer(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(buyer);
		// check error
		assertEquals("Buyer email cannot be empty.", error);
	}
	
	@Test
	public void testGetExistingBuyer() {
		assertEquals(BUYER_KEY, buyerService.getBuyer(BUYER_KEY).getEmail());
	}
	
	@Test
	public void testGetNonExistingBuyer() {
		assertNull(buyerService.getBuyer(NONEXISTING_BUYER));
	}
	
	@Test
	public void testGetAllBuyers() {
		
	}
	////////////////////////////
	//////Posting tests/////////
	////////////////////////////
	
	@Test
	public void testCreatePosting() {
		assertEquals(0, postingService.getAllPostings().size());
		
		int postingID = 984532;
		Gallery gallery = new Gallery();
		Artist artist = new Artist();
		gallery.addArtist(artist);
		int price = 100;
		double x = 1;
		double y = 1;
		double z = 1;
		String title  = "Moon";
		String description = "This is a moon";
		Date date = new Date(0);
		String url = "fakeimage";
		Posting posting = null;
		
		try {
			posting = postingService.createPosting(postingID, artist, price, x, y, z, title, description, date, url);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(posting);
		assertEquals(price, posting.getPrice());
	}
	
	@Test
	public void testCreatePostingNull() {
		
		int postingID = 984532;
		Artist artist = null;
		int price = 100;
		double x = 1;
		double y = 1;
		double z = 1;
		String title  = "Moon";
		String description = "This is a moon";
		Date date = new Date(0);
		String url = "fakeimage";
		Posting posting = null;
		String error = null;
		
		try {
			posting = postingService.createPosting(postingID, artist, price, x, y, z, title, description, date, url);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(error, "Posting artist cannot be empty.");
	}
	
	@Test
	public void testCreatePostingPriceLessThanZero() {
		
		int postingID = 984532;
		Gallery gallery = new Gallery();
		Artist artist = new Artist();
		gallery.addArtist(artist);
		int price = -100;
		double x = 1;
		double y = 1;
		double z = 1;
		String title  = "Moon";
		String description = "This is a moon";
		Date date = new Date(0);
		String url = "fakeimage";
		Posting posting = null;
		String error = null;
		
		try {
			posting = postingService.createPosting(postingID, artist, price, x, y, z, title, description, date, url);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(error, "Posting price must be above 0.");
	}
	
	@Test
	public void testGetExistingPosting() {
		assertEquals(POSTING_KEY, postingService.getPosting(POSTING_KEY).getPostingID());
	}
	
	@Test
	public void testGetNonExistingPosting() {
		assertNull(postingService.getPosting(NONEXISTING_POSTING));
	}
	
	@Test
	public void testDeleteExistingPosting() {
		
	}
	
	@Test
	public void testDeleteNonExistingPosting() {
		
	}
	
	@Test
	public void testGetAllPostings() {
		
	}
	
	////////////////////////////
	//////Purchase tests////////
	////////////////////////////
	
	@Test
	public void testCreatePurchase() {
		assertEquals(0, purchaseService.getAllPurchases().size());
		
		int purchaseID = 982423;
		Buyer buyer = new Buyer();
		Purchase purchase = null;
		
		try {
			purchase = purchaseService.createPurchase(purchaseID, buyer);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(purchase);
		assertEquals(purchaseID, purchase.getPurchaseID());
	}
	
	@Test
	public void testCreatePurchaseNull() {
		
		int purchaseID = 984532;
		Buyer buyer = null;
		Purchase purchase = null;
		String error = null;
		
		try {
			purchase = purchaseService.createPurchase(purchaseID, buyer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(purchase);
		assertEquals(error, "Purchase buyer cannot be empty.");
	}
	
	@Test
	public void testGetExistingPurchase() {
		assertEquals(PURCHASE_KEY, purchaseService.getPurchase(PURCHASE_KEY).getPurchaseID());
	}
	
	@Test
	public void testGetNonExistingPurchase() {
		assertNull(postingService.getPosting(NONEXISTING_PURCHASE));
	}
	
	@Test
	public void testGetAllPurchases() {
		
	}
	
	////////////////////////////
	///Purchase method tests////
	////////////////////////////
	
	@Test
	public void testMakePurchaseExistingPurchase() {
		
	}
	
	@Test
	public void testMakePurchaseNullPurchase() {
		
	}
	
	@Test
	public void testMakePurchaseNonExistingPurchase() {
		
	}
	
	@Test
	public void testMakePurchaseValidDeliveryType() {
		
	}
	
	@Test
	public void testMakePurchaseInvalidDeliveryTyper() {
		
	}
	
	@Test
	public void testMakePurchaseNullDeliveryType() {
		
	}
	
	@Test
	public void testCancelPurchaseExistingPurchase() {
		
	}
	
	@Test
	public void testCancelPurchaseNonExistingPurchase() {
		
	}
	
	@Test
	public void testCancelPurchaseNullPurchase() {
		
	}
	
	////////////////////////////
	////////Action tests////////
	////////////////////////////
	
	@Test
	public void testClearDatabase() {
		
	}
	
	////////////////////////////
	/////////Cart tests/////////
	////////////////////////////
	
	@Test
	public void testAddToCartExistingBuyer() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = buyerService.createBuyer("gregory.walfish@mail.mcgill.ca", "Gregory", "youthought", gallery);
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Date date = new Date(0);
		Posting posting = postingService.createPosting(1, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
		Purchase purchase = purchaseService.addToCart(buyer, posting);
		assertEquals(buyer.getCart().getPurchaseID(), purchase.getPurchaseID() );
	
	}
	
	@Test
	public void testAddToCartNullBuyer() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = null;
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Date date = new Date(0);
		Posting posting = postingService.createPosting(1, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
		String error = null;
		Purchase purchase = new Purchase();
		try {
			purchase = purchaseService.addToCart(buyer, posting);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "addToCart buyer cannot be empty.");
		
	}
	
	@Test
	public void testAddToCartNonExistingBuyer() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = buyerService.getBuyer(NONEXISTING_BUYER);
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Date date = new Date(0);
		Posting posting = postingService.createPosting(1, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
		String error = null;
		Purchase purchase = new Purchase();
		try {
			purchase = purchaseService.addToCart(buyer, posting);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "addToCart buyer cannot be empty.");
		
	}
	
	@Test
	public void testAddToCartExistingPosting() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = buyerService.createBuyer("gregory.walfish@mail.mcgill.ca", "Gregory", "youthought", gallery);
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Date date = new Date(0);
		Posting posting = postingService.getPosting(POSTING_KEY);
		String error = null;
		Purchase purchase = new Purchase();
		try {
			purchase = purchaseService.addToCart(buyer, posting);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "addToCart posting cannot be On Hold or Purchased.");
		
	}
	
	@Test
	public void testAddToCartNullPosting() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = buyerService.createBuyer("gregory.walfish@mail.mcgill.ca", "Gregory", "youthought", gallery);
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Posting posting = null;
		String error = null;
		Purchase purchase = new Purchase();
		try {
			purchase = purchaseService.addToCart(buyer, posting);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "addToCart posting cannot be empty. ");
		
	}
	
	@Test
	public void testAddToCartNonExistingPosting() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = buyerService.createBuyer("gregory.walfish@mail.mcgill.ca", "Gregory", "youthought", gallery);
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Posting posting = postingService.getPosting(NONEXISTING_POSTING);
		String error = null;
		Purchase purchase = new Purchase();
		try {
			purchase = purchaseService.addToCart(buyer, posting);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "addToCart posting cannot be empty. ");
		
	}
	
	@Test
	public void testRemoveFromCartExistingBuyer() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = buyerService.createBuyer("gregory.walfish@mail.mcgill.ca", "Gregory", "youthought", gallery);
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Date date = new Date(0);
		Posting posting = postingService.createPosting(1, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
		String error = null;
		
		Purchase purchase = purchaseService.addToCart(buyer, posting);
		purchase = purchaseService.removeFromCart(buyer, posting);
		assertEquals(purchase.getPostings(), buyer.getCart().getPostings());
		
	}
	
	@Test
	public void testRemoveFromCartNullBuyer() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = null;
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Date date = new Date(0);
		Posting posting = postingService.createPosting(1, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
		String error = null;
		Purchase purchase = new Purchase();
		try {
			purchase = purchaseService.removeFromCart(buyer, posting);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "removeFromCart buyer cannot be empty. ");
		
	}
	
	@Test
	public void testRemoveFromCartNonExistingBuyer() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = buyerService.getBuyer(NONEXISTING_BUYER);
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Date date = new Date(0);
		Posting posting = postingService.createPosting(1, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
		String error = null;
		Purchase purchase = new Purchase();
		try {
			purchase = purchaseService.removeFromCart(buyer, posting);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "removeFromCart buyer cannot be empty. ");
		
	}
	
	@Test
	public void testRemoveFromCartExistingPosting() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = buyerService.createBuyer("gregory.walfish@mail.mcgill.ca", "Gregory", "youthought", gallery);
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Date date = new Date(0);
		Posting posting = postingService.createPosting(1, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
		String error = null;
		Purchase purchase = purchaseService.addToCart(buyer, posting);
		purchase = purchaseService.removeFromCart(buyer, posting);
		assertEquals(posting.getArtStatus(), ArtStatus.Available);
		assertEquals(purchase.getPostings(), buyer.getCart().getPostings());
		
	}
	
	@Test
	public void testRemoveFromCartNullPosting() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = buyerService.createBuyer("gregory.walfish@mail.mcgill.ca", "Gregory", "youthought", gallery);
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Date date = new Date(0);
		Posting posting = null;
		Posting postingTwo = postingService.createPosting(1, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
		String error = null;
		Purchase purchase = purchaseService.addToCart(buyer, postingTwo);
		try {
			purchase = purchaseService.removeFromCart(buyer, posting);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "removeFromCart posting cannot be empty.");
		
		
	}
	
	@Test
	public void testRemoveFromCartNonExistingPosting() {
		Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
		Buyer buyer = buyerService.createBuyer("gregory.walfish@mail.mcgill.ca", "Gregory", "youthought", gallery);
		Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
		Date date = new Date(0);
		Posting posting = postingService.getPosting(NONEXISTING_POSTING);
		Posting postingTwo = postingService.createPosting(1, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
		
		String error = null;
		Purchase purchase = purchaseService.addToCart(buyer, postingTwo);
		try {
			purchase = purchaseService.removeFromCart(buyer, posting);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "removeFromCart posting cannot be empty.");
		
	}
	
	////////////////////////////
	////Private helper tests////
	////////////////////////////
	
	@Test
	public void testConvertToModelExistingPurchaseDto() {
		
	}
	
	@Test
	public void testConvertToModelNonExistingPurchaseDto() {
		
	}
	
	@Test
	public void testConvertToModelNullPurchaseDto() {
		
	}
	
	@Test
	public void testConvertToModelExistingAdministratorDto() {
		
	}
	
	@Test
	public void testConvertToModelNonExistingAdministratorDto() {
		
	}
	
	@Test
	public void testConvertToModelNullAdministratorDto() {
		
	}
	
	@Test
	public void testConvertToModelExistingBuyerDto() {
		
	}
	
	@Test
	public void testConvertToModelNonExistingBuyerDto() {
		
	}
	
	@Test
	public void testConvertToModelNullBuyerDto() {
		
	}
	
	@Test
	public void testConvertToModelExistingPostingDto() {
		
	}
	
	@Test
	public void testConvertToModelNonExistingPostingDto() {
		
	}
	
	@Test
	public void testConvertToModelNullPostingDto() {
		
	}
	
	@Test
	public void testConvertToModelExistingArtistDto() {
		
	}
	
	@Test
	public void testConvertToModelNonExistingArtistDto() {
		
	}
	
	@Test
	public void testConvertToModelNullArtistDto() {
		
	}
	
	@Test
	public void testConvertToModelExistingGalleryDto() {
		
	}
	
	@Test
	public void testConvertToModelNonExistingGalleryDto() {
		
	}
	
	@Test
	public void testConvertToModelNullGalleryDto() {
		
	}
	
	@Test
	public void testGeneratePurchaseIDNoDuplicates() {
		
	}
	
	@Test
	public void testGeneratePostingIDNoDuplicates() {
		
	}
	
	@Test
	public void testCalculateFinalPriceExistingPurchase() {
		
	}
	
	@Test
	public void testCalculateFinalPriceNonExistingPurchase() {
		
	}
	
	@Test
	public void testCalculateFinalPriceNullPurchase() {
		
	}
}