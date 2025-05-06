package com.example.java.quiz5.unit;

import com.example.java.quiz5.entity.Employee;
import com.example.java.quiz5.repository.EmployeeRepository;
import com.example.java.quiz5.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void testSaveEmployee() {
        // Mock data (ID null olmalı çünkü yeni bir kayıt ekleniyor)
        Employee employee = new Employee(null, "John Doe", "50000", 30L, "profile.jpg");

        // Stubbing the repository methods
        when(employeeRepository.existsById(any())).thenReturn(false);  // Mock: existsById() will return false
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);  // Mock: save() will return the employee

        // Call the method under test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // Assert that the saved employee is not null
        assertNotNull(savedEmployee, "Saved employee should not be null.");
        assertEquals("John Doe", savedEmployee.getEmployee_name());
        assertEquals("50000", savedEmployee.getEmployee_salary());

        // Optional: You can also verify that the method was called correctly
        verify(employeeRepository).save(employee);
        verify(employeeRepository).existsById(any());  // Verify existsById() was called
    }









    @Test
    void testGetAllEmployees() {
        // Test verisi
        Employee employee1 = new Employee(1L, "John Doe", "50000", 30L, "profile.jpg");
        Employee employee2 = new Employee(2L, "Jane Smith", "60000", 25L, "profile2.jpg");

        // Mock: employeeRepository.findAll metodunu doğru şekilde davranacak şekilde yapılandırıyoruz
        when(employeeRepository.findAll()).thenReturn(List.of(employee1, employee2));

        // Servis metodunu çağırıyoruz
        List<Employee> employees = employeeService.getAllEmployees();

        // Sonuçları doğruluyoruz
        assertNotNull(employees);
        assertEquals(2, employees.size());
        assertEquals("John Doe", employees.get(0).getEmployee_name());
        assertEquals("Jane Smith", employees.get(1).getEmployee_name());
    }

    @Test
    void testGetEmployeeById() {
        // Test verisi
        Employee employee = new Employee(1L, "John Doe", "50000", 30L, "profile.jpg");

        // Mock: employeeRepository.findById metodunu doğru şekilde davranacak şekilde yapılandırıyoruz
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Servis metodunu çağırıyoruz
        Employee foundEmployee = employeeService.getEmployeeById(1L);

        // Sonuçları doğruluyoruz
        assertNotNull(foundEmployee);
        assertEquals("John Doe", foundEmployee.getEmployee_name());
        assertEquals("50000", foundEmployee.getEmployee_salary());
    }

    @Test
    void testDeleteEmployee() {
        Long employeeId = 1L;

        // Mock: deleteById metodunun çağrılmasını doğruluyoruz
        doNothing().when(employeeRepository).deleteById(employeeId);

        // Servis metodunu çağırıyoruz
        employeeService.deleteEmployee(employeeId);

        // Verifikasyon: deleteById metodunun çağrıldığını doğruluyoruz
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void testUpdateEmployee() {
        // Test verisi
        Employee existingEmployee = new Employee(1L, "John Doe", "50000", 30L, "profile.jpg");
        Employee updatedEmployee = new Employee(1L, "John Doe", "55000", 31L, "profile_updated.jpg");

        // Mock: employeeRepository.findById ve save metodunun doğru şekilde davranmasını sağlıyoruz
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        // Servis metodunu çağırıyoruz
        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        // Sonuçları doğruluyoruz
        assertNotNull(result);
        assertEquals("55000", result.getEmployee_salary());
        assertEquals("profile_updated.jpg", result.getProfile_image());

        // Veritabanında güncelleme kontrolü
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(employeeRepository, times(1)).findById(1L);
    }
}
