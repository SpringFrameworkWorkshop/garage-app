package io.spring.garage.dao.vehicle;

import io.spring.garage.dao.AbstractDAO;
import io.spring.garage.entities.vehicle.VehicleEntity;

public abstract class AbstractVehicleDAO<V extends VehicleEntity> extends AbstractDAO<V> {

    public AbstractVehicleDAO(Class<V> clazz) {
        super(clazz);
    }
}
