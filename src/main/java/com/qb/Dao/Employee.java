package com.qb.Dao;

import lombok.Data;

@Data
public class Employee {

	
	private int id;
	
	private String first_name;
	
	private String last_name;
	
	private int age;
	
	private String gender;
	
	private String contact_number;
	
	private String emg_contact_number;
	
	private Address current_address;
	
	private Address permanent_address;
	
	
}
