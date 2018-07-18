package io.spring.garage.entities.vehicle;

import io.spring.garage.entities.GarageEntity;

public interface VehicleEntity extends GarageEntity {
	
	String getColor();
	
	String getModel();
	
	Integer getNumWheels();
	
}
