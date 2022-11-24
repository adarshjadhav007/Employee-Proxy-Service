package com.qb.Dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String house_num;
	
	private String street_name;
	
	private String district;
	
	private String state;
	
	private String country;
	
	private String pin_code;
	
	//private String address_type;
}
