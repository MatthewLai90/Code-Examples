package functions;

public class InvalidLoginException extends Exception{
        public InvalidLoginException(){
            super();
        }
        public InvalidLoginException(String message){
            super(message);
        }
}
