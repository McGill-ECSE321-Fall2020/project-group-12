package ca.mcgill.ecse321.smartart.model;


import javax.persistence.OneToOne;
import javax.persistence.Entity;

/**
 * 
 * @author Group 12
 * Enumeration that states whether the art piece shall be delivered
 * by pickup in person or by shipping. 
 */
public enum DeliveryType {
	PickUp, Shipped
}
