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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContact_number() {
		return contact_number;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}

	public String getEmg_contact_number() {
		return emg_contact_number;
	}

	public void setEmg_contact_number(String emg_contact_number) {
		this.emg_contact_number = emg_contact_number;
	}

	public Address getCurrent_address() {
		return current_address;
	}

	public void setCurrent_address(Address current_address) {
		this.current_address = current_address;
	}

	public Address getPermanent_address() {
		return permanent_address;
	}

	public void setPermanent_address(Address permanent_address) {
		this.permanent_address = permanent_address;
	}
	
	
	
	
}
