package com.example.callmanagement.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {


    @Id
    private String emp_id;

    private String name;
    private String location;

    public Employee(){

    }

    public Employee(String emp_id, String name, String location) {
        this.emp_id = emp_id;
        this.name = name;
        this.location = location;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
