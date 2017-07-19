package entities;

import functions.*;
import java.util.Vector;
import java.sql.*;

public class Meeting {
    private int meetingID;
    private Employee owner;
    private Room meetingRoom;
    private java.sql.Date meetingDate;
    private java.sql.Time timeBegin;
    private java.sql.Time timeEnd;
    public static final int SHOW_OWNED=1;
    public static final int SHOW_PARTICIPANT=2;
    public static final int SHOW_ALL=3;

    public Meeting(int meetingID, Employee owner, Room meetingRoom, Date meetingDate, Time timeBegin, Time timeEnd) {
        this.meetingID = meetingID;
        this.owner = owner;
        this.meetingRoom = meetingRoom;
        this.meetingDate = meetingDate;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
    }

  
    public void updateMeeting(Room meetingRoom, Date meetingDate, Time timeBegin, Time timeEnd, Vector<TimeSlot> timeslot,Vector<Employee> participants)
        throws ScheduleConflictException, SQLException, InvalidScheduleException{
        if(!WeeklySchedule.checkScheduleValid(timeBegin, timeEnd)) throw new InvalidScheduleException("time begin can't be >= time end");
        if(checkTimeSlotScheduleConflict(meetingDate, timeBegin, timeEnd, timeslot)) throw new ScheduleConflictException("participants");
        if(checkRoomScheduleConflictOther(meetingRoom, meetingDate, timeBegin, timeEnd)) throw new ScheduleConflictException("room");
            MainProgram.getMPC().removeMeetingFromMPC(meetingID);
        PreparedStatement createStmt=MainProgram.getConnection().prepareStatement(
                "update meeting set roomid=?,meetingdate=?,meetingtimebegin=?,meetingtimeend=? where meetingid="+meetingID);        

        createStmt.setInt(1, meetingRoom.getRoomID());
        createStmt.setDate(2, meetingDate);
        createStmt.setTime(3, timeBegin);
        createStmt.setTime(4, timeEnd);
        createStmt.executeUpdate();
        for(int i=0;i<participants.size();i++){
            MainProgram.getMPC().addMeetingParticipant(participants.get(i), this);
        }
        this.meetingRoom=meetingRoom;
        this.meetingDate=meetingDate;
        this.timeBegin=timeBegin;
        this.timeEnd=timeEnd;
    }    
    
    public void inviteEmployee(Employee em)throws SQLException,EmployeeAlreadyInMeetingException,
            ScheduleConflictException {
        if(MainProgram.getMPC().isEmployeeInMeeting(em, this)){
            throw new EmployeeAlreadyInMeetingException();
        }
        if(doesScheduleConflict(em.getMySchedule())){
            throw new ScheduleConflictException("employee schedule");
        }
        if(checkMeetingScheduleConflict(em,meetingDate,timeBegin,timeEnd)){
            throw new ScheduleConflictException("meeting participant schedule");
        }
        MainProgram.getMPC().addMeetingParticipant(em, this);
    }
    
    public static boolean checkMeetingScheduleConflict(Employee participant,java.util.Date meetingDate,java.util.Date timeUnavailbegin,java.util.Date timeUnavailend){
        Vector<Meeting> m=MainProgram.getMeetingList(Meeting.SHOW_PARTICIPANT, participant.getEmployeeID());
        for(int i=0;i<m.size();i++){
            if(WeeklySchedule.dayEqual(meetingDate, m.get(i).getMeetingDate())&&WeeklySchedule.timeOverlap(timeUnavailbegin, timeUnavailend, m.get(i).getTimeBegin(),
                    m.get(i).getTimeEnd())){
                return true;
            }
        }
        return false;
    }         
    
    public boolean doesScheduleConflict(Vector<WeeklySchedule> sche){
        int dayOfWeek=meetingDate.getDay();
        System.out.println(dayOfWeek);
        for(int i=0;i<sche.size();i++){
            if(sche.get(i).getWhichDay().ordinal()==dayOfWeek){
                if(WeeklySchedule.timeOverlap(sche.get(i).getTimeUnAvailBegin(), sche.get(i).getTimeUnAvailEnd(), timeBegin, timeEnd)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean checkRoomScheduleConflict(Room meetingRoom, Date meetingDate, Time timeBegin, Time timeEnd)
        throws SQLException{
            Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);        
            ResultSet rs=stmt.executeQuery("select * from roomschedule where roomid="+meetingRoom.getRoomID());
            while(rs.next()){
                if(WeeklySchedule.dayEqual(meetingDate, rs.getDate("MeetingDate"))&&
                        WeeklySchedule.timeOverlap(timeBegin, timeEnd, rs.getTime("MeetingTimeBegin"), rs.getTime("MeetingTimeEnd")))
                {    
                    return true;
                }
            }        
            return false;
    }

    public boolean checkRoomScheduleConflictOther(Room meetingRoom, Date meetingDate, Time timeBegin, Time timeEnd)
        throws SQLException{
             Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);        
            ResultSet rs=stmt.executeQuery("select * from roomschedule where roomid="+meetingRoom.getRoomID()+"and not meetingid="+meetingID);
            while(rs.next()){
                if(WeeklySchedule.dayEqual(meetingDate, rs.getDate("MeetingDate"))&&
                        WeeklySchedule.timeOverlap(timeBegin, timeEnd, rs.getTime("MeetingTimeBegin"), rs.getTime("MeetingTimeEnd")))
                {    
                    return true;
                }
            }        
            return false;
    }    
    
    public static boolean checkTimeSlotScheduleConflict(Date meetingDate, Time timeBegin, Time timeEnd,Vector<TimeSlot> timeslot){
        for(int i=0;i<timeslot.size();i++){
            if(WeeklySchedule.timeOverlap(timeBegin, timeEnd, timeslot.get(i).getTimeBegin(), timeslot.get(i).getTimeEnd())){
                return true;
            }
        }
        return false;
    }    
    
    public Date getMeetingDate() {
        return meetingDate;
    }

    public int getMeetingID() {
        return meetingID;
    }

    public Room getMeetingRoom() {
        return meetingRoom;
    }

    public Employee getOwner() {
        return owner;
    }

    public Time getTimeBegin() {
        return timeBegin;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }
    
}
