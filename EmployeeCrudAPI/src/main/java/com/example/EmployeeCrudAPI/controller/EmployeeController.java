package com.example.EmployeeCrudAPI.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeCrudAPI.Repository.EmployeeRepository;
import com.example.EmployeeCrudAPI.model.Employee;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;
	
	//Save data 
	@PostMapping("/employees")	
	public String createNewEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		return "Employee created in database";
	}
	
	@GetMapping("/employees")	
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> empList = new ArrayList<>();
		employeeRepository.findAll().forEach(empList :: add);
		return new ResponseEntity<List<Employee>>(empList,HttpStatus.OK);
	}
	
	@GetMapping("/employees/{empid}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long empid){
		Optional<Employee> emp = employeeRepository.findById(empid);
		if(emp.isPresent()) {
			return new ResponseEntity<Employee>(emp.get(),HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/employees/{empid}")
	public String UpdateEmployeeById(@PathVariable long empid, @RequestBody Employee employee){
		Optional<Employee> emp = employeeRepository.findById(empid);
		if(emp.isPresent()) {
			Employee existEmp = emp.get();
			existEmp.setEmp_age(employee.getEmp_age());
			existEmp.setEmp_city(employee.getEmp_city());
			existEmp.setEmp_name(employee.getEmp_name());
			existEmp.setEmp_salary(employee.getEmp_salary());
			employeeRepository.save(existEmp);
			return "Employee Details against empid " +empid+ " is Updated";
		}
		else {
			return "Employee Details not exist for empid "+empid;
		}
	}
	
	@DeleteMapping("/employees/{empid}")
	public String deleteEmployeeById(@PathVariable long empid) {
		employeeRepository.deleteById(empid);
		return "Employee deleted successfully";
	}
	
	@DeleteMapping("/employees")
	public String deleteAllEmployee() {
		employeeRepository.deleteAll();
		return "All Employee deleted successfully";
	}
	
	
	//Assignment    
	//Get employee by city
	@GetMapping("/employees/city/{emp_city}")
	public ResponseEntity<?> getEmployeeByCity(@PathVariable String emp_city) {
	    List<Employee> empList = new ArrayList<>();
	    employeeRepository.findByEmpCity(emp_city).forEach(empList::add);

	    if (empList.isEmpty()) {
	    	return new ResponseEntity<>("No data found for city: " + emp_city, HttpStatus.NOT_FOUND);
	    } else {
	        return new ResponseEntity<>(empList, HttpStatus.FOUND);
	    }
	}

	//Get employee age> given age 
	@GetMapping("/employees/age/{emp_age}")
	public ResponseEntity<?> getEmployeeByAge(@PathVariable int emp_age) {
	    List<Employee> empList = new ArrayList<>();
	    employeeRepository.findByEmpAgeGreaterThan(emp_age).forEach(empList::add);

	    if (empList.isEmpty()) {
	    	return new ResponseEntity<>("No data found for city: " + emp_age, HttpStatus.NOT_FOUND);
	    } else {
	        return new ResponseEntity<>(empList, HttpStatus.FOUND);
	    }
	}


	
}
