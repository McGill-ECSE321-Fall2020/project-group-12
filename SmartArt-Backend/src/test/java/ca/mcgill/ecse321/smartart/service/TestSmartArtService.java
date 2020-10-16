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
	
}