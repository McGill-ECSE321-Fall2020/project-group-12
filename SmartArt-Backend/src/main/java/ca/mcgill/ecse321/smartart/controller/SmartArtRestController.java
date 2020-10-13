package ca.mcgill.ecse321.smartart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartart.service.SmartArtService;

@CrossOrigin(origins = "*")
@RestController
public class SmartArtRestController {

	@Autowired
	private SmartArtService service;

}
