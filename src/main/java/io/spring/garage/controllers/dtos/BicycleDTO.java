package io.spring.garage.controllers.dtos;

import io.spring.garage.entities.vehicle.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BicycleDTO extends VehicleDTO {

	public BicycleDTO() {
		super.setType(VehicleType.BICYCLE);
	}
	
}
