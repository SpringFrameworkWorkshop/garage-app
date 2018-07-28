package io.spring.garage.controllers;

import io.spring.garage.controllers.dtos.VehicleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/vehicle")
public interface VehicleController {

    @GetMapping()
    ResponseEntity<List<VehicleDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<VehicleDTO> findById(final @PathVariable("id") Long id);

    @PostMapping()
    ResponseEntity<VehicleDTO> create(final @RequestBody VehicleDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<VehicleDTO> update(final @PathVariable("id") Long id, final @RequestBody VehicleDTO dto);

    @DeleteMapping("/{id}")
    void remove(final @PathVariable("id") Long id);

}
