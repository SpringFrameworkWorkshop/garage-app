package io.spring.garage.controllers;

import io.spring.garage.controllers.dtos.MotorBikeDTO;
import io.spring.garage.entities.vehicle.MotorBike;
import io.spring.garage.manager.MotorBikeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MotorBikeControllerImpl extends AbstractController<MotorBike, MotorBikeDTO, Long> implements MotorBikeController {

    @Autowired
    private MotorBikeManager manager;

    public MotorBikeControllerImpl() {
        super(MotorBike.class, MotorBikeDTO.class);
    }

    @Override
    protected MotorBikeManager getManager() {
        return this.manager;
    }

    // @WTF
    public ResponseEntity<List<MotorBikeDTO>> findAll() {
        return new ResponseEntity<>(convertToDTOList(getManager().findAll()), HttpStatus.OK);
    }

}
