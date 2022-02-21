package Repository;

import java.util.Collection;

public interface Repository<T, ID> {
    T add(T elem);

    void delete(T elem);

    void update(ID id, T elem);

    T findById(ID id);

    Iterable<T> findAll();

    Collection<T> getAll();
}
