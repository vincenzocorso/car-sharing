package it.vincenzocorso.carsharing.common.exceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException() {
        super();
    }

    public InternalServerException(String message) {
        super(message);
    }
}
