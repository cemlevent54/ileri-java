package com.example.java.quiz5.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("employee_name")
    private String employee_name;

    @JsonProperty("employee_salary")
    private String employee_salary;

    @JsonProperty("employee_age")
    private Long employee_age;

    @JsonProperty("profile_image")
    private String profile_image;
}
