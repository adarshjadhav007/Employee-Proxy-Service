package com.qb.Dao;

import lombok.Data;

@Data
public class Salary {

	
	private int id;
	
	private int emp_id;
	
	private String month;
	
	private String year;
	
	private long basic_pay;
	
	private long bonus;
	
	private long hra;
	
	private long total;
}
