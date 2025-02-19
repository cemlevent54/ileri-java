package com.javacrudpractice.javademo.service;

import com.javacrudpractice.javademo.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(Long employeeId);

    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO updatedEmployee);

    void deleteEmployee(Long employeeId);
}
