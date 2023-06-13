package com.bancopichincha.credito.automotriz.service;

import com.bancopichincha.credito.automotriz.domain.Vehicle;
import com.bancopichincha.credito.automotriz.service.dto.VehicleRequest;
import com.bancopichincha.credito.automotriz.service.dto.VehicleResponse;
import java.util.List;
import java.util.Optional;

public interface VehicleService {
  VehicleResponse save(VehicleRequest vehicle);

  VehicleResponse update(String plate, VehicleRequest vehicle);

  Vehicle reserve(String plate);

  VehicleResponse sold(String plate);

  void delete(String plate);

  List<Vehicle> findVehicles(Long brandId, String model, Integer year);

  Optional<Vehicle> getByPlate(String vehiclePlate);
}
