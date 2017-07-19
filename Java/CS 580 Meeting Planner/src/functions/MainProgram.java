package functions;
import gui.*;
import java.sql.*;
import entities.*;
import java.util.Vector;

public class MainProgram {
    private static Connection con;
    private static Vector<Employee> emList;
    private static Vector<Room> roomList;
    private static Vector<Meeting> meetingList;
    private static MeetingParticipantControl mpc;
    private static MeetingPlannerClient client;
    private static MeetingPlannerServer server;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8888;
    public static Connection getConnection(){
        return con;
    }
    
    private static void setupClasses(){
        emList = new Vector<Employee>();
        try{
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);        
            ResultSet rs=stmt.executeQuery("select * from employee");
            while(rs.next()){
                emList.add(new Employee(rs.getInt("employeeID"),
                    rs.getString("userName"),
                    rs.getString("ssn"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phoneNumber"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getBoolean("isAdmin")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }        
        roomList = new Vector<Room>();
        try{
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);        
            ResultSet rs=stmt.executeQuery("select * from room");
            while(rs.next()){
                roomList.add(new Room(rs.getInt(1),rs.getString(2)));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }        
        meetingList = new Vector<Meeting>();
        try{
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=stmt.executeQuery("select * from Meeting order by MeetingDate,MeetingTimeBegin");        
            while(rs.next()){
                meetingList.add(new Meeting(rs.getInt(1),getEmployeeByID(rs.getInt(2)),getRoomByID(rs.getInt(3)),rs.getDate(4),rs.getTime(5),rs.getTime(6)));
            }
            mpc=new MeetingParticipantControl();
        }catch(SQLException e){
            e.printStackTrace();
        }               
        
        server = new MeetingPlannerServer();
        new Thread(server).start();
        client = new MeetingPlannerClient(HOST, PORT);
    }
    
    
    public static Employee login(String username,String password) throws InvalidLoginException,SQLException{
        Employee temp;
        Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=stmt.executeQuery("select * from employee where username = '"+username+"'");
        if(!rs.next()){
            throw new InvalidLoginException("Username not found");
        }
        String md5pass=rs.getString("password");
        //check if password is correct
        if(PasswordHash.checkPassword(password,md5pass)){
            temp=getEmployeeByID(rs.getInt("employeeID"));
            //close the window and return the employee object
            //doClose(temp);
        }else{
            throw new InvalidLoginException("Invalid password");
        }
        temp.fetchScheduleFromDB();
        return temp;
    }       
    
    public static Employee getEmployeeByID(int employeeID){
        //Employee em;
        for(int i=0;i<emList.size();i++){
            if(emList.get(i).getEmployeeID()==employeeID) return emList.get(i);
        }
        System.out.println("null id"+employeeID);
        return null;        
    }
    
    public static Meeting getMeetingByID(int meetingID){
        //Employee em;
        for(int i=0;i<meetingList.size();i++){
            if(meetingList.get(i).getMeetingID()==meetingID) return meetingList.get(i);
        }
        return null;        
    }   
    
    public static Vector<Employee> getEmployeeList(){
        return emList;
    }

    public static Vector<Employee> getEmployeeListCopied(){
        Vector<Employee> emListNew=new Vector<Employee>();
        for(int i=0;i<emList.size();i++){
            emListNew.add(emList.get(i));
        }
        return emListNew;
    }    
    
    public static Vector<Room> getRoomList(){
        return roomList;
    }
    
    public static Room getRoomByID(int roomID) throws SQLException{
        for(int i=0;i<roomList.size();i++){
            if(roomList.get(i).getRoomID()==roomID) return roomList.get(i);
        }
        return null;    
    }
    
    //get list of all meetings
    public static Vector<Meeting> getMeetingList(){
        return meetingList;
    }

    public static Vector<Meeting> getMeetingList(int showOption, int employeeID){
        if(showOption==Meeting.SHOW_ALL) return getMeetingList();
        Vector<Meeting> ret = new Vector<Meeting>();
            switch(showOption){
                case Meeting.SHOW_OWNED:
                    for(int i=0;i<meetingList.size();i++){
                        if(meetingList.get(i).getOwner().getEmployeeID()==employeeID){
                            ret.add(meetingList.get(i));
                        }
                    }
                    break;
                case Meeting.SHOW_PARTICIPANT:  
                    for(int i=0;i<mpc.getWrap().size();i++){
                        MeetingParticipant temp=mpc.getWrap().get(i);
                        System.out.println(temp.getParticipant());
                        if(temp.getParticipant().getEmployeeID()==employeeID){
                            ret.add(mpc.getWrap().get(i).getMeeting());
                        }
                    }      
                    break;
            }
        return ret;
    }    

    public static Vector<Meeting> getMeetingListByRoom(int roomID){
        Vector<Meeting> ret = new Vector<Meeting>();

                    for(int i=0;i<meetingList.size();i++){
                        if(meetingList.get(i).getMeetingRoom().getRoomID()==roomID){
                            ret.add(meetingList.get(i));
                        }
                    }

        return ret;
    }        
    
    public static Vector<Room> getRoomsAvailable(java.util.Date day, java.util.Date timeBegin,java.util.Date timeEnd){
        Vector<Room> r = new Vector<Room>();
        for(int i=0;i<roomList.size();i++){
            r.add(roomList.get(i));
        }
        //int dayOfWeek=day.getDay();
        try{
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);        
            ResultSet rs=stmt.executeQuery("select * from roomschedule");
            while(rs.next()){
                if(WeeklySchedule.dayEqual(day, rs.getDate("MeetingDate"))&&
                        WeeklySchedule.timeOverlap(timeBegin, timeEnd, rs.getTime("MeetingTimeBegin"), rs.getTime("MeetingTimeEnd")))
                {    
                    String roomName=rs.getString(2);
                    System.out.println("removing unavai room:"+roomName);
                    System.out.println("removed?"+r.remove(new Room(rs.getInt(1),roomName)));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return r;
    }
// getRoomsAvailable with minCapacity parameter    
    public static Vector<Room> getRoomsAvailable(java.util.Date day, java.util.Date timeBegin,java.util.Date timeEnd, int minCapacity){
        Vector<Room> r = new Vector<Room>();
        for(int i=0;i<roomList.size();i++){
            r.add(roomList.get(i));
        }
        //int dayOfWeek=day.getDay();
        try{
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);        
            ResultSet rs=stmt.executeQuery("select * from roomschedule");
            while(rs.next()){
                if(WeeklySchedule.dayEqual(day, rs.getDate("MeetingDate"))&&
                        WeeklySchedule.timeOverlap(timeBegin, timeEnd, rs.getTime("MeetingTimeBegin"), rs.getTime("MeetingTimeEnd")))
                {    
                    String roomName=rs.getString(2);
                    System.out.println("removing unavai room:"+roomName);
                    System.out.println("removed?"+r.remove(new Room(rs.getInt(1),roomName)));
                }
            }
// check capacity            
            for(int i = 0; i < r.size(); i++)
                if(minCapacity > r.get(i).getRoomCapacity()) r.remove(i);
            
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return r;
    }
    
    public static Vector<TimeSlot> computeTimeSlot(java.util.Date meetingDate,Meeting meetingExclude,Vector<Employee> employeeList){
        Vector<TimeSlot> unavailTSDay=new Vector<TimeSlot>();
        //add the time unavailable based on the weekly schedules of each employee to unavailTSDay
        for(int i=0;i<employeeList.size();i++){
            Vector<WeeklySchedule> sche=employeeList.get(i).getMySchedule();
            for(int j=0;j<sche.size();j++){
                if(meetingDate.getDay()==sche.get(j).getWhichDay().ordinal())
                    unavailTSDay.add(new TimeSlot(sche.get(j).getTimeUnAvailBegin(),sche.get(j).getTimeUnAvailEnd()));
            }
            //get the list of meeting that the employees are participating
        Vector<Meeting> m=MainProgram.getMeetingList(Meeting.SHOW_PARTICIPANT, employeeList.get(i).getEmployeeID());
        //add the meeting times of the meeting list as unavailable time to unavailTSDay
        //if we are modifying meeting, we set meetingExclude to the current meeting that we are going to modify
        //so that we do not count its schedule as unavailable time
        //if we are creating meeting, we set meetingExclude=null
        //if we are modifying meeting, we set meetingExclude to the meeting we are modifying
        if(meetingExclude==null){
            //create meeting
            for(int k=0;k<m.size();k++){
                //if meeting date same, add it to time unavailable
                if(WeeklySchedule.dayEqual(meetingDate, m.get(k).getMeetingDate())){
                    unavailTSDay.add(new TimeSlot(m.get(k).getTimeBegin(),m.get(k).getTimeEnd()));
                }
            }      
        }else{
            //modify meeting
            for(int k=0;k<m.size();k++){
                //if meeting date same, and it's not the meeting we are going to exclude,
                //add its schedule to time unavailable
                if(m.get(k).getMeetingID()!=meetingExclude.getMeetingID()&&WeeklySchedule.dayEqual(meetingDate, m.get(k).getMeetingDate())){
                    unavailTSDay.add(new TimeSlot(m.get(k).getTimeBegin(),m.get(k).getTimeEnd()));
                }
            }
        }

        }
        //sort and merge the time slots
        unavailTSDay=mergeOverlap(unavailTSDay);
            return unavailTSDay;
    }
    
    private static Vector<TimeSlot> mergeOverlap(Vector<TimeSlot> ts){
            improvedBubbleSort(ts);
            for(int i=0;i<ts.size();i++) System.out.println(ts.get(i).getTimeBegin()+":"+ts.get(i).getTimeEnd());
            Vector<TimeSlot> newOne=new Vector<TimeSlot>();
            if(ts==null||ts.size()==0) return new Vector<TimeSlot>();
            newOne.add(ts.get(0));
            //while it still overlaps, merge
            for(int j=1;j<ts.size();j++){
                if(WeeklySchedule.compareTime(ts.get(j).getTimeBegin(),ts.get(j-1).getTimeEnd())<=0){
                    java.util.Date d=ts.get(j).getTimeEnd();
                    newOne.get(newOne.size()-1).setTimeEnd(d);
                }else{
                    newOne.add(ts.get(j));
                }
            }
            return newOne;
    }
    
    public static void improvedBubbleSort(Vector<TimeSlot> items) {
        if(items==null) return;
       int k = 0;
       boolean exchangeMade = true;
       while (exchangeMade && k < items.size() - 1) {
          exchangeMade = false;
          // Number of comparisons on kth pass
          for (int j = 0; j < items.size() - (k+1); j++)
             if (WeeklySchedule.compareTime(items.get(j).getTimeBegin(), items.get(j + 1).getTimeBegin())>0) {
                 TimeSlot temp=items.get(j);
                 items.set(j, items.get(j + 1));
                 items.set(j+1,temp);
                //swap(items, j, j + 1); 
                exchangeMade = true;
              }
           k++;
       }
    }

    public static void main(String[] args) throws Exception{
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            // set this to a MS Access DB you have on your machine
            java.io.File f = new java.io.File("cs580.mdb");
            System.out.println(f.getAbsolutePath());
            String database; 
            database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="+f.getAbsolutePath()+";readOnly=false"; // add on to the end 
            // now we can get the connection from the DriverManager
            try {
                con = DriverManager.getConnection(database ,"",""); 
            } catch(SQLException e)
            {
                database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="+f.getAbsolutePath()+";readOnly=false"; // add on to the end 
                con = DriverManager.getConnection(database ,"","");
            }
            setupClasses();

        for(int i=0;i<emList.size();i++){
            System.out.println(emList.get(i).getName());
        }

        for(int i=0;i<roomList.size();i++){
            System.out.println(roomList.get(i).getRoomName());
        }            

        for(int i=0;i<meetingList.size();i++){
            System.out.println(meetingList.get(i).getMeetingDate());
        }            

                //get the employee object from login window
                Employee em = new LoginWindow(null, true).openDialog();
                System.out.println(em);
                if(em!=null){
                    EmployeeDialog diag=new EmployeeDialog(null,true,em);
                    diag.setLocationRelativeTo(null);
                    diag.setVisible(true);
                }
                //close the connection
                System.out.println("closing the sql connection");
                con.close();
                System.out.println("closing socket connection");
                client.close();
                server.close();
    }

    public static MeetingParticipantControl getMPC() {
        return mpc;
    }

    public static Vector<Employee> getEmList() {
        return emList;
    }

    public static void sendEmail(Meeting meeting, boolean isModify) {
        System.out.println("SEND EMAIL");
//        client.sendEmail((isModify)?1:0, meeting.getOwner().getEmployeeID(), meeting.getTimeBegin(), meeting.getMeetingRoom().getRoomID());
        client.sendEmail((isModify)?1:0, meeting.getMeetingID());
    }
}
