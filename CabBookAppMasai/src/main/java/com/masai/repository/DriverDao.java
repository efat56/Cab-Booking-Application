package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.masai.model.Customer;
import com.masai.model.DriverDetails;

@Repository
public interface DriverDao extends JpaRepository<DriverDetails,Integer>{
	
	public DriverDetails findByMobileNumber(String mobileNo);
	
    public DriverDetails findByDriverId(Integer driverId);

}
