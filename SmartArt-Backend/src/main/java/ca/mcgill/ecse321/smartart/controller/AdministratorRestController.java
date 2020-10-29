package ca.mcgill.ecse321.smartart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ca.mcgill.ecse321.smartart.dto.AdministratorDto;
import ca.mcgill.ecse321.smartart.model.Administrator;
import ca.mcgill.ecse321.smartart.service.AdministratorService;

public class AdministratorRestController {
	@Autowired
	private AdministratorService adminService;
	@Autowired
	private RestControllerHelper controllerHelper;
	
	@GetMapping(value = {"/administrators", "/administrators/"})
	public List<AdministratorDto> getAllAdministrators(){
		return adminService.getAllAdministrators().stream().map(a -> controllerHelper.convertToDto(a)).collect(Collectors.toList());
	}
	@GetMapping(value = { "/administrators/{email}", "/administrators/{email}/" })
	public AdministratorDto getAdministratorByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return controllerHelper.convertToDto(adminService.getAdministrator(email));
	}
	
	@PostMapping(value = { "/administrator/create", "/administrator/create/"})
	public AdministratorDto createAdministrator(@RequestBody AdministratorDto data) throws IllegalArgumentException{
		Administrator administrator = adminService.createAdministrator(data);
		return controllerHelper.convertToDto(administrator);
	}
}
