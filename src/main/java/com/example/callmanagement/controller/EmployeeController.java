package com.example.callmanagement.controller;

import com.example.callmanagement.entity.Employee;
import com.example.callmanagement.service.EmployeeAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/**")
public class EmployeeController {

    @Autowired
    private EmployeeAccess employeeAccess;

    @PostMapping(value = "/employee")
    public ResponseEntity<Object> addUser(@RequestBody Employee emp) throws URISyntaxException {
        Employee empResponse = employeeAccess.addEmployee(emp);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(empResponse.getEmp_id()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/employees")
    public List<Employee> getAllEmployees(){
        return employeeAccess.getEmployees();
    }

}
