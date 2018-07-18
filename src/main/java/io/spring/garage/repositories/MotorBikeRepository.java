package io.spring.garage.repositories;

import io.spring.garage.entities.vehicle.MotorBike;
import org.springframework.data.repository.CrudRepository;

public interface MotorBikeRepository extends CrudRepository<MotorBike, Long> {
}
