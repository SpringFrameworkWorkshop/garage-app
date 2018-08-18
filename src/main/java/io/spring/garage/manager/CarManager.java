package io.spring.garage.manager;

import io.spring.garage.common.CarFilter;
import io.spring.garage.entities.vehicle.Car;
import io.spring.garage.repositories.CarRepository;
import io.spring.garage.specifications.CarSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class CarManager extends AbstractManager<Car> {

    private CarRepository repository;

    @Autowired
    public CarManager(final CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarRepository getRepository() {
        return repository;
    }

    public List<Car> findAll(final CarFilter filter) {

        final Specification<Car> spec = where((Specification<Car>) null)
                // filter by color
                .and((filter.getColor() == null)? null : CarSpecification.hasColor(filter.getColor()))
                // filter by model
                .and((filter.getModel() == null)? null : CarSpecification.hasModel(filter.getModel()));

        return getRepository().findAll(spec);

    }
}
