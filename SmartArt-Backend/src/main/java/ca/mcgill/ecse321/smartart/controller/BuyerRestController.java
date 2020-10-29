package ca.mcgill.ecse321.smartart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartart.dto.BuyerDto;
import ca.mcgill.ecse321.smartart.model.Buyer;
import ca.mcgill.ecse321.smartart.service.BuyerService;

@CrossOrigin(origins = "*")
@RestController
public class BuyerRestController {
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private RestControllerHelper controllerHelper;
	
	@GetMapping(value = {"/buyers", "/buyers/"})
	public List<BuyerDto> getAllBuyers(){
		return buyerService.getAllBuyers().stream().map(b -> controllerHelper.convertToDto(b)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/buyers/{email}", "/buyers/{email}/" })
	public BuyerDto getBuyerByEmail(@PathVariable("email") String email)  throws IllegalArgumentException{
		return controllerHelper.convertToDto(buyerService.getBuyer(email));
	}
	
	@PostMapping(value = { "/buyer/create", "/buyer/create/" })
	public BuyerDto createBuyer(@RequestBody BuyerDto data) throws IllegalArgumentException {
		Buyer buyer = buyerService.createBuyer(data);
		return controllerHelper.convertToDto(buyer);
	}
}
