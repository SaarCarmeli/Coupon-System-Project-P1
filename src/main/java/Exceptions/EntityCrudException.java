package Exceptions;

/**
 * Thrown when failed to execute Create-Read-Update-Delete Methods.
 */
public class EntityCrudException extends Exception {
    /**
     * Thrown when failed to execute Create-Read-Update-Delete Methods.
     */
    public EntityCrudException() {
    }

    /**
     * Thrown when failed to execute Create-Read-Update-Delete Methods.
     *
     * @param message Error message
     */
    public EntityCrudException(String message) {
        super(message);
    }

    /**
     * Thrown when failed to execute Create-Read-Update-Delete (CRUD) Methods. Custom error message.
     *
     * @param entityType Entity failed CRUD on
     * @param operation  CRUD operation failed
     */
    public EntityCrudException(EntityType entityType, CrudOperation operation) {
        super("Failed to " + operation + " entity of type: " + entityType);
    }
}
