package com.masai.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exceptions.LoginException;
import com.masai.model.CurrentUserSession;
import com.masai.model.Customer;
import com.masai.model.DriverDetails;
import com.masai.model.LoginDTO;
import com.masai.repository.CustomerDao;
import com.masai.repository.DriverDao;
import com.masai.repository.SessionDao;

import net.bytebuddy.utility.RandomString;


@Service
public class DriverLoginServiceImpl implements DriverLoginService{


	@Autowired
	private DriverDao driveDao;
	
	@Autowired
	private SessionDao sessDao;
	
	
	
	@Override
	public String logIntoAccount(LoginDTO dto)throws LoginException{
		
		
		DriverDetails existingDriver= driveDao.findByMobileNumber(dto.getMobileNumber());
		
		
	
		
		
		if(existingDriver == null) {
			
			throw new LoginException("Please Enter a valid mobile number");
			
			 
		}
		
		
		
		
		Optional<CurrentUserSession> validDriverSessionOpt =  sessDao.findById(existingDriver.getDriverId());
		
		
		
		
		
		if(validDriverSessionOpt.isPresent()) {
			
			throw new LoginException("User already Logged In with this number");
			
		}
		
		if(existingDriver.getPassword().equals(dto.getPassword())) {
			
			String key= RandomString.make(6);
			
			
			
			CurrentUserSession currentUserSession = new CurrentUserSession(existingDriver.getDriverId(),key,LocalDateTime.now());
			
			sessDao.save(currentUserSession);

			return currentUserSession.toString();
		}
		else
			throw new LoginException("Please Enter a valid password");
		
		
	}


	@Override
	public String logOutFromAccount(String key)throws LoginException {
		
		CurrentUserSession validDriverSession = sessDao.findByUuid(key);
		
		
		if(validDriverSession == null) {
			throw new LoginException("User Not Logged In with this number");
			
		}
		
		
		sessDao.delete(validDriverSession);
		
		
		return "Logged Out !";
		
		
	}


}
