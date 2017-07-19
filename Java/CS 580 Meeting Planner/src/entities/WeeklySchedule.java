package entities;

import java.sql.*;
import functions.MainProgram;
import java.util.Vector;

public class WeeklySchedule {
    private int recordID;
    private int employeeID;
    private WeekDay whichDay;
    private Time timeUnAvailBegin;
    private Time timeUnAvailEnd;

    public WeeklySchedule(int recordID, int employeeID, WeekDay whichDay, Time timeUnAvailBegin, Time timeUnAvailEnd) {
        this.recordID = recordID;
        this.employeeID = employeeID;
        this.whichDay = whichDay;
        this.timeUnAvailBegin = timeUnAvailBegin;
        this.timeUnAvailEnd = timeUnAvailEnd;
    }

    public int getRecordID() {
        return recordID;
    }

    public int getEmployeeID() {
        return employeeID;
    }
    
    public Time getTimeUnAvailBegin() {
        return timeUnAvailBegin;
    }

    public Time getTimeUnAvailEnd() {
        return timeUnAvailEnd;
    }

    public WeekDay getWhichDay() {
        return whichDay;
    }

    public boolean updateSchedule(WeekDay d, Time b, Time e) throws InvalidScheduleException{
        try{
        if(!checkScheduleValid(timeUnAvailBegin, timeUnAvailEnd)){     
            throw new InvalidScheduleException("time end can't be before or equals time begin");
        }   
        if(isThereOtherSameSchedule(d,b,e)){
            throw new InvalidScheduleException("schedule already exists");
        }
        dropConflictMeeting(employeeID, d, b, e);
        PreparedStatement updateStmt=MainProgram.getConnection().prepareStatement(
                "update weeklyschedule set whichDay=?, TimeUnavailBegin=?, TimeUnavailEnd=? where recordID=?");
        updateStmt.setInt(1, whichDay.ordinal());
        updateStmt.setTime(2, timeUnAvailBegin);
        updateStmt.setTime(3, timeUnAvailEnd);
        updateStmt.setInt(4, recordID);
        updateStmt.executeUpdate();        
        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
        whichDay=d;
        timeUnAvailBegin=b;
        timeUnAvailEnd=e;
        return true;
    }
    
    public static boolean scheduleAlreadyExists(int theEmployeeID, WeekDay theDay, Time theTimeBegin, Time theTimeEnd) throws SQLException{
        PreparedStatement checkStmt=MainProgram.getConnection().prepareStatement(
                "select * from weeklyschedule where employeeid=? and whichday=? and timeUnAvailBegin=? and timeUnAvailEnd=?");
        checkStmt.setInt(1, theEmployeeID);
        checkStmt.setInt(2, theDay.ordinal());
        checkStmt.setTime(3, theTimeBegin);
        checkStmt.setTime(4, theTimeEnd);
        ResultSet checkrs=checkStmt.executeQuery();
        return checkrs.next();
    }

    public boolean isThereOtherSameSchedule(WeekDay theDay, Time theTimeBegin, Time theTimeEnd) throws SQLException{
        PreparedStatement checkStmt=MainProgram.getConnection().prepareStatement(
                "select * from weeklyschedule where employeeid=? and whichday=? and timeUnAvailBegin=? and timeUnAvailEnd=? and not recordid=?");
        checkStmt.setInt(1, employeeID);
        checkStmt.setInt(2, theDay.ordinal());
        checkStmt.setTime(3, theTimeBegin);
        checkStmt.setTime(4, theTimeEnd);
        checkStmt.setInt(5, recordID);
        ResultSet checkrs=checkStmt.executeQuery();
        return checkrs.next();
    }    
    
    public static boolean scheduleConflictMeeting(int employeeID, WeekDay theDay, Time theTimeBegin, Time theTimeEnd){
        Vector<Meeting> meetingList= MainProgram.getMeetingList(Meeting.SHOW_PARTICIPANT, employeeID);
        int dayOfWeek=theDay.ordinal();
        System.out.println(dayOfWeek);
        for(int i=0;i<meetingList.size();i++){
            if(meetingList.get(i).getMeetingDate().getDay()==dayOfWeek){
                if(timeOverlap(theTimeBegin,theTimeEnd,meetingList.get(i).getTimeBegin(),meetingList.get(i).getTimeEnd())){
                    return true;
                }
            }
                 
        }
        return false;        
    }

    public static void dropConflictMeeting(int employeeID, WeekDay theDay, Time theTimeBegin, Time theTimeEnd) throws SQLException{
        Vector<Meeting> meetingList= MainProgram.getMeetingList(Meeting.SHOW_PARTICIPANT, employeeID);
        int dayOfWeek=theDay.ordinal();
        System.out.println(dayOfWeek);
        for(int i=0;i<meetingList.size();i++){
            if(meetingList.get(i).getMeetingDate().getDay()==dayOfWeek){
                if(timeOverlap(theTimeBegin,theTimeEnd,meetingList.get(i).getTimeBegin(),meetingList.get(i).getTimeEnd())){
                    MainProgram.getMPC().removeMeetingParticipant(employeeID, meetingList.get(i).getMeetingID());
                }
            }
                 
        }      
    }    
    
    public static boolean timeOverlap(java.util.Date begin, java.util.Date end, java.util.Date secondBegin, java.util.Date secondEnd){
                if(compareTime(begin, secondBegin)<=0&&
                        WeeklySchedule.compareTime(end, secondBegin)>=0){
                    return true;
                }
                if(compareTime(begin, secondEnd)<=0&&
                        WeeklySchedule.compareTime(end, secondEnd)>=0){
                    return true;
                }        
                if(compareTime(begin, secondBegin)>=0&&
                        WeeklySchedule.compareTime(end, secondEnd)<=0){
                    return true;
                }             
                if(compareTime(begin, secondBegin)<=0&&
                        WeeklySchedule.compareTime(end, secondEnd)>=0){
                    return true;
                }                      
                return false;
    }
    
    public static boolean checkScheduleValid(Time timeBegin,Time timeEnd){
        return compareTime(timeBegin,timeEnd)<0;
    }
    
    public static int compareTime(java.util.Date t1, java.util.Date t2){
        int t1int=t1.getHours()*3600+t1.getMinutes()*60+t1.getSeconds();
        int t2int=t2.getHours()*3600+t2.getMinutes()*60+t2.getSeconds();
        return t1int-t2int;
    }
    
    public static boolean dayEqual(java.util.Date d1,java.util.Date d2){
        return d1.getYear()==d2.getYear() && d1.getMonth()==d2.getMonth() && d1.getDate()==d2.getDate();
    }
//    public static WeekDay convertIntToWeek(int num){
//        return WeekDay.values()[num];
//    }
    
}
