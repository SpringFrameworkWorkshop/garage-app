package io.spring.garage.manager.vehicle;

import io.spring.garage.dao.vehicle.BicycleDAO;
import io.spring.garage.entities.vehicle.Bicycle;
import io.spring.garage.manager.AbstractManager;

public class BicycleManager extends AbstractManager<Bicycle> {
	
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
