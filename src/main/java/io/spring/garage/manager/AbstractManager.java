package io.spring.garage.manager;

import io.spring.garage.dao.AbstractDAO;
import io.spring.garage.entities.GarageEntity;

import java.util.List;

public abstract class AbstractManager<T extends GarageEntity> {


    public abstract AbstractDAO<T> getDao();


    public List<T> findAll() {
        return getDao().findAll();
    }

    public void save(final T element) {
        getDao().save(element);
    }

    public void delete(final T element) {
        getDao().delete(element);
    }

    public T get(final long id) {
        return getDao().get(id);
    }
}
