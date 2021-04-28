package com.example.callmanagement.controller;

import com.example.callmanagement.entity.Meeting;
import com.example.callmanagement.bo.MeetingBO;
import com.example.callmanagement.service.MeetingAccess;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/**")
public class MeetingController {

    @Autowired
    private MeetingAccess meetingAccess;

    @PostMapping(value = "/employee/meeting")
    public ResponseEntity<Object> addMeeting(@RequestBody MeetingBO meetingBO){
        Meeting meetingResponse = meetingAccess.addMeeting(meetingBO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(meetingResponse.getMeetingId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/employee/{employeeId}/meeting/{date}")
    public ResponseEntity<Object> getMeeting(@PathVariable String employeeId, @PathVariable String date) throws NullPointerException {
        List<Meeting> response = meetingAccess.getmeetings(employeeId,date);
        if(response.size() == 0){
            return ResponseEntity.ok("No Meetings scheduled for Employee on the given date");
        }else{
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping(value = "/employee/date/{date}/{employeeId1}/freeslots/{employeeId2}")
    public ResponseEntity<String> getFreeSlots(@PathVariable String date, @PathVariable String employeeId1, @PathVariable String employeeId2) throws Exception {
        return ResponseEntity.ok(meetingAccess.freeSlots(date,employeeId1,employeeId2));
    }

    @GetMapping(value = "/meeting/conflict/{meetingId}")
    public ResponseEntity<Object> getMeetingConflict(@PathVariable Integer meetingId){
        List<String> response = meetingAccess.getConflict(meetingId);
        if(response.size()==0){
           return  ResponseEntity.ok("NO Conflicts");
        }else{
            return ResponseEntity.ok(meetingAccess.getConflict(meetingId));
        }
    }
}
