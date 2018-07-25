package io.spring.garage.entities.vehicle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Inheritance(strategy = InheritanceType.JOINED)
public class Vehicle implements VehicleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	private VehicleType type;

	private String color;
	private String model;
	private Integer numWheels;

}
