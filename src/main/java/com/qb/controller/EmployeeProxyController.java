package com.qb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qb.Dao.Employee;
import com.qb.service.EmployeeProxyService;

@RestController
public class EmployeeProxyController {
	
	//@Autowired
	//RestTemplate restTemplate;
	
	@Autowired
	private EmployeeProxyService service;
	
	@PostMapping("/addemp")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee emp){
		
		return service.addEmployee(emp);
		
	}
	
	@PostMapping("getempbypin")
	public ResponseEntity<Integer> getEmployeeByPinCode(@RequestBody Map<String, String> data){
		
		return service.getEmployeeByPinCode(data);
		
	}
	
	@PostMapping("getempbyage")
	public ResponseEntity<List<Employee>> getEmployeeByAgeGroup(@RequestBody Map<String, String> data){
		
		return service.getEmployeeByAgeGroup(data);
		
		
	}
	
	@PostMapping("getempbydistrict")
	public ResponseEntity<Map<String, Integer>> getEmployeeByDistrict(){
		
		return service.getEmployeeByDistrict();
		
	}

	
	@PostMapping("getempbyleave")
	public ResponseEntity<List<Employee>> getEmployeeByLeave(){
		
		return service.getEmployeeByLeave();
		
		
	}
}
