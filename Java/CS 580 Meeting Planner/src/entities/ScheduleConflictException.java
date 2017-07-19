package entities;

public class ScheduleConflictException extends Exception {
    public ScheduleConflictException(){
        
    }
    public ScheduleConflictException(String str){
        super(str);
    }    
}
