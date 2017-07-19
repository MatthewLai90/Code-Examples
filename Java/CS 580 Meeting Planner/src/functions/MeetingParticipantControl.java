package functions;
import java.util.Vector;
import entities.*;
import java.sql.*;

public class MeetingParticipantControl {
    private Vector<MeetingParticipant> wrap;

    public Vector<MeetingParticipant> getWrap() {
        return wrap;
    }
    public MeetingParticipantControl() throws SQLException{
        wrap=new Vector<MeetingParticipant>();
            Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            String query="select * from meetingparticipantslist";
            ResultSet rs=stmt.executeQuery(query);        
            while(rs.next()){
                wrap.add(new MeetingParticipant(MainProgram.getEmployeeByID(rs.getInt(2)),
                        MainProgram.getMeetingByID(rs.getInt(3))));
            }
    }

    public Vector<Employee> getParticipantList(Meeting meet){
        Vector<Employee> ret = new Vector<Employee>();
        System.out.println("total participants:"+wrap.size());
        for(int i=0;i<wrap.size();i++){
            if(wrap.get(i).getMeeting().getMeetingID()==meet.getMeetingID()){
                ret.add(wrap.get(i).getParticipant());
            }
        }
        return ret;
    }    
    
    public Vector<Meeting> getMeetingList(Employee participant){
                Vector<Meeting> ret = new Vector<Meeting>();
                    for(int i=0;i<wrap.size();i++){
                        MeetingParticipant temp=wrap.get(i);
                        System.out.println(temp.getParticipant());
                        if(temp.getParticipant().getEmployeeID()==participant.getEmployeeID()){
                            ret.add(wrap.get(i).getMeeting());
                        }
                    }        
                return ret;
    }    
    
    public boolean isEmployeeInMeeting(Employee em,Meeting m){
        for(int i=0;i<wrap.size();i++){
            MeetingParticipant temp=wrap.get(i);
            if(temp.getParticipant().getEmployeeID()==em.getEmployeeID() &&temp.getMeeting().getMeetingID()==m.getMeetingID()){
                return true;
            }
        }
        return false;
    }
    
    public void addMeetingParticipant(Employee e,Meeting m) throws SQLException{
        Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        stmt.executeUpdate("insert into meetingparticipantslist(employeeID,meetingID) values("+e.getEmployeeID()+","+m.getMeetingID()+")");        
        wrap.add(new MeetingParticipant(e,m));
    }
    
    public void removeMeetingFromMPC(int meetingID) throws SQLException{
            Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate("delete from MeetingParticipantslist where meetingID = "+meetingID);        
        for(int i=0;i<wrap.size();i++){
            if(wrap.get(i).getMeeting().getMeetingID()==meetingID){
                wrap.remove(i);
                i--;
            }
        }        
    }

    public void removeMeetingParticipant(int employeeID,int meetingID) throws SQLException{
            Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate("delete from MeetingParticipantslist where employeeID="+employeeID+" and meetingID = "+meetingID);        
        for(int i=0;i<wrap.size();i++){
            if(wrap.get(i).getMeeting().getMeetingID()==meetingID
                    &&wrap.get(i).getParticipant().getEmployeeID()==employeeID){
                wrap.remove(i);
                return;
            }
        }        
    }    
    
    public void removeParticipantFromMPC(int participantID) throws SQLException{
            Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate("delete from MeetingParticipantslist where employeeID = "+participantID);        
        for(int i=0;i<wrap.size();i++){
            if(wrap.get(i).getParticipant().getEmployeeID()==participantID){
                wrap.remove(i);
                i--;
            }
        }        
    }    
}
