package ca.mcgill.ecse321.smartart.controller;

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
    public ResponseEntity<?> getAllBuyers() {
        return new ResponseEntity<>(buyerService.getAllBuyers().stream().map(b -> controllerHelper.convertToDto(b)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = {"/buyers/{email}", "/buyers/{email}/"})
    public ResponseEntity<?> getBuyerByEmail(@PathVariable("email") String email) throws IllegalArgumentException {
        try {
            Buyer buyer = buyerService.getBuyer(email);
            return new ResponseEntity<>(controllerHelper.convertToDto(buyer), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = {"/buyer/create", "/buyer/create/"})
    public ResponseEntity<?> createBuyer(@RequestBody BuyerDto data) throws IllegalArgumentException {
        try {
            Buyer buyer = buyerService.createBuyer(data);
            return new ResponseEntity<>(controllerHelper.convertToDto(buyer), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
