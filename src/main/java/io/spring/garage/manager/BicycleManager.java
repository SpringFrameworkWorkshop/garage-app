package io.spring.garage.manager;

import io.spring.garage.entities.vehicle.Bicycle;
import io.spring.garage.repositories.BicycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BicycleManager extends AbstractManager<Bicycle> {

    private BicycleRepository repository;

    @Autowired
    public BicycleManager(final BicycleRepository repository) {
        this.repository = repository;
    }

    @Override
    public BicycleRepository getRepository() {
        return repository;
    }
}
