package com.example.java.quiz5.repository;

import com.example.java.quiz5.entity.Employee;
import com.example.java.quiz5.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
