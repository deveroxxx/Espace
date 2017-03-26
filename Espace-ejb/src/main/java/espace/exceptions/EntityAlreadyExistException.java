package espace.exceptions;

public class EntityAlreadyExistException extends Exception{
    public EntityAlreadyExistException() {
    }

    public EntityAlreadyExistException(String message) {
        super(message);
    }

    public EntityAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
