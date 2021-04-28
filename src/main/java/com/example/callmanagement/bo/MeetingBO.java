package com.example.callmanagement.bo;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingBO {

        private String agenda;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private List<String> employees;


        public MeetingBO(){

        }

    public MeetingBO(String agenda, LocalDateTime startTime, LocalDateTime endTime, List<String> employees) {
        this.agenda = agenda;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employees = employees;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
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

    public List<String> getEmployees() {
        return employees;
    }

    public void setEmployees(List<String> employees) {
        this.employees = employees;
    }
}
