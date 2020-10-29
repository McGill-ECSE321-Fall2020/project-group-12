package ca.mcgill.ecse321.smartart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ca.mcgill.ecse321.smartart.dto.BuyerDto;
import ca.mcgill.ecse321.smartart.dto.PurchaseDto;
import ca.mcgill.ecse321.smartart.model.DeliveryType;
import ca.mcgill.ecse321.smartart.model.Purchase;
import ca.mcgill.ecse321.smartart.service.PurchaseService;

public class PurchaseRestController {
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private RestControllerHelper controllerHelper;
	
	@GetMapping(value = {"/purchases", "/puchases/" })
	public ResponseEntity<?> getAllPurchases(){
		List<PurchaseDto> purchases = purchaseService.getAllPurchases().stream().map(p -> controllerHelper.convertToDto(p)).collect(Collectors.toList());
		return new ResponseEntity<>(purchases, HttpStatus.OK);
	}
	
	@GetMapping(value = { "/purchases/{purchaseID}", "/purchases/{purchaseID}/" })
	public ResponseEntity<?> getPurchaseByPurchaseID(@PathVariable("purchaseID") int purchaseID) {
		Purchase purchase = purchaseService.getPurchase(purchaseID);
		if (purchase != null) {
			return new ResponseEntity<>(controllerHelper.convertToDto(purchase), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value = { "/purchases/buyer/{email}", "/purchases/buyer/{email}/" })
	public ResponseEntity<?> getPurchasesByBuyer(@PathVariable("email") String email) {
		try {
			List<PurchaseDto> purchases = purchaseService.getPurchasesByBuyer(email).stream().map(p -> controllerHelper.convertToDto(p)).collect(Collectors.toList());
			return new ResponseEntity<>(purchases, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = { "/purchases/cart/{email}", "/purchases/cart/{email}/" })
	public ResponseEntity<?> getCart(@PathVariable("email") String email) {
		Purchase cart = purchaseService.getCart(email);
		try {
			if(cart != null)
				return new ResponseEntity<>(controllerHelper.convertToDto(cart), HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = {"/purchase/create", "/purchase/create/" })
	public ResponseEntity<?> createPurchase(@RequestBody PurchaseDto data) {
		try {
			Purchase purchase = purchaseService.createPurchase(data);
			return new ResponseEntity<>(controllerHelper.convertToDto(purchase), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = {"/purchase/make/{deliveryType}", "/purchase/make/{deliveryType}/"})
	public ResponseEntity<?> makePurchase(@RequestBody PurchaseDto data, @PathVariable("deliveryType") DeliveryType deliveryType) {
		try {
			Purchase purchase = purchaseService.makePurchase(data, deliveryType);
			return new ResponseEntity<>(controllerHelper.convertToDto(purchase), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = {"/purchase/cancel", "/purchase/cancel/"})
	public ResponseEntity<?> cancelPurchase(@RequestBody PurchaseDto data) {
		try {
			boolean canceled = purchaseService.cancelPurchase(data);
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
			Purchase cart = purchaseService.addToCart(buyerData, postingID);
			return new ResponseEntity<>(controllerHelper.convertToDto(cart), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
	}
	
	@DeleteMapping(value = {"/purchase/cart/remove/{postingID}", "/purchase/cart/remove/{postingID}/"})
	public ResponseEntity<?> removeFromCart(@RequestBody BuyerDto buyerData, @PathVariable (name = "postingID") int postingID) {
		try {
			Purchase cart = purchaseService.removeFromCart(buyerData, postingID);
			return new ResponseEntity<>(controllerHelper.convertToDto(cart), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
