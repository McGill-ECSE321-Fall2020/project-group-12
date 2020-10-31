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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.time.LocalDateTime;

import ca.mcgill.ecse321.smartart.model.*;
import ca.mcgill.ecse321.smartart.dao.*;


@ExtendWith(MockitoExtension.class)
public class PurchaseMethodAndCartTest {
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
  private static final String ARTIST_KEY = "TestArtist";
  private static final String ADMINISTRATOR_KEY = "TestAdministrator";
  private static final String BUYER_KEY = "TestBuyer";
  private static final String NONEXISTING_BUYER = "NotABuyer";
  private static final int POSTING_KEY = 100;
  private static final int NONEXISTING_POSTING = -1;
  private static final int PURCHASE_KEY = 200;
  
  @BeforeEach
  public void setMockOutput() {
      lenient().when(galleryDao.findGalleryByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(GALLERY_KEY)) {
              Gallery gallery = new Gallery();
              gallery.setName(GALLERY_KEY);
              galleryDao.save(gallery);
              return gallery;
          } else {
              return null;
          }
      });
      lenient().when(artistDao.findArtistByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(ARTIST_KEY)) {
              Artist artist = new Artist();
              artist.setEmail(ARTIST_KEY);
              artistDao.save(artist);
              return artist;
          } else {
              return null;
          }
      });
      lenient().when(administratorDao.findAdministratorByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(ADMINISTRATOR_KEY)) {
              Administrator administrator = new Administrator();
              administrator.setEmail(ADMINISTRATOR_KEY);
              administratorDao.save(administrator);
              return administrator;
          } else {
              return null;
          }
      });
      lenient().when(buyerDao.findBuyerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(BUYER_KEY)) {
              Buyer buyer = new Buyer();
              buyer.setEmail(BUYER_KEY);
              buyerDao.save(buyer);
              return buyer;
          } else {
              return null;
          }
      });
      lenient().when(postingDao.findPostingByPostingID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(POSTING_KEY)) {
              Posting posting = new Posting();
              posting.setPostingID(POSTING_KEY);
              postingDao.save(posting);
              return posting;
          } else {
              return null;
          }
      });
      lenient().when(purchaseDao.findPurchaseByPurchaseID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(PURCHASE_KEY)) {
              Purchase purchase = new Purchase();
              purchase.setPurchaseID(PURCHASE_KEY);
              purchaseDao.save(purchase);
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
  ///Purchase method tests////
  ////////////////////////////
  
  @Test
  public void testMakePurchaseExistingPurchase() {
      Purchase cart = new Purchase();
      Buyer buyer = buyerDao.findBuyerByEmail(BUYER_KEY);
      Gallery gallery = galleryDao.findGalleryByName(GALLERY_KEY);
      buyer.setGallery(gallery);
      buyer.setCart(cart);
      cart.setPurchaseID(124344);
      LocalDateTime now = LocalDateTime.now();
      cart.setTime(now);
      cart.addPosting(postingDao.findPostingByPostingID(POSTING_KEY));
      cart.setTotalPrice(100);
      int finalPrice = (int)(cart.getTotalPrice() + (1*gallery.getCommission()));
      Purchase purchase = purchaseService.makePurchase(cart, DeliveryType.PickUp);
      
      assertEquals(DeliveryType.PickUp, purchase.getDeliveryType());
      assertEquals(purchase.getTotalPrice(), finalPrice);
      assertNotNull(buyer.getPurchases());
      assertNull(buyer.getCart());
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
  /////////Cart tests/////////
  ////////////////////////////
  
  @Test
  public void testAddToCartExistingBuyer() {
      Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
      Buyer buyer = buyerService.createBuyer("gregory.walfish@mail.mcgill.ca", "Gregory", "youthought", gallery);
      Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
      Date date = new Date(0);
      Posting posting = postingService.createPosting(124344, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
      Purchase purchase = purchaseService.addToCart(buyer, posting);
      assertEquals(buyer.getCart().getPurchaseID(), purchase.getPurchaseID() );
  
  }
  
  @Test
  public void testAddToCartNullBuyer() {
      Gallery gallery = galleryService.createGallery("bellefeuile", "montreal", 0.4);
      Buyer buyer = null;
      Artist artist = artistService.createArtist("aidan.williams@mail.mcgill.ca", "Aidan", "gregismyidol", gallery);
      Date date = new Date(0);
      Posting posting = postingService.createPosting(124344, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
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
      Posting posting = postingService.createPosting(124344, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
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
      Posting posting = postingService.createPosting(124344, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
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
      Posting posting = postingService.createPosting(124344, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
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
      Posting posting = postingService.createPosting(124344, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
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
      Posting posting = postingService.createPosting(124344, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
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
      Posting postingTwo = postingService.createPosting(124344, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
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
      Posting postingTwo = postingService.createPosting(124344, artist, 1000, 1, 1, 1, "Mona Lisa", "copy of it", date, "image");
      
      String error = null;
      Purchase purchase = purchaseService.addToCart(buyer, postingTwo);
      try {
          purchase = purchaseService.removeFromCart(buyer, posting);
      } catch (IllegalArgumentException e) {
          error = e.getMessage();
      }
      assertEquals(error, "removeFromCart posting cannot be empty.");
      
  }
}
