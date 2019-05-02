package middleware.Exceptions;

public class DataAlreadyExistException extends Exception {
    public DataAlreadyExistException() {
    }

    public DataAlreadyExistException(String message) {
        super(message + " already exist in the database!");
    }
}