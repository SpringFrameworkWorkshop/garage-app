package io.spring.garage.controllers;

import io.spring.garage.controllers.dtos.CarDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/car")
public interface CarController {

    @GetMapping()
    ResponseEntity<List<CarDTO>> findAll(
            @RequestParam(value = "color", required = false) final String color,
            @RequestParam(value = "model", required = false) final String model
    );

    @GetMapping("/{id}")
    ResponseEntity<CarDTO> findById(final @PathVariable("id") Long id);

    @PostMapping()
    ResponseEntity<CarDTO> create(final @RequestBody CarDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<CarDTO> update(final @PathVariable("id") Long id, final @RequestBody CarDTO dto);

    @DeleteMapping("/{id}")
    void remove(final @PathVariable("id") Long id);

}
