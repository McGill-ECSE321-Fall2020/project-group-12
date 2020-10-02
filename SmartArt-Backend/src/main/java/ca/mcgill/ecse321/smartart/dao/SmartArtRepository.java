package ca.mcgill.ecse321.smartart.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartart.model.*;

@Repository
public class SmartArtRepository {

	@Autowired
	EntityManager entityManager;



}