package io.spring.garage.controllers;

import io.spring.garage.controllers.dtos.BicycleDTO;
import io.spring.garage.entities.vehicle.Bicycle;
import io.spring.garage.manager.BicycleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BicycleControllerImpl extends AbstractController<Bicycle, BicycleDTO, Long> implements BicycleController {

    @Autowired
    private BicycleManager manager;

    public BicycleControllerImpl() {
        super(Bicycle.class, BicycleDTO.class);
    }

    @Override
    protected BicycleManager getManager() {
        return this.manager;
    }

    // @WTF
    public ResponseEntity<List<BicycleDTO>> findAll() {
        return new ResponseEntity<>(convertToDTOList(getManager().findAll()), HttpStatus.OK);
    }

}
