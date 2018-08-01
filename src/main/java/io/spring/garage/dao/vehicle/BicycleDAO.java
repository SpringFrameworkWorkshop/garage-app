package io.spring.garage.dao.vehicle;

import io.spring.garage.dao.AbstractDAO;
import io.spring.garage.entities.vehicle.Bicycle;

public class BicycleDAO extends AbstractDAO<Bicycle> {
	
	private static BicycleDAO instance;

	private BicycleDAO() {
		super(Bicycle.class);
	}

	public static BicycleDAO getInstance() {
		if (instance == null) {
			instance = new BicycleDAO();
		}
		return instance;
	}
}
