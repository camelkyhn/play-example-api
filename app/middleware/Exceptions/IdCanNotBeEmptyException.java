package middleware.Exceptions;

public class IdCanNotBeEmptyException extends Exception {

    public IdCanNotBeEmptyException() {
    }

    public IdCanNotBeEmptyException(String message) {
        super(message);
    }
}
