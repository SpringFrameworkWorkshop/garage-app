package io.spring.garage.manager;

import io.spring.garage.entities.vehicle.Vehicle;
import io.spring.garage.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleManager extends AbstractManager<Vehicle> {

    private VehicleRepository repository;

    @Autowired
    public VehicleManager(final VehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public VehicleRepository getRepository() {
        return repository;
    }
}
