package Exceptions;

public class EntityCrudException extends Exception {
    public EntityCrudException() {
    }

    public EntityCrudException(String message) {
        super(message);
    }

    public EntityCrudException(EntityType entityType, CrudOperation operation) {
        super("Failed to " + operation + " entity of type: " + entityType);
    }
}
