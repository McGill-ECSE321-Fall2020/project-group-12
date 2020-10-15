package ca.mcgill.ecse321.smartart.dto;

import java.util.Collections;
import java.util.List;

public class PurchaseDto {

	private int purchaseID;
	private BuyerDto buyer;
	private double totalPrice;
	private List<PostingDto> postings;
	private DeliveryTypeDto deliveryType;

	public PurchaseDto() {
	}
	
	public PurchaseDto(int purchaseID, BuyerDto buyer) {
		this(purchaseID, buyer, 0, Collections.emptyList(), null);
	}
	
	public PurchaseDto(int purchaseID, BuyerDto buyer, double totalPrice, List<PostingDto> postings, DeliveryTypeDto deliveryType) {
		this.purchaseID = purchaseID;
		this.buyer = buyer;
		this.totalPrice = totalPrice;
		this.postings = postings;
		this.deliveryType = deliveryType;
	}
	
	public List<PostingDto> getPostings() {
		return this.postings;
	}

	public void setPostings(List<PostingDto> postings) {
		this.postings = postings;
	}

	public BuyerDto getBuyer() {
		return this.buyer;
	}

	public void setBuyer(BuyerDto buyer) {
		this.buyer = buyer;
	}

	public void setTotalPrice(double value) {
		this.totalPrice = value;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public void setPurchaseID(int value) {
		this.purchaseID = value;
	}

	public int getPurchaseID() {
		return this.purchaseID;
	}

	public void setDeliveryType(DeliveryTypeDto deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	public DeliveryTypeDto getDeliveryType() {
		return this.deliveryType;
	}
	
}
