package io.spring.garage.manager;

import io.spring.garage.entities.vehicle.MotorBike;
import io.spring.garage.repositories.MotorBikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotorBikeManager extends AbstractManager<MotorBike> {

    private MotorBikeRepository repository;

    @Autowired
    public MotorBikeManager(final MotorBikeRepository repository) {
        this.repository = repository;
    }

    @Override
    public MotorBikeRepository getRepository() {
        return repository;
    }
}
