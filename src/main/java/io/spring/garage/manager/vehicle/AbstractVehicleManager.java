package io.spring.garage.manager.vehicle;

import io.spring.garage.entities.vehicle.VehicleEntity;
import io.spring.garage.manager.AbstractManager;

import java.util.List;

public abstract class AbstractVehicleManager<T extends VehicleEntity> extends AbstractManager<T> {


    @Override
    public List<T> findAll() {
        return super.findAll();
    }

    @Override
    public void save(T element) {
        super.save(element);
    }

    @Override
    public void delete(T element) {
        super.delete(element);
    }

    @Override
    public T get(long id) {
        return super.get(id);
    }
}
