package com.example.employee;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }

    public Optional<Employee> findById(Integer id) {
        return employeeRepository.findById(id);
    }

    public Employee findByFirstNameAndAge(String firstName, Integer age) {
        return employeeRepository.findByFirstNameAndAge(firstName, age);
    }

    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee updateEmployee(Integer id, Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setAge(employee.getAge());
            existingEmployee.setEducationDetails(employee.getEducationDetails());
            existingEmployee.setRole(employee.getRole());
            return employeeRepository.save(existingEmployee);
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }
}
