package entities;
import java.util.Vector;
import functions.MainProgram;

public class Room {
    private int roomID;
    private String roomName;
    private int roomCapacity;

    public Room(int roomID, String roomName) {
        this.roomID = roomID;
        this.roomName = roomName;
    }
    
    public Room(int roomID, String roomName, int roomCapacity) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    
    public int getRoomCapacity() {
        return roomCapacity;
    }
    
    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
       
    public boolean stillHasMeeting(){
        Vector<Meeting> meetingList=MainProgram.getMeetingList();
        for(int i=0;i<meetingList.size();i++){
            if(meetingList.get(i).getMeetingRoom().getRoomID()==roomID){
                return true;
            }
        }        
        return false;
    }
    
    public boolean equals(Object o){
        if(!(o instanceof Room)) return false;
        Room r=(Room)o;
        System.out.println("equals?"+(roomID==r.roomID && roomName.equals(r.roomName) && roomCapacity==r.roomCapacity));
        return roomID==r.roomID && roomName.equals(r.roomName) && roomCapacity==r.roomCapacity;
    }
     
}
