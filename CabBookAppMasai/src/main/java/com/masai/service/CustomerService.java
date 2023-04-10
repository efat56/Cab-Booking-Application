package com.masai.service;

import java.util.List;

import com.masai.exceptions.CustomerException;
import com.masai.exceptions.DriverException;
import com.masai.model.CabDetails;
import com.masai.model.Customer;
import com.masai.model.DriverDetails;

public interface CustomerService {
	
	
	public Customer createCustomer(Customer customer)throws CustomerException;
	
	public List<DriverDetails> getAllRide(String key,Integer customerId,String source,String Destination) throws DriverException;
	
	public String book(String key,Integer customerId,Integer driverId,Integer x,Integer y) throws DriverException;
	
	

}
