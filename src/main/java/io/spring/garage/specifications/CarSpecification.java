package io.spring.garage.specifications;

import io.spring.garage.entities.vehicle.Car;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecification {

    public static Specification<Car> hasColor(final String color) {
        return (root, query, builder) -> builder.equal(root.get("color"), color);
    }

    public static Specification<Car> hasModel(final String model) {
        return (root, query, builder) -> builder.equal(root.get("model"), model);
    }

}
