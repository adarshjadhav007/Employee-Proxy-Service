package com.qb.controller;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.aspectj.weaver.loadtime.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.qb.Dao.Employee;
import com.qb.Dao.Leaves;
import com.qb.Dao.Salary;

@RestController
public class EmployeeProxyController {
	
	//@Autowired
	//RestTemplate restTemplate;
	
	@PostMapping("/addemp")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee emp){
		
		
		RestTemplate restTemplate =new RestTemplate();
		
		Gson gson = new Gson();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(emp), headers);
		Employee response = restTemplate.postForObject("http://localhost:8081/addemployee", entity, Employee.class);
		HttpHeaders responseHeaders = new HttpHeaders();
	    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
	}
	
	@PostMapping("getempbypin")
	public ResponseEntity<Integer> getEmployeeByPinCode(@RequestBody Map<String, String> data){
		RestTemplate restTemplate =new RestTemplate();
		
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8081/getemployeedetails", List.class);
		
		String pin = data.get("pincode");
	
		
		ObjectMapper mapper = new ObjectMapper();
		List<Employee> empList = mapper.convertValue(
			    response.getBody(), 
			    new TypeReference<List<Employee>>(){}
		);
		
		int count = empList.stream().filter(emp -> emp.getPermanent_address().getPin_code().equals(pin) || emp.getCurrent_address().getPin_code().equals(pin)).collect(Collectors.toList()).size();
		
		HttpHeaders responseHeaders = new HttpHeaders();
		if(count == 0) {
			return new ResponseEntity<>(count, responseHeaders, HttpStatus.NOT_FOUND);
		}
		 return new ResponseEntity<>(count, responseHeaders, HttpStatus.OK);
	}
	
	@PostMapping("getempbyage")
	public ResponseEntity<List<Employee>> getEmployeeByAgeGroup(@RequestBody Map<String, String> data){
		RestTemplate restTemplate =new RestTemplate();
		
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8081/getemployeedetails", List.class);
		
		String[] ageGroup = data.get("age").split("-");
		int minAge = Integer.parseInt(ageGroup[0]);
		int maxAge = Integer.parseInt(ageGroup[1]);
		
		ObjectMapper mapper = new ObjectMapper();
		List<Employee> empList = mapper.convertValue(
			    response.getBody(), 
			    new TypeReference<List<Employee>>(){}
		);
		
		List<Employee> empWithAge = empList.stream().filter(emp -> emp.getAge() >= minAge && emp.getAge() <= maxAge).collect(Collectors.toList());
		
		HttpHeaders responseHeaders = new HttpHeaders();
		if (empWithAge.isEmpty()) {
			return new ResponseEntity<>(empWithAge, responseHeaders, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(empWithAge, responseHeaders, HttpStatus.OK);
	}
	
	@PostMapping("getempbydistrict")
	public ResponseEntity<Map<String, Integer>> getEmployeeByDistrict(){
		RestTemplate restTemplate =new RestTemplate();
		
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8081/getemployeedetails", List.class);
	
		
		ObjectMapper mapper = new ObjectMapper();
		List<Employee> empList = mapper.convertValue(
			    response.getBody(), 
			    new TypeReference<List<Employee>>(){}
		);
		
		Map<String, Integer> districtEmpMap = new HashMap<>();
		int count = 0;
		for (Employee employee : empList) {
			count = districtEmpMap.get(employee.getPermanent_address().getDistrict()) == null ? 1 
					: districtEmpMap.get(employee.getPermanent_address().getDistrict()) + 1;
			districtEmpMap.put(employee.getPermanent_address().getDistrict(), count);
		}
		
		
		HttpHeaders responseHeaders = new HttpHeaders();
		if (districtEmpMap.isEmpty()) {
			return new ResponseEntity<>(districtEmpMap, responseHeaders, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(districtEmpMap, responseHeaders, HttpStatus.OK);
	}

	
	@PostMapping("getempbyleave")
	public ResponseEntity<List<Employee>> getEmployeeByLeave(){
		RestTemplate restTemplate =new RestTemplate();
		
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8081/leavesalldetails", List.class);
	
		
		ObjectMapper mapper = new ObjectMapper();
		List<Leaves> leavesList = mapper.convertValue(
			    response.getBody(), 
			    new TypeReference<List<Leaves>>(){}
		);
		
		String year = String.valueOf(Year.now().getValue());
		System.out.println(leavesList.get(0).getTo_date().substring(leavesList.get(0).getTo_date().length() - 4));
		List<Leaves> leavesByYearList =  leavesList.stream().filter(leave -> leave.getTo_date().substring(leave.getTo_date().length() - 4).equals(year))
			.collect(Collectors.toList());
		
		
		Map<Integer, Integer> empLeavesMap = new HashMap<>();
		for (Leaves leaves : leavesByYearList) {
			if(empLeavesMap.get(leaves.getEmp_id().getId()) == null) {
				empLeavesMap.put(leaves.getEmp_id().getId(), leaves.getNo_of_days());
			}else {
				empLeavesMap.put(leaves.getEmp_id().getId(), leaves.getNo_of_days() + empLeavesMap.get(leaves.getEmp_id().getId()));
			}
		}
		
		List<Integer> empListWithSameLeave = new ArrayList<>();
		long minValueInMap = (Collections.min(empLeavesMap.values()));
		 for (Entry<Integer, Integer> entry : empLeavesMap.entrySet()) {
            if (entry.getValue() == minValueInMap) {
            	empListWithSameLeave.add(entry.getKey());
            }
        }
		 
		List<Employee> responseList = new ArrayList<>(); 
		for (Integer empid : empListWithSameLeave) {
			responseList.add(restTemplate.getForObject("http://localhost:8081/getemployeedetails/"+empid, Employee.class));
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<>(responseList, responseHeaders, HttpStatus.OK);
	}
}
