package Exceptions;

/**
 * Created by kyle on 11/10/17.
 */
public class SaveException extends Exception {

    /**
     * /**
     * An exception thrown when saving a file
     * @param message - the error message
     * @param originalException - the original exception thrown
     */
    public SaveException(String message, Throwable originalException){
        super(message, originalException);
    }

    /**
     * An exception thrown when saving a file
     * @param message - the error message
     */
    public SaveException(String message){
        super(message);
    }

}
