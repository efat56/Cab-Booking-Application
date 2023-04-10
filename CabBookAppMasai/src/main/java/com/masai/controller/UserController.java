package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.CustomerException;
import com.masai.exceptions.DriverException;
import com.masai.model.CabDetails;
import com.masai.model.Customer;
import com.masai.model.DriverDetails;
import com.masai.service.CustomerService;

@RestController
@RequestMapping("/masaicab/user")
public class UserController {
	
	@Autowired
	private CustomerService cService;
	
    @PostMapping("/register")
	private ResponseEntity<Customer> createUser(Customer customer) throws CustomerException {
	
    Customer cust =	cService.createCustomer(customer);
    return new ResponseEntity<>(cust,HttpStatus.CREATED);
		
	}
    @GetMapping("/findride")
    private ResponseEntity<List<DriverDetails>> getAllDriver(@RequestParam String key, @RequestParam Integer Customerid,@RequestParam String source,@RequestParam String destination) throws DriverException{
    	List<DriverDetails> drivers = cService.getAllRide(key, Customerid, source, destination);
    	return new ResponseEntity<List<DriverDetails>>(drivers,HttpStatus.OK);
    }
    @GetMapping("/book/{driverId}/{x}/{y}")
	private ResponseEntity<String> Book(@RequestParam String key, @RequestParam Integer customerId, @RequestParam Integer driverId,@RequestParam Integer x , @RequestParam Integer y) throws DriverException{
    	String str = cService.book(key, customerId, driverId,x,y);
    	
    	
    	return new ResponseEntity<>(str,HttpStatus.OK);
    	
    }
    
}
 