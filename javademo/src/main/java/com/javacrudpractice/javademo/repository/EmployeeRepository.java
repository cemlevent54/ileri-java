package com.javacrudpractice.javademo.repository;

import com.javacrudpractice.javademo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    
}
