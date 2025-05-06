package com.example.java.quiz5.service;

import com.example.java.quiz5.entity.Employee;
import com.example.java.quiz5.interfaces.EmployeeService;
import com.example.java.quiz5.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        if (employee.getId() == null || !employeeRepository.existsById(employee.getId())) {
            // Eğer ID null ise veya ID mevcut değilse, kaydı kaydediyoruz
            employee = employeeRepository.save(employee);  // Save çağrısı burada yapılıyor.
        }
        return employeeRepository.findById(employee.getId()).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setEmployee_name(employee.getEmployee_name());
            existingEmployee.setEmployee_salary(employee.getEmployee_salary());
            existingEmployee.setEmployee_age(employee.getEmployee_age());
            existingEmployee.setProfile_image(employee.getProfile_image());
            return employeeRepository.save(existingEmployee);
        }
        return null;
    }
}
