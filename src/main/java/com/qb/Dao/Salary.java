package com.qb.Dao;

import lombok.Data;

@Data
public class Salary {

	
	private int id;
	
	private Employee emp_id;
	
	private String month;
	
	private String year;
	
	private long basic_pay;
	
	private long bonus;
	
	private long hra;
	
	private long total;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Employee getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Employee emp_id) {
		this.emp_id = emp_id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public long getBasic_pay() {
		return basic_pay;
	}

	public void setBasic_pay(long basic_pay) {
		this.basic_pay = basic_pay;
	}

	public long getBonus() {
		return bonus;
	}

	public void setBonus(long bonus) {
		this.bonus = bonus;
	}

	public long getHra() {
		return hra;
	}

	public void setHra(long hra) {
		this.hra = hra;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	
}
