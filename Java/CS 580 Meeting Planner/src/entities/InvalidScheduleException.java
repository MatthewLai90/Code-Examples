package entities;

public class InvalidScheduleException extends Exception{
    public InvalidScheduleException(){
        super();
    }
    public InvalidScheduleException(String message){
        super(message);
    }
}
