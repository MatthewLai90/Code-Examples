package entities;

public class InvalidInfoException extends Exception {
    private int errorCode=0;
    public static final int SSNEXISTS=0x1;
    public static final int EMAILEXISTS=0x2;  
    public static final int USERNAMEEXISTS=0x4;  
    public InvalidInfoException(){
        super();
    }
    
    public InvalidInfoException(int theErrorCode){
        super();
        errorCode=theErrorCode;
    }
    
    public int getErrorCode(){
        return errorCode;
    }
    
    public String getMessage(){
         if(errorCode!=0){
            String errmsg="";
            if((errorCode & USERNAMEEXISTS)==USERNAMEEXISTS){
                errmsg+="username already exist, ";
            }
            if((errorCode & SSNEXISTS)==SSNEXISTS){
                errmsg+="ssn already exist, ";
            }
            if((errorCode & EMAILEXISTS)==EMAILEXISTS){
                errmsg+="email already exist";
            }
            return errmsg;
        }else{
             return super.getMessage();
        }
    }
    
    public InvalidInfoException(String message){
        super(message);
    }
}
