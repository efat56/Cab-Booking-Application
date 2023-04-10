package com.masai.service;

import java.util.ArrayList;
import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.masai.repository.CabDao;
import com.masai.repository.CustomerDao;
import com.masai.repository.DriverDao;
import com.masai.repository.SessionDao;

import com.masai.exceptions.CustomerException;
import com.masai.exceptions.DriverException;
import com.masai.model.CabDetails;
import com.masai.model.CurrentUserSession;
import com.masai.model.Customer;
import com.masai.model.DriverDetails;
import com.masai.model.DriverDTO;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao cusDao;
	
	@Autowired
	private SessionDao sessDao;
	
	@Autowired
	private DriverDao drivedao;
	
	@Autowired
	private CabDao cabDao;
	
	
	@Override
	public Customer createCustomer(Customer customer)throws CustomerException {
		
		
		Customer existingCustomer= cusDao.findByMobileNumber(customer.getMobileNumber());
		
	 
		
		if(existingCustomer != null) 
			throw new CustomerException("Customer Already Registered with This Mobile !");
			
		
		
		
			return cusDao.save(customer);
			
			
		}


	@Override
	public List<DriverDetails> getAllRide(String key, Integer customerId,String source, String destination ) throws DriverException {
		// TODO Auto-generated method stub
      CurrentUserSession loggedInUser= sessDao.findByUuid(key);
		
		if(loggedInUser == null) {
			throw new DriverException("Please Provide a valid key to get all drivers Details!");
		}
		
		
		if(customerId == loggedInUser.getUserId()) {
			
			Customer cust = cusDao.findById(customerId).get();
			
			if(cust == null) throw new DriverException("Customer Not Found..!!");
			List<DriverDetails> driver = drivedao.findAll();
			
			List<DriverDetails> nearBy = new ArrayList<>();
			
			
			int [] userp = cust.getCurrentPosition();
			
			for(DriverDetails i: driver) {
				int [] driverp = i.getCurrentPosition();
				int dis = (int) Math.pow((Math.pow(Math.abs(driverp[0]-userp[0]),2)+Math.pow(Math.abs(driverp[1]-userp[1]),2)), 0.5);
				if(dis <= 5 && i.getAvailablity() == true) {
					nearBy.add(i);
				}
			}
		
			
			return nearBy;
			
		}else {
			throw new DriverException("Invalid Driver Details, Please Do login First");
		}
		
	}


	@Override
	public String book(String key, Integer customerId, Integer driverId,Integer x,Integer y) throws DriverException {
		// TODO Auto-generated method stub
		 CurrentUserSession loggedInUser= sessDao.findByUuid(key);
			
			if(loggedInUser == null) {
				throw new DriverException("Please provide a valid key to get all Drivers Details!");
			}
			
			
			if(customerId == loggedInUser.getUserId()) {
				Customer customer = cusDao.findByCustomerId(customerId);
				DriverDetails driver = drivedao.findById(driverId).get();
				if(driver == null) {
					throw new DriverException("Driver Doesn't Exist With This Id : "+ driverId);
				}
				CabDetails cab = driver.getCab();
				cab.getCustomers().add(customer);
				driver.getCurrentPosition()[0] = x;
				driver.getCurrentPosition()[1] = y;
				
				customer.getCurrentPosition()[0] = x;
				customer.getCurrentPosition()[1] = y;
				cusDao.save(customer);
				drivedao.save(driver);
				cabDao.save(cab);
				return "Cab is Booked Successfully !";
				
			}else {
				throw new DriverException("Invalid Driver Details, please Do login First!");
			}
		
		
		
		
	}

	
	
	}


