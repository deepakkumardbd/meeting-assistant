package com.example.callmanagement.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Meeting {


    @Id
    private Integer meetingId;

    private String agenda;
    private LocalDateTime createdAt;
    private long duration;
    private  LocalDateTime startTime;
    private LocalDateTime endTime;



    public Meeting(){

    }

    public Meeting(Integer meetingId, String agenda, LocalDateTime createdAt, long duration, LocalDateTime startTime, LocalDateTime endTime) {
        this.meetingId = meetingId;
        this.agenda = agenda;
        this.createdAt = createdAt;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Meeting(String agenda, LocalDateTime createdAt, long duration, LocalDateTime startTime, LocalDateTime endTime) {
        this.agenda = agenda;
        this.createdAt = createdAt;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
