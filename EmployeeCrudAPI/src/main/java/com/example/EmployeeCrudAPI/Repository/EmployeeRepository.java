package com.example.EmployeeCrudAPI.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EmployeeCrudAPI.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	List<Employee> findByEmpCity(String emp_city);
	List<Employee> findByEmpAgeGreaterThan(int emp_age);
}
