package com.qb.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qb.Dao.Employee;
import com.qb.Dao.Salary;

@Service
public class EmployeeSalaryService {

	public ResponseEntity<Employee> getEmpWithMaxSal(Map<String, String> data) {
		

		RestTemplate restTemplate =new RestTemplate();
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8081/getsalarydetails", List.class);
		
		String month = data.get("month");
		
		ObjectMapper mapper = new ObjectMapper();
		List<Salary> empSalary = mapper.convertValue(
			    response.getBody(), 
			    new TypeReference<List<Salary>>(){}
		);
		
		Optional<Salary> empsal = empSalary.stream().filter(sal -> sal.getMonth().equals(month)).collect(Collectors.maxBy(Comparator.comparingLong(Salary::getTotal)));
		
		System.out.println(empsal);
		
		Employee maxSalEmpObj = restTemplate.getForObject("http://localhost:8081/getemployeedetails/"+empsal.get().getEmp_id().getId(), Employee.class);
		
		System.out.println("Hello:"+maxSalEmpObj);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		
		return new ResponseEntity<>(maxSalEmpObj, responseHeaders, HttpStatus.OK);
	}

	public ResponseEntity<List<Employee>> getEmpWithMaxSalByYear(Map<String, String> data) {
		
		RestTemplate restTemplate =new RestTemplate();
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8081/getsalarydetails", List.class);
		
		String year = data.get("year");
	
		
		ObjectMapper mapper = new ObjectMapper();
		List<Salary> empSalary = mapper.convertValue(
			    response.getBody(), 
			    new TypeReference<List<Salary>>(){}
		);
		
		List<Salary> empList = empSalary.stream().filter(sal -> sal.getYear().equals(year)).collect(Collectors.toList());
		
		Map<Integer, Long> empSalMap = new HashMap<>();
		for (Salary salary : empList) {
			if(empSalMap.get(salary.getEmp_id().getId()) == null) {
				empSalMap.put(salary.getEmp_id().getId(), salary.getTotal());
			}else {
				empSalMap.put(salary.getEmp_id().getId(), salary.getTotal() + empSalMap.get(salary.getEmp_id().getId()));
			}
		}
		
		
		
		List<Integer> empListWithSameSal = new ArrayList<>();
		long maxValueInMap = (Collections.max(empSalMap.values()));
		 for (Entry<Integer, Long> entry : empSalMap.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
            	empListWithSameSal.add(entry.getKey());
            }
        }
		 
		List<Employee> responseList = new ArrayList<>(); 
		for (Integer empid : empListWithSameSal) {
			responseList.add(restTemplate.getForObject("http://localhost:8081/getemployeedetails/"+empid, Employee.class));
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		
		return new ResponseEntity<>(responseList, responseHeaders, HttpStatus.OK);
	}

	
	
}
