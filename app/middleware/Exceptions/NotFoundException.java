package middleware.Exceptions;

public class NotFoundException extends Exception {

    public NotFoundException() {
    }

    public NotFoundException(String name) {
        super(name + " is not found!");
    }
}
