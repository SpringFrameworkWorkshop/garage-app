package io.spring.garage.entities.vehicle;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Car extends AbstractVehicle {

	private String plate;

	public Car() {
		super.setType(VehicleType.CAR);
		super.setNumWheels(4);
	}

	@Override
	public String toString() {
		return String.format("\t\n{id: %s, type: %s, color: %s, model: %s, numWheels: %s, plate: %s}", getId(), getType(), getColor(), getModel(), getNumWheels(), plate);
	}
	
}
