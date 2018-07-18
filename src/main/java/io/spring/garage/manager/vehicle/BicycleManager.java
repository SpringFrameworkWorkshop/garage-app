package io.spring.garage.manager.vehicle;

import io.spring.garage.dao.vehicle.BicycleDAO;
import io.spring.garage.entities.vehicle.Bicycle;

public class BicycleManager extends AbstractVehicleManager<Bicycle> {
	
	private static BicycleManager instance;

	private BicycleManager() {
	}

	public static BicycleManager getInstance() {
		if (instance == null) {
			instance = new BicycleManager();
		}
		return instance;
	}
	
	@Override
	public BicycleDAO getDao() {
		return BicycleDAO.getInstance();
	}
	
}
