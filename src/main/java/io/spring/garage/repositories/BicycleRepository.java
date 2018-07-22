package io.spring.garage.repositories;

import io.spring.garage.entities.vehicle.Bicycle;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BicycleRepository extends PagingAndSortingRepository<Bicycle, Long> {
}
