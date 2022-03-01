package Exceptions;

public class EntityAlreadyExistException extends Exception {
    public EntityAlreadyExistException() {
    }

    public EntityAlreadyExistException(EntityType entityType) {
        super("Failed to create " + entityType + " as " + entityType + " already exists!");
    }
}
