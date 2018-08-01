package io.spring.garage.manager.vehicle;

import io.spring.garage.dao.vehicle.CarDAO;
import io.spring.garage.entities.vehicle.Car;
import io.spring.garage.manager.AbstractManager;

import java.util.List;

public class CarManager extends AbstractManager<Car> {

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

	public List<Car> findAllByColor(String color) {
		return getDao().findAllByColor(color);
	}
}
