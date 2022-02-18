package DBDAO;

import Exceptions.EntityCrudException;

public abstract class UserDAO<ID, Entity> implements CRUDdao<ID, Entity> {
    public abstract Entity readByEmail(final String email) throws EntityCrudException;

    public boolean isExists(final String email) throws EntityCrudException {
        return readByEmail(email) != null;
    }
}
