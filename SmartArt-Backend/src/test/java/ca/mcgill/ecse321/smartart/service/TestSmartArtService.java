package ca.mcgill.ecse321.smartart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;

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
	private SmartArtService service;
	
	private static final String GALLERY_KEY = "TestGallery";
	private static final String NONEXISTING_GALLERY = "NotAGallery";
	private static final String ARTIST_KEY = "TestArtist";
	private static final String NONEXISTING_ARTIST = "NotAnArtist";
	private static final String ADMINISTRATOR_KEY = "TestAdministrator";
	private static final String NONEXISTING_ADMINISTRATOR = "NotAnAdministrator";
	private static final String BUYER_KEY = "TestBuyer";
	private static final String NONEXISTING_BUYER = "NotABuyer";
	private static final String POSTING_KEY = "TestPosting";
	private static final String NONEXISTING_POSTING = "NotAPosting";
	private static final String PURCHASE_KEY = "TestPurchase";
	private static final String NONEXISTING_PURCHASE = "NotAPurchase";
	
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
		assertEquals(0, service.getAllGalleries().size());

		String name = "Gugenhiem";
		String city = "Bilbao";
		double commission = 0.1;
		Gallery gallery = null;
		try {
			gallery = service.createGallery(name, city, commission);
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
			gallery = service.createGallery(name, "Montreal", 0.05);
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
			gallery = service.createGallery(name, "Montreal", 0.05);
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
			gallery = service.createGallery("Gallery", city, 0.05);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(gallery);
		// check error
		assertEquals("Gallery city cannot be empty.", error);
	}
	
	@Test
	public void testGetExistingGallery() {
		assertEquals(GALLERY_KEY, service.getGallery(GALLERY_KEY).getName());
	}
	
	@Test
	public void testGetNonExistingGallery() {
		assertNull(service.getGallery(NONEXISTING_GALLERY));
	}
	
	////////////////////////////
	//////Artists tests/////////
	////////////////////////////
	
	@Test
	public void testCreateArtist() {
		assertEquals(0, service.getAllArtists().size());

		String email = "bob@mail.com";
		String name = "bob";
		String password = "123";
		Gallery gallery = new Gallery();
		Artist artist = null;
		try {
			artist = service.createArtist(email, name, password, gallery);
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
			artist = service.createArtist(email, name, password, gallery);
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
			artist = service.createArtist(email, name, password, gallery);
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
			artist = service.createArtist(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(artist);
		// check error
		assertEquals("Artist email cannot be empty.", error);
	}
	
	@Test
	public void testGetExistingArtist() {
		assertEquals(ARTIST_KEY, service.getArtist(ARTIST_KEY).getName());
	}
	
	@Test
	public void testGetNonExistingArtist() {
		assertNull(service.getArtist(NONEXISTING_ARTIST));
	}
	
	////////////////////////////
	//////Administrator tests///
	////////////////////////////
	
	@Test
	public void testCreateAdministrator() {
		assertEquals(0, service.getAllArtists().size());

		String email = "bob@mail.com";
		String name = "bob";
		String password = "123";
		Gallery gallery = new Gallery();
		Administrator administrator = null;
		try {
			administrator = service.createAdministrator(email, name, password, gallery);
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
			administrator = service.createAdministrator(email, name, password, gallery);
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
			administrator = service.createAdministrator(email, name, password, gallery);
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
			administrator = service.createAdministrator(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(administrator);
		// check error
		assertEquals("Administrator email cannot be empty.", error);
	}
	
	
	////////////////////////////
	//////Buyer tests///////////
	////////////////////////////
	
	@Test
	public void testCreateBuyer() {
		assertEquals(0, service.getAllBuyers().size());

		String email = "bob@mail.com";
		String name = "bob";
		String password = "123";
		Gallery gallery = new Gallery();
		Buyer buyer = null;
		try {
			buyer = service.createBuyer(email, name, password, gallery);
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
			buyer = service.createBuyer(email, name, password, gallery);
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
			buyer = service.createBuyer(email, name, password, gallery);
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
			buyer = service.createBuyer(email, name, password, gallery);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(buyer);
		// check error
		assertEquals("Buyer email cannot be empty.", error);
	}
	
	
	////////////////////////////
	//////Posting tests/////////
	////////////////////////////
	
	@Test
	public void testCreatePosting() {
		assertEquals(0, service.getAllPostings().size());
		
		int postingID = 984532;
		Gallery gallery = new Gallery();
		Artist artist = new Artist();
		gallery.addArtist(artist);
		double price = 100;
		double x = 1;
		double y = 1;
		double z = 1;
		String title  = "Moon";
		String description = "This is a moon";
		Date date = new Date(0);
		Posting posting = null;
		
		try {
			posting = service.createPosting(postingID, artist, price, x, y, z, title, description, date);
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
		double price = 100;
		double x = 1;
		double y = 1;
		double z = 1;
		String title  = "Moon";
		String description = "This is a moon";
		Date date = new Date(0);
		Posting posting = null;
		String error = null;
		
		try {
			posting = service.createPosting(postingID, artist, price, x, y, z, title, description, date);
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
		double price = -100;
		double x = 1;
		double y = 1;
		double z = 1;
		String title  = "Moon";
		String description = "This is a moon";
		Date date = new Date(0);
		Posting posting = null;
		String error = null;
		
		try {
			posting = service.createPosting(postingID, artist, price, x, y, z, title, description, date);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(error, "Posting price must be above 0.");
	}
	
	////////////////////////////
	//////Purchase tests////////
	////////////////////////////
	
	@Test
	public void testCreatePurchase() {
		assertEquals(0, service.getAllPurchases().size());
		
		int purchaseID = 982423;
		Buyer buyer = new Buyer();
		Purchase purchase = null;
		
		try {
			purchase = service.createPurchase(purchaseID, buyer);
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
			purchase = service.createPurchase(purchaseID, buyer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(purchase);
		assertEquals(error, "Purchase buyer cannot be empty.");
	}
	
	////////////////////////////
	//////Action tests////////
	////////////////////////////
	
}