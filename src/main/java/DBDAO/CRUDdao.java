package DBDAO;
import Exceptions.EntityCrudException;

import java.util.List;

public interface CRUDdao<ID, Entity> {
    ID create(final Entity entity) throws EntityCrudException;
    Entity readById(final ID id) throws EntityCrudException;
    List<Entity> readAll() throws EntityCrudException;
    void update(final Entity entity) throws EntityCrudException;
    void delete(final ID id) throws EntityCrudException;
}
