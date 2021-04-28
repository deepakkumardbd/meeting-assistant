package com.example.callmanagement.dao;

import com.example.callmanagement.entity.CompositeKey;
import com.example.callmanagement.entity.EmployeeMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEmployeeMeetingDao extends JpaRepository<EmployeeMeeting, CompositeKey> {
    List<EmployeeMeeting> findAll();
    List<EmployeeMeeting> findByEmpId(String empId);
    List<EmployeeMeeting> findByMeetingId(Integer meetingId);
    List<EmployeeMeeting> findByEmpIdNot(String empId);
}
