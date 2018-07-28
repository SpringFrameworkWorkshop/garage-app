package io.spring.garage.repositories;

import io.spring.garage.entities.vehicle.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
}
