package espace.exceptions;

public class ItemIsAssignedException extends Exception {

    public ItemIsAssignedException() {
        super("Item is assigned to an auction, cannot be deleted!");
    }

    public ItemIsAssignedException(String message) {
        super(message);
    }
}
