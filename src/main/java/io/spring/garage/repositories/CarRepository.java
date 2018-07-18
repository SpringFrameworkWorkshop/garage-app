package io.spring.garage.repositories;

import io.spring.garage.entities.vehicle.Car;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long>, JpaSpecificationExecutor<Car> {
}
