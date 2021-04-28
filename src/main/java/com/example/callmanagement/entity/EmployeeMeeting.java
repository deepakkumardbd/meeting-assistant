package com.example.callmanagement.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;



@Entity
@IdClass(CompositeKey.class)
public class EmployeeMeeting {

    @Id
    private Integer meetingId;

    @Id
    private String empId;

    public EmployeeMeeting(){

    }

    public EmployeeMeeting(Integer meetingId, String empId) {
        this.meetingId = meetingId;
        this.empId = empId;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
