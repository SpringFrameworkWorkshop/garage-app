package io.spring.garage.controllers.dtos;

import io.spring.garage.entities.vehicle.VehicleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleDTO implements GarageDTO {
	private Long id;
	private VehicleType type;
	private String color;
	private String model;
	private Integer numWheels;
}
