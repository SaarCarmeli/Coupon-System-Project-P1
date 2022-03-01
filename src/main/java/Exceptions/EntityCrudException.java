package Exceptions;

public class EntityCrudException extends Exception {
    public EntityCrudException() {
    }

    public EntityCrudException(final EntityType entityType, final CrudOperation operation) {
        super("Failed to " + operation + " entity of type: " + entityType);
    }
}
