package io.spring.garage.dao.vehicle;

import io.spring.garage.entities.vehicle.MotorBike;

public class MotorBikeDAO extends AbstractVehicleDAO<MotorBike> {
	
	private static MotorBikeDAO instance;

	private MotorBikeDAO() {
		super(MotorBike.class);
	}

	public static MotorBikeDAO getInstance() {
		if (instance == null) {
			instance = new MotorBikeDAO();
		}
		return instance;
	}

}
