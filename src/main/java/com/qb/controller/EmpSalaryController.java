package com.qb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qb.Dao.Employee;
import com.qb.service.EmployeeSalaryService;


@RestController
public class EmpSalaryController {
	
	
	@Autowired
	private EmployeeSalaryService service;
	
	@PostMapping("/getmaxsalemp")
	public ResponseEntity<Employee> getEmpWithMaxSal(@RequestBody Map<String, String> data){
		
		return service.getEmpWithMaxSal(data);
		
	}
	
	@PostMapping("/getmaxsalempyear")
	public ResponseEntity<List<Employee>> getEmpWithMaxSalByYear(@RequestBody Map<String, String> data){
		
		return service.getEmpWithMaxSalByYear(data);
		
	}

}
