package io.spring.garage.manager.vehicle;

import io.spring.garage.dao.vehicle.MotorBikeDAO;
import io.spring.garage.entities.vehicle.MotorBike;

public class MotorBikeManager extends AbstractVehicleManager<MotorBike> {
	
	private static MotorBikeManager instance;

	private MotorBikeManager() {
	}

	public static MotorBikeManager getInstance() {
		if (instance == null) {
			instance = new MotorBikeManager();
		}
		return instance;
	}

	@Override
	public MotorBikeDAO getDao() {
		return MotorBikeDAO.getInstance();
	}
}
