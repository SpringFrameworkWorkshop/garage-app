package io.spring.garage.controllers;

import io.spring.garage.controllers.dtos.BicycleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bicycle")
public interface BicycleController {

    @GetMapping()
    ResponseEntity<List<BicycleDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<BicycleDTO> findById(final @PathVariable("id") Long id);

    @PostMapping()
    ResponseEntity<BicycleDTO> create(final @RequestBody BicycleDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<BicycleDTO> update(final @PathVariable("id") Long id, final @RequestBody BicycleDTO dto);

    @DeleteMapping("/{id}")
    void remove(final @PathVariable("id") Long id);

}
