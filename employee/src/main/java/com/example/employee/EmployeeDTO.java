package com.example.employee;

import lombok.Data;

@Data
public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private Integer age;
    private String educationDetails;
    private String role;
    private Integer page;
    private Integer size;
}
