package db;

import java.util.List;

public interface Controller<T, P> {

    List<T> selectAll();

    void insert(T obj);

    void update(T obj);

    void delete(P id);

    boolean isPresent(P id);

    T get(P id);

    int count();
}
