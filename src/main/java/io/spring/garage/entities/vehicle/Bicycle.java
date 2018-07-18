package io.spring.garage.entities.vehicle;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Bicycle extends AbstractVehicle {

	public Bicycle() {
		super.setType(VehicleType.BICYCLE);
		super.setNumWheels(2);
	}
	
	@Override
	public String toString() {
		return String.format("\t\n{id: %s, type: %s, color: %s, model: %s, numWheels: %s}", getId(), getType(), getColor(), getModel(), getNumWheels());
	}
	
}
