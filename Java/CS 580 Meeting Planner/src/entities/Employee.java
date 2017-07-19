package entities;
import functions.*;
import java.sql.*;
import java.util.Vector;
import java.sql.Time;

public class Employee {
    private int employeeID;
    private String userName;
    private String ssn;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String department;
    private boolean isAdmin;
    private Vector<WeeklySchedule> mySchedule;
    private boolean scheduleLoaded=false;
    public Employee(int employeeID, String userName, String ssn, String name, String address, String phoneNumber, String email, String department, boolean isAdmin) {
        this.employeeID = employeeID;
        this.userName = userName;
        this.ssn = ssn;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.department = department;
        this.isAdmin = isAdmin;
    }

    public boolean fetchScheduleFromDB(){
        Vector<WeeklySchedule> newSchedule = new Vector<WeeklySchedule>();
        try{
            Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=stmt.executeQuery("select * from WeeklySchedule where EmployeeID = "+employeeID+" order by WhichDay,TimeUnavailBegin");        
            while(rs.next()){
                newSchedule.add(new WeeklySchedule(rs.getInt(1),rs.getInt(2),WeekDay.values()[rs.getInt(3)],rs.getTime(4),rs.getTime(5)));
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        mySchedule=newSchedule;
        scheduleLoaded=true;
        return true;
    }
    
    public String getAddress() {
        return address;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public boolean isItAdmin() {
        return isAdmin;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSSN() {
        return ssn;
    }

    public String getUserName() {
        return userName;
    }

    public Vector<WeeklySchedule> getMySchedule() {
        if(!scheduleLoaded) fetchScheduleFromDB();
        return mySchedule;
    }

    public void setMySchedule(Vector<WeeklySchedule> mySchedule) {
        this.mySchedule = mySchedule;
    }

    public boolean DeleteWeeklySchedule(int recordID){
        try{
            Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate("delete from WeeklySchedule where recordID = "+recordID);
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        for(int i=0;i<mySchedule.size();i++){
            if(mySchedule.get(i).getRecordID()==recordID){
                mySchedule.removeElementAt(i);
                return true;
            }
        }
        return false;
    }
    
    public void updateInfo(String address,String department,String email,String name,
            String phoneNumber,String ssn) throws InvalidInfoException, SQLException{  
        int code=helperCheckDuplicateInfo(ssn,email);
        if(code!=0){
            throw new InvalidInfoException(code);
        }
            PreparedStatement updatestmt = MainProgram.getConnection().prepareStatement("update employee SET address=?,department=?,email=?,name=?,phonenumber=?,ssn=? where employeeID=?");
            updatestmt.setString(1, address);
            updatestmt.setString(2, department);
            updatestmt.setString(3, email);
            updatestmt.setString(4, name);
            updatestmt.setString(5, phoneNumber);
            updatestmt.setString(6, ssn);
            updatestmt.setInt(7, employeeID);
            updatestmt.executeUpdate();
        this.address = address;
        this.department = department;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ssn = ssn;            
    }
    
    public static int checkIfInfoUnique(String username, String ssn, String email) throws SQLException{
       int returnVal=0;
        PreparedStatement checkStmt=MainProgram.getConnection().prepareStatement(
                "select * from employee where ssn=?");
        checkStmt.setString(1, ssn);
        ResultSet checkrs=checkStmt.executeQuery();
        if(checkrs.next()){
            returnVal |= InvalidInfoException.SSNEXISTS;
        }
        checkStmt=MainProgram.getConnection().prepareStatement(
                "select * from employee where email=?");
        checkStmt.setString(1, email);
        checkrs=checkStmt.executeQuery();
        if(checkrs.next()){
            returnVal |= InvalidInfoException.EMAILEXISTS;
        }        
        checkStmt=MainProgram.getConnection().prepareStatement(
                "select * from employee where username=?");
        checkStmt.setString(1, username);
        checkrs=checkStmt.executeQuery();
        if(checkrs.next()){
            returnVal |= InvalidInfoException.USERNAMEEXISTS;
        }        
        return returnVal;        
    }
    
    private int helperCheckDuplicateInfo(String ssn, String email) throws SQLException{
        int returnVal=0;
        PreparedStatement checkStmt=MainProgram.getConnection().prepareStatement(
                "select * from employee where ssn=? and not employeeid="+employeeID);
        checkStmt.setString(1, ssn);
        ResultSet checkrs=checkStmt.executeQuery();
        if(checkrs.next()){
            returnVal |= InvalidInfoException.SSNEXISTS;
        }
        checkStmt=MainProgram.getConnection().prepareStatement(
                "select * from employee where email=? and not employeeid="+employeeID);
        checkStmt.setString(1, email);
        checkrs=checkStmt.executeQuery();
        if(checkrs.next()){
            returnVal |= InvalidInfoException.EMAILEXISTS;
        }        
        return returnVal;
    }
    
    public boolean updatePassword(String oldPasswd,String newPasswd){
        //pseudocode:
        //get the md5sum password from the database
        //if(!checkPassword(oldPasswd,md5sumStr)) return false; //old password doesn't match
        //get the md5sum of newPasswd, store it in database and return true
        try{
            PreparedStatement stmt = MainProgram.getConnection().prepareStatement(
                    "select password from employee where employeeID=?");
            stmt.setInt(1, employeeID);
            ResultSet rs=stmt.executeQuery();
            rs.next();
            if(!PasswordHash.checkPassword(oldPasswd,rs.getString(1))) return false; //old password doesn't match
            else{
                PreparedStatement stmtUpdate = MainProgram.getConnection().prepareStatement(
                        "update employee set password=? where employeeID=?");
                stmtUpdate.setString(1, PasswordHash.getMD5sum(newPasswd));
                stmtUpdate.setInt(2, employeeID);
                stmtUpdate.executeUpdate();            
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public WeeklySchedule createSchedule(WeekDay whichDay, Time timeUnAvailBegin, Time timeUnAvailEnd) 
            throws InvalidScheduleException{
        if(!WeeklySchedule.checkScheduleValid(timeUnAvailBegin, timeUnAvailEnd)){     
            throw new InvalidScheduleException("time end can't be before or equals time begin");
        }   
        try{
        if(WeeklySchedule.scheduleAlreadyExists(employeeID, whichDay, timeUnAvailBegin, timeUnAvailEnd)){
            System.err.print("schedule already exists");
            throw new InvalidScheduleException("schedule already exists");
        }
        WeeklySchedule.dropConflictMeeting(employeeID, whichDay, timeUnAvailBegin, timeUnAvailEnd);
        PreparedStatement updateStmt=MainProgram.getConnection().prepareStatement(
                "insert into weeklyschedule(employeeid,whichday,timeunavailbegin,timeunavailend) values(?,?,?,?)");
        updateStmt.setInt(1, employeeID);
        updateStmt.setInt(2, whichDay.ordinal());
        updateStmt.setTime(3, timeUnAvailBegin);
        updateStmt.setTime(4, timeUnAvailEnd);
        updateStmt.executeUpdate();
        Statement searchStmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=searchStmt.executeQuery("select @@identity");
        rs.next();
        int recordID=rs.getInt(1);
        System.out.println("recordID gotten for schedule"+recordID);
        WeeklySchedule toReturn=new WeeklySchedule(recordID,employeeID,whichDay,timeUnAvailBegin,timeUnAvailEnd);
        return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public Meeting createMeeting(Room meetingRoom, Date meetingDate, Time timeBegin, Time timeEnd, Vector<TimeSlot> timeslot,Vector<Employee> participants)
        throws ScheduleConflictException, SQLException, InvalidScheduleException{
        if(!WeeklySchedule.checkScheduleValid(timeBegin, timeEnd)) throw new InvalidScheduleException("time begin can't be >= time end");
        if(Meeting.checkTimeSlotScheduleConflict(meetingDate, timeBegin, timeEnd, timeslot)) throw new ScheduleConflictException("participants");
        if(Meeting.checkRoomScheduleConflict(meetingRoom, meetingDate, timeBegin, timeEnd)) throw new ScheduleConflictException("room");
        PreparedStatement createStmt=MainProgram.getConnection().prepareStatement(
                "insert into meeting(ownerid,roomid,meetingdate,meetingtimebegin,meetingtimeend) values(?,?,?,?,?)");        
        createStmt.setInt(1, employeeID);
        createStmt.setInt(2, meetingRoom.getRoomID());
        createStmt.setDate(3, meetingDate);
        createStmt.setTime(4, timeBegin);
        createStmt.setTime(5, timeEnd);
        createStmt.executeUpdate();
        Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=stmt.executeQuery("select @@identity");
        rs.next();
        int recordID=rs.getInt(1);
        System.out.println("recordID gotten for meeting"+recordID);

        Meeting created= new Meeting(recordID,this,meetingRoom,meetingDate,timeBegin,timeEnd);
        for(int i=0;i<participants.size();i++){
            MainProgram.getMPC().addMeetingParticipant(participants.get(i), created);
        }
        MainProgram.getMeetingList().add(created);
        return created;
    }
    
    public static void cancelMeeting(int meetingID) throws SQLException{
            Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate("delete from Meeting where meetingID = "+meetingID);
            //MainProgram.removeMeetingByID(meetingID);
            MainProgram.getMPC().removeMeetingFromMPC(meetingID);
        Vector<Meeting> meetingList=MainProgram.getMeetingList();
        for(int i=0;i<meetingList.size();i++){
            if(meetingList.get(i).getMeetingID()==meetingID) {
                meetingList.remove(i);
                i--;
            }
        }        
    }
    
    public boolean stillHasMeeting(){
        Vector<Meeting> meetingList=MainProgram.getMeetingList();
        for(int i=0;i<meetingList.size();i++){
            if(meetingList.get(i).getOwner().getEmployeeID()==employeeID){
                return true;
            }
        }        
        return false;
    }
    
    public String toString(){
            return "Employee Info: "+"\n"+
                    employeeID+"\n"+
                    userName+"\n"+
                    ssn+"\n"+
                    name+"\n"+
                    address+"\n"+
                    phoneNumber+"\n"+
                    email+"\n"+
                    department+"\n"+
                    isAdmin+"\n";
    }
    
}
