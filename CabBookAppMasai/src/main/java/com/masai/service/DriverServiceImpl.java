package com.masai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exceptions.DriverException;
import com.masai.model.CabDetails;
import com.masai.model.DriverDetails;
import com.masai.model.DriverDTO;
import com.masai.repository.DriverDao;

@Service
public class DriverServiceImpl implements DriverService{
	
	@Autowired
	private DriverDao driverDao;

	@Override
	public DriverDetails createDriver(DriverDTO driver) throws DriverException {
		// TODO Auto-generated method stub
	    DriverDetails d = new DriverDetails();
	    d.setLicenseNumber(driver.getLicenseNumber());
	    d.setUsername(driver.getUsername());
	    d.setMobileNumber(driver.getMobileNumber());
		d.setPassword(driver.getPassword());
		d.setCurrentPosition(driver.getCurrentPosition());
		
		CabDetails cab = new CabDetails();
		cab.setNumberPlate(driver.getNumberPlate());
		cab.setDriver(d);
		d.setCab(cab);
		
		driverDao.save(d);
		return d;
	}

}
