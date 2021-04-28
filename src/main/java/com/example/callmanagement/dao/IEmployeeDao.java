package com.example.callmanagement.dao;

import com.example.callmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEmployeeDao extends JpaRepository<Employee, String> {
    List<Employee> findAll();
}
