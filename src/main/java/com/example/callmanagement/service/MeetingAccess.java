package com.example.callmanagement.service;


import com.example.callmanagement.entity.EmployeeMeeting;
import com.example.callmanagement.entity.Meeting;
import com.example.callmanagement.bo.MeetingBO;
import com.example.callmanagement.dao.IEmployeeMeetingDao;
import com.example.callmanagement.dao.IMeetingDao;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MeetingAccess {

    static int meetingids = 0;

    @Autowired
    private IMeetingDao iMeetingDao;

    @Autowired
    private IEmployeeMeetingDao iEmployeeMeetingDao;

    public List<Meeting> getALlMeeting(){
        return iMeetingDao.findAll();
    }

    public Meeting addMeeting(MeetingBO meetingBO){

        Duration period = Duration.between(meetingBO.getStartTime(),meetingBO.getEndTime());
        List<String> employees = new ArrayList<>();
        employees = meetingBO.getEmployees();
        Meeting meeting = new Meeting(++meetingids, meetingBO.getAgenda()
                , LocalDateTime.now(),period.toMinutes(),meetingBO.getStartTime(),meetingBO.getEndTime());


        iMeetingDao.save(meeting);
        for(int i=0; i < employees.size() ; i++){
            iEmployeeMeetingDao.save(new EmployeeMeeting(meetingids,employees.get(i)));
        }
        return meeting;
    }

    public String freeSlots(String date, String empId1, String empId2)throws Exception{
        LocalDateTime date1 = LocalDateTime.parse(date);
        List<EmployeeMeeting> employeeMeetings1 = iEmployeeMeetingDao.findByEmpId(empId1);
        int emp1[] = new int[48];
        List<Meeting> meetings1 = new ArrayList<>();
        for(int i = 0; i<employeeMeetings1.size(); i++){
            meetings1.addAll(iMeetingDao.findByMeetingId(employeeMeetings1.get(i).getMeetingId()));
        }
        for(int i= 0 ; i < meetings1.size(); i++){
            LocalDateTime time = meetings1.get(i).getStartTime();
            if(time.getYear() == date1.getYear() && time.getMonth() == date1.getMonth() && time.getDayOfMonth() == date1.getDayOfMonth()){
                if(time.getMinute() <= 30){
                    emp1[time.getHour()*2] = 1;
                }else{
                    emp1[time.getHour()*2+1] = 1;
                }
            }
        }

        List<EmployeeMeeting> employeeMeetings2 = iEmployeeMeetingDao.findByEmpId(empId1);
        int emp2[] = new int[48];
        List<Meeting> meetings2 = new ArrayList<>();
        for(int i = 0;i<employeeMeetings2.size();i++){
            meetings2.addAll(iMeetingDao.findByMeetingId(employeeMeetings2.get(i).getMeetingId()));
        }
        for(int i= 0 ; i < meetings2.size(); i++){
            LocalDateTime time = meetings2.get(i).getStartTime();
            if(time.getYear() == date1.getYear() && time.getMonth() == date1.getMonth() && time.getDayOfMonth() == date1.getDayOfMonth()){
                if(time.getMinute() <= 30){
                    emp2[time.getHour()*2] = 1;
                }else{
                    emp2[time.getHour()*2+1] = 1;
                }
            }
        }
        int freeSlots[] = new int [48];
        for(int i=0;i<48;i++){
            freeSlots[i] = emp1[i] & emp2[i];
        }
        JSONObject jsonObject = convertTotime(freeSlots);
        return jsonObject.toString();
    }

    public List<Meeting> getmeetings(String empId, String date) {
        LocalDateTime date1 = LocalDateTime.parse(date);
        List<EmployeeMeeting> employeeMeetings1 = iEmployeeMeetingDao.findByEmpId(empId);
        List<Meeting> meetings1 = new ArrayList<>();
        List<Meeting> meetingReturn = new ArrayList<>();
        for(int i = 0; i<employeeMeetings1.size(); i++){
            meetings1.addAll(iMeetingDao.findByMeetingId(employeeMeetings1.get(i).getMeetingId()));
        }
        for(int i= 0 ; i < meetings1.size(); i++){
            LocalDateTime time = meetings1.get(i).getStartTime();
            if(time.getYear() == date1.getYear() && time.getMonth() == date1.getMonth() && time.getDayOfMonth() == date1.getDayOfMonth()){
                meetingReturn.add(meetings1.get(i));
            }
        }
        return meetingReturn;
    }

    public List<String> getConflict(Integer meetingId) throws  NullPointerException{
        List<Meeting> conflictedWithMeeting = iMeetingDao.findByMeetingId(meetingId);
        LocalDateTime startTimeOfMeeting = conflictedWithMeeting.get(0).getStartTime();
        LocalDateTime endTimeOfMeeting = conflictedWithMeeting.get(0).getEndTime();
        List<Meeting> allMeetings = iMeetingDao.findByMeetingIdNot(meetingId);
        List<Meeting> conflictedWith = new ArrayList<>();
        for(int i = 0; i < allMeetings.size(); i++){
            LocalDateTime time1 = allMeetings.get(i).getStartTime();
            LocalDateTime time2 = allMeetings.get(i).getEndTime();
            if(time2.isAfter(startTimeOfMeeting) && time2.isBefore(endTimeOfMeeting)
                    || time1.isAfter(startTimeOfMeeting) && time1.isBefore(endTimeOfMeeting)
                    || time1.equals(startTimeOfMeeting) && time2.isEqual(endTimeOfMeeting)
                    || time1.isAfter(startTimeOfMeeting)&& time2.isBefore(endTimeOfMeeting)
                    || time1.isBefore(startTimeOfMeeting) && time2.isAfter(endTimeOfMeeting)){
                conflictedWith.add(allMeetings.get(i));
            }
        }

        List<EmployeeMeeting> employeeConflict = iEmployeeMeetingDao.findByMeetingId(meetingId);
        ArrayList<String> employeeInCurrentMeeting = new ArrayList<>();
        for(int i=0; i<employeeConflict.size(); i++){
            employeeInCurrentMeeting.add(employeeConflict.get(i).getEmpId());
        }
        List<String> employeehavingConflict = new ArrayList<>();
        for(int i=0;i<conflictedWith.size();i++){
            List<EmployeeMeeting> temp = iEmployeeMeetingDao.findByMeetingId(conflictedWith.get(i).getMeetingId());
            for(int j = 0; j<temp.size(); j++){
                if(employeeInCurrentMeeting.contains(temp.get(j).getEmpId())){
                    employeehavingConflict.add(temp.get(j).getEmpId());
                }
            }
        }

        return employeehavingConflict;
    }


    public JSONObject convertTotime(int freeSlots[]){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("00:00 - 00:30",freeSlots[0]==0?"Available":"NotAvailable");
        jsonObject.put("00:30 - 01:00",freeSlots[1]==0?"Available":"NotAvailable");
        jsonObject.put("01:00 - 01:30",freeSlots[2]==0?"Available":"NotAvailable");
        jsonObject.put("01:30 - 02:00",freeSlots[3]==0?"Available":"NotAvailable");
        jsonObject.put("02:00 - 02:30",freeSlots[4]==0?"Available":"NotAvailable");
        jsonObject.put("02:30 - 03:00",freeSlots[5]==0?"Available":"NotAvailable");
        jsonObject.put("03:00 - 03:30",freeSlots[6]==0?"Available":"NotAvailable");
        jsonObject.put("03:30 - 04:00",freeSlots[7]==0?"Available":"NotAvailable");
        jsonObject.put("04:00 - 04:30",freeSlots[8]==0?"Available":"NotAvailable");
        jsonObject.put("04:30 - 05:00",freeSlots[9]==0?"Available":"NotAvailable");
        jsonObject.put("05:00 - 05:30",freeSlots[10]==0?"Available":"NotAvailable");
        jsonObject.put("05:30 - 06:00",freeSlots[11]==0?"Available":"NotAvailable");
        jsonObject.put("06:00 - 06:30",freeSlots[12]==0?"Available":"NotAvailable");
        jsonObject.put("06:30 - 07:00",freeSlots[13]==0?"Available":"NotAvailable");
        jsonObject.put("07:00 - 07:30",freeSlots[14]==0?"Available":"NotAvailable");
        jsonObject.put("07:30 - 08:00",freeSlots[15]==0?"Available":"NotAvailable");
        jsonObject.put("08:00 - 08:30",freeSlots[16]==0?"Available":"NotAvailable");
        jsonObject.put("08:30 - 09:00",freeSlots[17]==0?"Available":"NotAvailable");
        jsonObject.put("09:00 - 09:30",freeSlots[18]==0?"Available":"NotAvailable");
        jsonObject.put("09:30 - 10:00",freeSlots[19]==0?"Available":"NotAvailable");
        jsonObject.put("10:00 - 10:30",freeSlots[20]==0?"Available":"NotAvailable");
        jsonObject.put("10:30 - 11:00",freeSlots[21]==0?"Available":"NotAvailable");
        jsonObject.put("11:00 - 11:30",freeSlots[22]==0?"Available":"NotAvailable");
        jsonObject.put("11:30 - 12:00",freeSlots[23]==0?"Available":"NotAvailable");
        jsonObject.put("12:00 - 12:30",freeSlots[24]==0?"Available":"NotAvailable");
        jsonObject.put("12:30 - 13:00",freeSlots[25]==0?"Available":"NotAvailable");
        jsonObject.put("13:00 - 13:30",freeSlots[26]==0?"Available":"NotAvailable");
        jsonObject.put("13:30 - 14:00",freeSlots[27]==0?"Available":"NotAvailable");
        jsonObject.put("14:00 - 14:30",freeSlots[28]==0?"Available":"NotAvailable");
        jsonObject.put("14:30 - 15:00",freeSlots[29]==0?"Available":"NotAvailable");
        jsonObject.put("15:00 - 15:30",freeSlots[30]==0?"Available":"NotAvailable");
        jsonObject.put("15:30 - 16:00",freeSlots[31]==0?"Available":"NotAvailable");
        jsonObject.put("16:00 - 16:30",freeSlots[32]==0?"Available":"NotAvailable");
        jsonObject.put("16:30 - 17:00",freeSlots[33]==0?"Available":"NotAvailable");
        jsonObject.put("17:00 - 17:30",freeSlots[34]==0?"Available":"NotAvailable");
        jsonObject.put("17:30 - 18:00",freeSlots[35]==0?"Available":"NotAvailable");
        jsonObject.put("18:00 - 18:30",freeSlots[36]==0?"Available":"NotAvailable");
        jsonObject.put("18:30 - 19:00",freeSlots[37]==0?"Available":"NotAvailable");
        jsonObject.put("19:00 - 19:30",freeSlots[38]==0?"Available":"NotAvailable");
        jsonObject.put("19:30 - 20:00",freeSlots[39]==0?"Available":"NotAvailable");
        jsonObject.put("20:00 - 20:30",freeSlots[40]==0?"Available":"NotAvailable");
        jsonObject.put("20:30 - 21:00",freeSlots[41]==0?"Available":"NotAvailable");
        jsonObject.put("21:00 - 21:30",freeSlots[42]==0?"Available":"NotAvailable");
        jsonObject.put("21:30 - 22:00",freeSlots[43]==0?"Available":"NotAvailable");
        jsonObject.put("22:00 - 22:30",freeSlots[44]==0?"Available":"NotAvailable");
        jsonObject.put("22:30 - 23:00",freeSlots[45]==0?"Available":"NotAvailable");
        jsonObject.put("23:00 - 23:30",freeSlots[46]==0?"Available":"NotAvailable");
        jsonObject.put("23:30 - 24:00",freeSlots[47]==0?"Available":"NotAvailable");
        return jsonObject;
    }
}
