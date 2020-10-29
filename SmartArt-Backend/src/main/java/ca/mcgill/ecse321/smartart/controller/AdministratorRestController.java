package ca.mcgill.ecse321.smartart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartart.dto.AdministratorDto;
import ca.mcgill.ecse321.smartart.model.Administrator;
import ca.mcgill.ecse321.smartart.service.AdministratorService;

@CrossOrigin(origins = "*")
@RestController
public class AdministratorRestController {
	@Autowired
	private AdministratorService adminService;
	@Autowired
	private RestControllerHelper controllerHelper;
	
	@GetMapping(value = {"/administrators", "/administrators/"})
	public ResponseEntity<?> getAllAdministrators(){
		return new ResponseEntity<>(adminService.getAllAdministrators().stream().map(a -> controllerHelper.convertToDto(a)).collect(Collectors.toList()), HttpStatus.OK);
	}
	@GetMapping(value = { "/administrators/{email}", "/administrators/{email}/" })
	public ResponseEntity<?> getAdministratorByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		try {
			Administrator administrator = adminService.getAdministrator(email);
			return new ResponseEntity<>(controllerHelper.convertToDto(administrator), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = { "/administrator/create", "/administrator/create/"})
	public ResponseEntity<?> createAdministrator(@RequestBody AdministratorDto data) throws IllegalArgumentException{
		try {
			Administrator administrator = adminService.createAdministrator(data);
			return new ResponseEntity<>(controllerHelper.convertToDto(administrator), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}
}
