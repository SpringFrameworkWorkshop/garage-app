package io.spring.garage.dao.vehicle;

import io.spring.garage.entities.vehicle.Car;

public class CarDAO extends AbstractVehicleDAO<Car> {

    private static CarDAO instance;

    private CarDAO() {
        super(Car.class);
    }

    public static CarDAO getInstance() {
        if (instance == null) {
            instance = new CarDAO();
        }
        return instance;
    }

}
