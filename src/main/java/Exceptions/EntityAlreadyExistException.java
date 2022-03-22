package Exceptions;

/**
 * Thrown when Entity already exist. Can not create the same entity twice.
 */
public class EntityAlreadyExistException extends Exception {

    /**
     * Thrown when Entity already exist. Can not create the same entity twice.
     */
    public EntityAlreadyExistException() {
    }

    /**
     * Thrown when Entity already exist. Can not create the same entity twice. Custom error message.
     *
     * @param entityType Entity that already exists
     */
    public EntityAlreadyExistException(EntityType entityType) {
        super("Failed to create " + entityType + " as " + entityType + " already exists!");
    }
}
