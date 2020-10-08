package ca.mcgill.ecse321.smartart.model;


import javax.persistence.OneToOne;
import javax.persistence.Entity;

/**
 * 
 * @author Group 12
 * Enumeration that states whether an art is purchased, on hold,
 * or available for purchase.
 */
public enum ArtStatus {
	Purchased, OnHold, Available
}
