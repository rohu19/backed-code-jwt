package com.example.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/employees")
@CrossOrigin
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/save")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Employee> findById(@PathVariable Integer id) {
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        return optionalEmployee.map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Employee> findByFirstNameAndAge(
            @RequestParam String firstName, @RequestParam Integer age) {
        Employee employees = employeeService.findByFirstNameAndAge(firstName, age);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/page")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<Employee>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = employeeService.findAll(pageable);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
}
