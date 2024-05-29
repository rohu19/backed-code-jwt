package com.example.employee;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Employee> searchByCriteria(EmployeeDTO employeeDTO) {

        Pageable pageable = PageRequest.of(employeeDTO.getPage() != null ? employeeDTO.getPage() : 0,
                employeeDTO.getSize() != null ?  employeeDTO.getSize() : 50, Sort.by("id").descending());

        QEmployee qEmployee = QEmployee.employee;
        BooleanBuilder builder = new BooleanBuilder();

        if (employeeDTO.getFirstName() != null) {
            builder.and(qEmployee.firstName.eq(employeeDTO.getFirstName()));
        }

        if (employeeDTO.getLastName() != null) {
            builder.and(qEmployee.lastName.eq(employeeDTO.getLastName()));
        }

        if (employeeDTO.getAge() != null) {
            builder.and(qEmployee.age.eq(employeeDTO.getAge()));
        }

        if(employeeDTO.getEducationDetails()!=null){
            builder.and(qEmployee.educationDetails.eq(employeeDTO.getEducationDetails()));
        }

        if(employeeDTO.getRole()!=null){
            builder.and(qEmployee.role.eq(employeeDTO.getRole()));
        }
        return employeeRepository.findAll(builder, pageable);
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
