package io.spring.garage.controllers.dtos;

import io.spring.garage.entities.vehicle.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MotorBikeDTO extends VehicleDTO {

	private String plate;

	public MotorBikeDTO() {
		super.setType(VehicleType.MOTORBIKE);
	}
	
}
