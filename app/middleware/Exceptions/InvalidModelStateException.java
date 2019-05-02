package middleware.Exceptions;

public class InvalidModelStateException extends Exception {
    public InvalidModelStateException() {
    }

    public InvalidModelStateException(String message) {
        super(message);
    }
}
