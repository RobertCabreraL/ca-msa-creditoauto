package com.bancopichincha.credito.automotriz.controller;

import com.bancopichincha.credito.automotriz.domain.Vehicle;
import com.bancopichincha.credito.automotriz.service.VehicleService;
import com.bancopichincha.credito.automotriz.service.dto.VehicleRequest;
import com.bancopichincha.credito.automotriz.service.dto.VehicleResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("vehicles")
public class VehicleController {

  private final VehicleService vehicleService;

  @PostMapping
  public ResponseEntity<VehicleResponse> createVehicle(@RequestBody VehicleRequest vehicle) {
    VehicleResponse vehicleResponse = vehicleService.save(vehicle);

    return ResponseEntity.status(HttpStatus.OK).body(vehicleResponse);
  }

  @PutMapping("/{plate}")
  public ResponseEntity<VehicleResponse> updateVehicle(
      @PathVariable String plate, @RequestBody VehicleRequest vehicleRequest) {

    log.info("deleteVehicle plate: " + plate + ", vehicle: " + vehicleRequest);

    VehicleResponse vehicleResponse = vehicleService.update(plate, vehicleRequest);

    return ResponseEntity.status(HttpStatus.OK).body(vehicleResponse);
  }

  @PatchMapping("/{plate}/sold")
  public ResponseEntity<VehicleResponse> soldVehicle(@PathVariable String plate) {
    log.info("reserveVehicle plate: " + plate);

    VehicleResponse vehicleResponse = vehicleService.sold(plate);

    return ResponseEntity.status(HttpStatus.OK).body(vehicleResponse);
  }

  @DeleteMapping("/{plate}")
  public ResponseEntity<String> deleteVehicle(@PathVariable String plate) {
    log.info("deleteVehicle plate: " + plate);
    vehicleService.delete(plate);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping
  public ResponseEntity<List<Vehicle>> findVehicles(
      @RequestParam(required = false) Long brandId,
      @RequestParam(required = false) String model,
      @RequestParam(required = false) Integer year) {

    List<Vehicle> vehicles = vehicleService.findVehicles(brandId, model, year);

    return ResponseEntity.ok(vehicles);
  }
}
