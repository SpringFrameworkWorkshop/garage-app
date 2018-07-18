package io.spring.garage.repositories;

import io.spring.garage.entities.vehicle.Bicycle;
import org.springframework.data.repository.CrudRepository;

public interface BicycleRepository extends CrudRepository<Bicycle, Long> {
}
