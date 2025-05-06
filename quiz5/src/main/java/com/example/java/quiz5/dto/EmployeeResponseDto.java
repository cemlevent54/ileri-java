package com.example.java.quiz5.dto;

import com.example.java.quiz5.entity.Employee;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponseDto {

    private String status;
    private List<Employee> data;
    private String message;
}
