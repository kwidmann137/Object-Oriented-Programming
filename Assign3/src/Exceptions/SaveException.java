package Exceptions;

/**
 * Created by kyle on 11/10/17.
 */
public class SaveException extends Exception {

    public SaveException(String message, Throwable originalException){
        super(message, originalException);
    }

    public SaveException(String message){
        super(message);
    }

}
