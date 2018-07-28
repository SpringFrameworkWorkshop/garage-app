package io.spring.garage.controllers;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.spring.garage.controllers.dtos.BicycleDTO;
import io.spring.garage.controllers.dtos.CarDTO;
import io.spring.garage.controllers.dtos.MotorBikeDTO;
import io.spring.garage.controllers.dtos.VehicleDTO;
import io.spring.garage.entities.vehicle.Bicycle;
import io.spring.garage.entities.vehicle.Car;
import io.spring.garage.entities.vehicle.MotorBike;
import io.spring.garage.entities.vehicle.Vehicle;
import io.spring.garage.manager.VehicleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehicleControllerImpl extends AbstractController<Vehicle, VehicleDTO, Long> implements VehicleController {

    private static final BiMap<Class<? extends Vehicle>, Class<? extends VehicleDTO>> MAPPING = HashBiMap.create();

    static {
        MAPPING.put(Car.class, CarDTO.class);
        MAPPING.put(MotorBike.class, MotorBikeDTO.class);
        MAPPING.put(Bicycle.class, BicycleDTO.class);
        MAPPING.put(Vehicle.class, VehicleDTO.class);
    }

    @Autowired
    private VehicleManager manager;

    public VehicleControllerImpl() {
        super(Vehicle.class, VehicleDTO.class);
    }

    @Override
    protected VehicleManager getManager() {
        return this.manager;
    }

    @Override
    protected Class<? extends VehicleDTO> getDtoClass(Vehicle vehicle) {
        return MAPPING.get(vehicle.getClass());
    }

    @Override
    protected Class<? extends Vehicle> getEntityClass(VehicleDTO vehicleDTO) {
        return MAPPING.inverse().get(vehicleDTO.getClass());
    }

    // @WTF
    public ResponseEntity<List<VehicleDTO>> findAll() {
        return new ResponseEntity<>(convertToDTOList(getManager().findAll()), HttpStatus.OK);
    }


}
