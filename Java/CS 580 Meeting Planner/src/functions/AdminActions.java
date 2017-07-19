package functions;
import entities.*;
import java.sql.*;
import java.util.Vector;

public class AdminActions {
    public static Employee createEmployee(String userName, String ssn, String name, String address, 
            String phoneNumber, String email, String department) throws SQLException, InvalidInfoException{
        int duplicatecode=Employee.checkIfInfoUnique(userName, ssn, email);
        if(duplicatecode>0){
            throw new InvalidInfoException(duplicatecode);
        }
        String passinsert=PasswordHash.getMD5sum(userName);
        PreparedStatement createstmt = MainProgram.getConnection().prepareStatement("insert into employee(userName, ssn, name, address, phoneNumber, email, department,password) values(?,?,?,?,?,?,?,?)");
        createstmt.setString(1, userName);
        createstmt.setString(2, ssn);
        createstmt.setString(3, name);
        createstmt.setString(4, address);
        createstmt.setString(5, phoneNumber);
        createstmt.setString(6, email);
        createstmt.setString(7, department);
        createstmt.setString(8, passinsert);
        createstmt.executeUpdate();
        Statement idstmt=MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs2=idstmt.executeQuery("select @@identity");
        rs2.next();
        int employeeID=rs2.getInt(1);
        System.out.println("employeeID gotten for schedule"+employeeID);     
        Employee em=new Employee(employeeID,userName, ssn, name, address, phoneNumber, email, department,false);
        MainProgram.getEmList().add(em);
        return em;
    }
    
    public static void deleteEmployee(int employeeID) throws SQLException, DeleteAdminException{
        //check if the user is admin
        PreparedStatement checkstmt = MainProgram.getConnection().prepareStatement("select isAdmin from employee where employeeID=?");
        checkstmt.setInt(1, employeeID);
        ResultSet rs=checkstmt.executeQuery();
        rs.next();
        if(rs.getBoolean(1)){
            throw new DeleteAdminException();
        }
        //delete employee from database
        PreparedStatement delstmt = MainProgram.getConnection().prepareStatement("delete from employee where employeeID=?");        
        delstmt.setInt(1, employeeID);
        delstmt.executeUpdate();
        //delete weekly schedule
        delstmt = MainProgram.getConnection().prepareStatement("delete from weeklyschedule where employeeID=?");        
        delstmt.setInt(1, employeeID);
        delstmt.executeUpdate();

        //delete participating
        MainProgram.getMPC().removeParticipantFromMPC(employeeID);
        
        //delete owned meetings
        Vector<Meeting> m=MainProgram.getMeetingList(Meeting.SHOW_OWNED, employeeID);
        for(int i=0;i<m.size();i++){
            Employee.cancelMeeting(m.get(i).getMeetingID());
        }
        Vector<Employee> employeeList=MainProgram.getEmployeeList();
        for(int i=0;i<employeeList.size();i++){
            if(employeeList.get(i).getEmployeeID()==employeeID) {
                employeeList.remove(i);
                break;
            }
        }
        
        //return true;
    }
    
    public static Room createRoom(String roomName) throws SQLException, RoomExistsException{
        Room newRoom=null;
        PreparedStatement searchStmt = MainProgram.getConnection().prepareStatement("select * from room where roomName=?");
        searchStmt.setString(1, roomName);
        ResultSet rs=searchStmt.executeQuery();
        if(rs.next()) {
            throw new RoomExistsException();
        }
        PreparedStatement createStmt = MainProgram.getConnection().prepareStatement("insert into room(roomName) values(?)");
        createStmt.setString(1, roomName);
        createStmt.executeUpdate();
        Statement idstmt=MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs2=idstmt.executeQuery("select @@identity");
        rs2.next();
        int roomID=rs2.getInt(1);
        System.out.println("recordID gotten for schedule"+roomID);      
        newRoom=new Room(roomID,roomName);
        MainProgram.getRoomList().add(newRoom);
        return newRoom;
    }
// createRoom with capacity support
    public static Room createRoom(String roomName, int capacity) throws SQLException, RoomExistsException{
        Room newRoom=null;
        PreparedStatement searchStmt = MainProgram.getConnection().prepareStatement("select * from room where roomName=?");
        searchStmt.setString(1, roomName);
        ResultSet rs=searchStmt.executeQuery();
        if(rs.next()) {
            throw new RoomExistsException();
        }
        PreparedStatement createStmt = MainProgram.getConnection().prepareStatement("insert into room(roomName,capacity) values(?,?)");
        createStmt.setString(1, roomName);
        createStmt.setInt(2, capacity);
        createStmt.executeUpdate();
        Statement idstmt=MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs2=idstmt.executeQuery("select @@identity");
        rs2.next();
        int roomID=rs2.getInt(1);
        System.out.println("recordID gotten for schedule"+roomID);      
        newRoom=new Room(roomID,roomName,capacity);
        MainProgram.getRoomList().add(newRoom);
        return newRoom;
    }
    public static void deleteRoom(int roomID) throws SQLException{
        //delete meetings
        Vector<Meeting> m=MainProgram.getMeetingListByRoom(roomID);
        for(int i=0;i<m.size();i++){
            Employee.cancelMeeting(m.get(i).getMeetingID());
        }

        PreparedStatement delstmt = MainProgram.getConnection().prepareStatement("delete from room where roomID=?");        
        delstmt.setInt(1, roomID);
        delstmt.executeUpdate();    
        Vector<Room> roomList=MainProgram.getRoomList();
        for(int i=0;i<roomList.size();i++){
            if(roomList.get(i).getRoomID()==roomID) {
                roomList.remove(i);
                i--;
            }
        }
    }
    
    public static boolean resetPass(Employee em){
        try{
            Statement stmt = MainProgram.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate("update employee set password='"+PasswordHash.getMD5sum(em.getUserName())
                    +"' where employeeID="+em.getEmployeeID());
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
