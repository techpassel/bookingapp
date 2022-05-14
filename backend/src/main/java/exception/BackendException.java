package exception;

public class BackendException extends RuntimeException{
    public BackendException(String message){
        super(message);
    }

    public BackendException(String message, Exception e) {
        super(message, e);
    }
}
