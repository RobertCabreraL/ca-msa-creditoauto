package com.bancopichincha.credito.automotriz.service.impl;

import com.bancopichincha.credito.automotriz.domain.Brand;
import com.bancopichincha.credito.automotriz.domain.Vehicle;
import com.bancopichincha.credito.automotriz.domain.enums.Status;
import com.bancopichincha.credito.automotriz.domain.enums.VehicleStatus;
import com.bancopichincha.credito.automotriz.exception.*;
import com.bancopichincha.credito.automotriz.helper.MessageService;
import com.bancopichincha.credito.automotriz.repository.CreditApplicationRepository;
import com.bancopichincha.credito.automotriz.repository.VehicleRepository;
import com.bancopichincha.credito.automotriz.service.BrandService;
import com.bancopichincha.credito.automotriz.service.VehicleService;
import com.bancopichincha.credito.automotriz.service.dto.VehicleRequest;
import com.bancopichincha.credito.automotriz.service.dto.VehicleResponse;
import com.bancopichincha.credito.automotriz.service.mapper.VehicleMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

  private final MessageService messageService;
  private final BrandService brandService;
  private final VehicleRepository vehicleRepository;
  private final CreditApplicationRepository creditApplicationRepository;
  private final VehicleMapper vehicleMapper;

  @Override
  public List<Vehicle> findVehicles(Long brandId, String model, Integer year) {
    return vehicleRepository.findVehiclesByBrandAndTypeAndYear(brandId, model, year);
  }

  @Override
  public Optional<Vehicle> getByPlate(String plate) {
    return vehicleRepository.getVehicleByPlate(plate);
  }

  @Override
  public VehicleResponse save(VehicleRequest vehicleRequest) {
    if (vehicleRepository.existsByPlate(vehicleRequest.getPlate())) {
      throw new VehicleDuplicatedPlateException(
          messageService.getMessage("vehicle.duplicated_plate"));
    }

    Brand brand =
        brandService
            .findById(vehicleRequest.getBrandId())
            .orElseThrow(
                () -> new BrandNotFoundException(messageService.getMessage("brand.not_found")));

    Vehicle vehicle = vehicleMapper.mapFromRequest(vehicleRequest, brand);
    vehicleRepository.save(vehicle);

    return vehicleMapper.mapFromEntity(vehicle);
  }

  @Override
  public VehicleResponse update(String plate, VehicleRequest vehicleRequest) {
    Vehicle vehicle =
        vehicleRepository
            .getVehicleByPlate(plate)
            .orElseThrow(
                () -> new VehicleNotFoundException(messageService.getMessage("vehicle.not_found")));

    vehicleMapper.mapFromRequest(vehicleRequest, vehicle);
    vehicleRepository.save(vehicle);

    return vehicleMapper.mapFromEntity(vehicle);
  }

  @Override
  public Vehicle reserve(String plate) {
    return updateAvailabilityStatus(plate, VehicleStatus.RESERVED);
  }

  @Override
  public VehicleResponse sold(String plate) {
    Vehicle vehicle = updateAvailabilityStatus(plate, VehicleStatus.SOLD);
    return vehicleMapper.mapFromEntity(vehicle);
  }

  public Vehicle updateAvailabilityStatus(String plate, VehicleStatus availabilityStatus) {
    Vehicle vehicle =
        vehicleRepository
            .getVehicleByPlate(plate)
            .orElseThrow(
                () -> new VehicleNotFoundException(messageService.getMessage("vehicle.not_found")));

    if (vehicle.getStatus().isDisabled()) {
      throw new OperationNotAllowedException(
          messageService.getMessage("global.operation_not_allowed"));
    }

    vehicle.setAvailabilityStatus(availabilityStatus);
    vehicleRepository.save(vehicle);

    return vehicle;
  }

  @Override
  public void delete(String plate) {
    Vehicle vehicle =
        vehicleRepository
            .getVehicleByPlate(plate)
            .orElseThrow(
                () -> new VehicleNotFoundException(messageService.getMessage("vehicle.not_found")));

    if (creditApplicationRepository.existsVehicleDataByPlate(plate)) {
      throw new VehicleCreditApplicationRegisteredException(
          messageService.getMessage("vehicle.with_operations"));
    }

    vehicle.setStatus(Status.DISABLED);
    vehicle.setAvailabilityStatus(VehicleStatus.UNAVAILABLE);

    vehicleRepository.save(vehicle);
  }
}
