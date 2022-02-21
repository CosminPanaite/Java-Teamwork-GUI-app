package Repository;

import Domain.Identifiable;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRepository<T extends Identifiable<ID>, ID> implements Repository<T, ID> {
    protected Map<ID, T> elem;

    public AbstractRepository() {
        elem = new HashMap<>();
    }

    public T add(T el) {
        if (elem.containsKey(el.getId()))
            throw new RuntimeException("Element already exists!!!");
        else
            elem.put(el.getId(), el);
        return el;
    }

    public void delete(T el) {
        if (elem.containsKey(el.getId()))
            elem.remove(el.getId());
    }

    /*
     * Precondition: id and el.getID() are equals
     */
    public void update( ID id,T el) {
        if (elem.containsKey(id))
            elem.put(el.getId(), el);
        else
            throw new RuntimeException("Element doesn’t exist");
    }

    public T findById(ID id) {
        if (elem.containsKey(id))
            return elem.get(id);
        else
            throw new RuntimeException("Element doesn't exist");
    }

    public Iterable<T> findAll() {
        return elem.values();
    }

    public Collection<T> getAll() {
        return elem.values();
    }
}

