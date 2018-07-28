package io.spring.garage.controllers;

import io.spring.garage.common.CarFilter;
import io.spring.garage.controllers.dtos.CarDTO;
import io.spring.garage.entities.vehicle.Car;
import io.spring.garage.manager.CarManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarControllerImpl extends AbstractController<Car, CarDTO, Long> implements CarController {

    @Autowired
    private CarManager manager;

    public CarControllerImpl() {
        super(Car.class, CarDTO.class);
    }

    @Override
    protected CarManager getManager() {
        return this.manager;
    }

    public ResponseEntity<List<CarDTO>> findAll(@RequestParam(value = "color", required = false) final String color,
                                                @RequestParam(value = "model", required = false) final String model) {
        final CarFilter carFilter = new CarFilter(color, model);
        return new ResponseEntity<>(convertToDTOList(getManager().findAll(carFilter)), HttpStatus.OK);
    }

}
