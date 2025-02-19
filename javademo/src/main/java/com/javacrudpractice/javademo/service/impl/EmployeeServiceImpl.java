package com.javacrudpractice.javademo.service.impl;

import com.javacrudpractice.javademo.dto.EmployeeDTO;
import com.javacrudpractice.javademo.entity.Employee;
import com.javacrudpractice.javademo.exception.ResourceNotFoundException;
import com.javacrudpractice.javademo.mapper.EmployeeMapper;
import com.javacrudpractice.javademo.repository.EmployeeRepository;
import com.javacrudpractice.javademo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDTO);
        Employee savedemployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedemployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        Employee employee= employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee is not exist with given id: " + employeeId));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();


        return employees.stream()
                .map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exists with given id: " + employeeId)
        );
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Employee is not exists with given id: "+ employeeId)
                );
        employeeRepository.deleteById(employeeId);
    }


}
