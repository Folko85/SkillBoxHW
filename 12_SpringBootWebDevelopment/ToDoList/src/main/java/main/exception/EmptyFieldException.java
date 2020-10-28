package main.exception;

public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException() {
    }

    public EmptyFieldException(String message) {
        super(message);
    }
}
