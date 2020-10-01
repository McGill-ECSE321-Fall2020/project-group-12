package ca.mcgill.ecse321.smartart.model;

import javax.persistence.OneToOne;
import javax.persistence.Entity;

@Entity
public enum DeliveryType {
	PickUp, Shipped
}
