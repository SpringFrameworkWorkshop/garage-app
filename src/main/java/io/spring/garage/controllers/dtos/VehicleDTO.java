package io.spring.garage.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import io.spring.garage.controllers.dtos.resolver.VehicleTypeResolver;
import io.spring.garage.entities.vehicle.VehicleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeIdResolver(VehicleTypeResolver.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, visible = true, property = "type", defaultImpl = VehicleDTO.class)
public class VehicleDTO implements GarageDTO {
	private Long id;
	private VehicleType type;
	private String color;
	private String model;
	private Integer numWheels;
}
