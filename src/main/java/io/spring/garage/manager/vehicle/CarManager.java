package io.spring.garage.manager.vehicle;

import io.spring.garage.dao.vehicle.CarDAO;
import io.spring.garage.entities.vehicle.Car;

public class CarManager extends AbstractVehicleManager<Car> {

	private static CarManager instance;

	private CarManager() {
	}

	public static CarManager getInstance() {
		if (instance == null) {
			instance = new CarManager();
		}
		return instance;
	}

	@Override
	public CarDAO getDao() {
		return CarDAO.getInstance();
	}
}
