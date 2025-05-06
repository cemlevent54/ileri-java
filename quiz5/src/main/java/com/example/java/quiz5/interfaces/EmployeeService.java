package com.example.java.quiz5.interfaces;

import com.example.java.quiz5.entity.Employee;

import java.util.List;

public interface EmployeeService {
    public Employee saveEmployee(Employee employee);
    public List<Employee> getAllEmployees();
    public Employee getEmployeeById(Long id);
    public void deleteEmployee(Long id);
    public Employee updateEmployee(Long id, Employee employee);
}
