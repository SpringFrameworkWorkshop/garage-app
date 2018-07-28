package io.spring.garage.controllers;

import io.spring.garage.controllers.dtos.MotorBikeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/motor-bike")
public interface MotorBikeController {

    @GetMapping()
    ResponseEntity<List<MotorBikeDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<MotorBikeDTO> findById(final @PathVariable("id") Long id);

    @PostMapping()
    ResponseEntity<MotorBikeDTO> create(final @RequestBody MotorBikeDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<MotorBikeDTO> update(final @PathVariable("id") Long id, final @RequestBody MotorBikeDTO dto);

    @DeleteMapping("/{id}")
    void remove(final @PathVariable("id") Long id);

}
