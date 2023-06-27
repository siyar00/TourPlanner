package at.technikum.planner.dal;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Dao interface defines an abstract API that performs CRUD operations on objects of type T
 * see: https://www.baeldung.com/java-dao-pattern
 *
 * @param <T> the type of the model
 */
public interface Dao<T> {

    Optional<T> get(int id);

    List<T> getAll();

    T create();

    void update(T t, List<?> params);

    void update(T t) throws Exception, IOException;

    void delete(T t);

    void add(T t);
}