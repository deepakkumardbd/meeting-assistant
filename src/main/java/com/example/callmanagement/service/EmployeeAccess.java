package com.example.callmanagement.service;

import com.example.callmanagement.entity.Employee;
import com.example.callmanagement.dao.IEmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeAccess {

    @Autowired
    private IEmployeeDao iEmployeeDao;

    public List<Employee> getEmployees(){
        return iEmployeeDao.findAll();
    }

    public Employee addEmployee(Employee emp){
        iEmployeeDao.save(emp);
        return emp;
    }
}
