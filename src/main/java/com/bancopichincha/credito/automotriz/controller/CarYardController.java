package com.bancopichincha.credito.automotriz.controller;

import com.bancopichincha.credito.automotriz.service.CarYardService;
import com.bancopichincha.credito.automotriz.service.dto.CarYardRequest;
import com.bancopichincha.credito.automotriz.service.dto.CarYardResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("car_yards")
public class CarYardController {

  private final CarYardService carYardService;

  @PostMapping
  public ResponseEntity<CarYardResponse> create(@RequestBody CarYardRequest carYardRequest) {
    log.info("CarYardController - create : " + carYardRequest);
    CarYardResponse carYardResponse = carYardService.create(carYardRequest);

    return ResponseEntity.ok().body(carYardResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CarYardResponse> update(
      @PathVariable Long id, @RequestBody CarYardRequest carYardRequest) {
    log.info("CarYardController update - id:" + id + ", carYardRequest" + carYardRequest);

    CarYardResponse carYardResponse = carYardService.update(id, carYardRequest);

    return ResponseEntity.ok().body(carYardResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable Long id) {
    log.info("CarYardController delete - id:" + id);
    carYardService.delete(id);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarYardResponse> findById(@PathVariable Long id) {
    log.info("CarYardController findById - id:" + id);
    CarYardResponse carYardResponse = carYardService.findById(id);

    return ResponseEntity.ok().body(carYardResponse);
  }

  @GetMapping
  public ResponseEntity<List<CarYardResponse>> findAll() {
    log.info("CarYardController findAll");
    List<CarYardResponse> carYardResponse = carYardService.findAll();

    return ResponseEntity.ok().body(carYardResponse);
  }
}
