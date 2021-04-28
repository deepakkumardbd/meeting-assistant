package com.example.callmanagement.dao;

import com.example.callmanagement.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IMeetingDao extends JpaRepository<Meeting, Integer> {
    List<Meeting> findAll();
    List<Meeting> findByMeetingId(Integer meetingId);
    List<Meeting> findByMeetingIdNot(Integer meetingId);
}
