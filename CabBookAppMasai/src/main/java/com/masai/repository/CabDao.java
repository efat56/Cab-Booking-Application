package com.masai.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.CabDetails;


@Repository
public interface CabDao extends JpaRepository<CabDetails, Integer> {

	public CabDetails findByNumberPlate(String number);
	
}
