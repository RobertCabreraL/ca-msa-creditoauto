package com.bancopichincha.credito.automotriz.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bancopichincha.credito.automotriz.domain.Brand;
import com.bancopichincha.credito.automotriz.domain.Vehicle;
import com.bancopichincha.credito.automotriz.domain.enums.Status;
import com.bancopichincha.credito.automotriz.domain.enums.VehicleStatus;
import com.bancopichincha.credito.automotriz.exception.BrandNotFoundException;
import com.bancopichincha.credito.automotriz.exception.OperationNotAllowedException;
import com.bancopichincha.credito.automotriz.exception.VehicleCreditApplicationRegisteredException;
import com.bancopichincha.credito.automotriz.exception.VehicleDuplicatedPlateException;
import com.bancopichincha.credito.automotriz.exception.VehicleNotFoundException;
import com.bancopichincha.credito.automotriz.helper.MessageService;
import com.bancopichincha.credito.automotriz.repository.CreditApplicationRepository;
import com.bancopichincha.credito.automotriz.repository.VehicleRepository;
import com.bancopichincha.credito.automotriz.service.BrandService;
import com.bancopichincha.credito.automotriz.service.dto.VehicleRequest;
import com.bancopichincha.credito.automotriz.service.dto.VehicleResponse;
import com.bancopichincha.credito.automotriz.service.mapper.VehicleMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VehicleServiceImplTest {
  @Mock private MessageService messageService;
  @Mock private BrandService brandService;
  @Mock private VehicleRepository vehicleRepository;
  @Mock private CreditApplicationRepository creditApplicationRepository;
  @Mock private VehicleMapper vehicleMapper;
  @InjectMocks private VehicleServiceImpl vehicleService;

  @Test
  public void shouldReturnListOfVehiclesOnFindVehicles() {
    List<Vehicle> expectedVehicles = Collections.singletonList(createDummyVehicle());
    when(vehicleRepository.findVehiclesByBrandAndTypeAndYear(anyLong(), any(), anyInt()))
        .thenReturn(expectedVehicles);

    List<Vehicle> actualVehicles = vehicleService.findVehicles(anyLong(), any(), anyInt());

    assertEquals(expectedVehicles, actualVehicles);
  }

  @Test
  public void shouldReturnVehicleWhenFoundOnGetByPlate() {
    Vehicle vehicle = createDummyVehicle();

    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.of(vehicle));

    Optional<Vehicle> result = vehicleService.getByPlate(anyString());

    assertTrue(result.isPresent());
    assertEquals(vehicle, result.get());
  }

  @Test
  public void shouldReturnEmptyOptionalWhenNotFoundOnGetByPlate() {
    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.empty());

    Optional<Vehicle> result = vehicleService.getByPlate(anyString());

    assertTrue(result.isEmpty());
  }

  @Test
  public void shouldSaveVehicleWhenPlateIsNotDuplicatedOnSave() {
    VehicleRequest vehicleRequest = createDummyVehicleRequest();
    Vehicle vehicle = createDummyVehicle();
    Brand brand = createDummyBrand();
    VehicleResponse vehicleResponse = createDummyVehicleResponse();

    when(vehicleRepository.existsByPlate(anyString())).thenReturn(false);
    when(brandService.findById(anyLong())).thenReturn(Optional.of(brand));
    when(vehicleMapper.mapFromRequest(any(), any(Brand.class))).thenReturn(vehicle);
    when(vehicleRepository.save(any())).thenReturn(vehicle);
    when(vehicleMapper.mapFromEntity(any())).thenReturn(vehicleResponse);

    VehicleResponse result = vehicleService.save(vehicleRequest);

    assertNotNull(result);
    verify(vehicleRepository).save(vehicle);
  }

  @Test
  public void shouldThrowExceptionWhenPlateIsDuplicatedOnSave() {
    VehicleRequest vehicleRequest = createDummyVehicleRequest();

    when(vehicleRepository.existsByPlate(any())).thenReturn(true);
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(VehicleDuplicatedPlateException.class, () -> vehicleService.save(vehicleRequest));
  }

  @Test
  public void shouldThrowExceptionWhenBrandNotFoundOnSave() {
    VehicleRequest vehicleRequest = createDummyVehicleRequest();

    when(vehicleRepository.existsByPlate(any())).thenReturn(false);
    when(brandService.findById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(BrandNotFoundException.class, () -> vehicleService.save(vehicleRequest));
  }

  @Test
  public void shouldUpdateVehicleWhenPlateIsFoundOnUpdate() {
    VehicleRequest vehicleRequest = createDummyVehicleRequest();
    Vehicle vehicle = createDummyVehicle();
    VehicleResponse vehicleResponse = createDummyVehicleResponse();

    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.of(vehicle));
    when(vehicleMapper.mapFromRequest(any(), any(Brand.class))).thenReturn(vehicle);
    when(vehicleRepository.save(any())).thenReturn(vehicle);
    when(vehicleMapper.mapFromEntity(any())).thenReturn(vehicleResponse);

    VehicleResponse result = vehicleService.update(anyString(), vehicleRequest);

    assertNotNull(result);
    verify(vehicleRepository).save(vehicle);
  }

  @Test
  public void shouldThrowExceptionWhenPlateIsNotFoundOnUpdate() {
    VehicleRequest vehicleRequest = createDummyVehicleRequest();

    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.empty());
    when(messageService.getMessage(anyString())).thenReturn("");

    assertThrows(
        VehicleNotFoundException.class, () -> vehicleService.update(anyString(), vehicleRequest));
  }

  @Test
  public void shouldUpdateAvailabilityStatusToReservedWhenPlateIsFoundOnReserve() {
    Vehicle vehicle = createDummyVehicle();

    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.of(vehicle));
    when(vehicleRepository.save(any())).thenReturn(vehicle);

    Vehicle result = vehicleService.reserve(anyString());

    assertNotNull(result);
    assertEquals(VehicleStatus.RESERVED, result.getAvailabilityStatus());
    verify(vehicleRepository).save(vehicle);
  }

  @Test
  public void shouldThrowExceptionWhenPlateIsNotFoundOnReserve() {
    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.empty());
    when(messageService.getMessage(anyString())).thenReturn("");

    assertThrows(VehicleNotFoundException.class, () -> vehicleService.reserve(anyString()));
  }

  @Test
  public void shouldUpdateAvailabilityStatusToSoldWhenPlateIsFoundOnSold() {
    Vehicle vehicle = createDummyVehicle();
    VehicleResponse vehicleResponse = createDummyVehicleResponse();

    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.of(vehicle));
    when(vehicleRepository.save(any())).thenReturn(vehicle);
    when(vehicleMapper.mapFromEntity(any())).thenReturn(vehicleResponse);

    VehicleResponse result = vehicleService.sold(anyString());

    assertNotNull(result);
    assertEquals(VehicleStatus.SOLD, vehicle.getAvailabilityStatus());
    verify(vehicleRepository).save(vehicle);
  }

  @Test
  public void shouldThrowExceptionWhenPlateIsNotFoundOnSold() {
    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.empty());
    when(messageService.getMessage(anyString())).thenReturn("");

    assertThrows(VehicleNotFoundException.class, () -> vehicleService.sold(anyString()));
  }

  @Test
  public void shouldUpdateStatusAndSaveVehicleOnUpdateAvailabilityStatus() {
    VehicleStatus availabilityStatus = VehicleStatus.RESERVED;
    Vehicle vehicle = createDummyVehicle();

    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.of(vehicle));
    when(vehicleRepository.save(any())).thenReturn(vehicle);

    Vehicle result = vehicleService.updateAvailabilityStatus(anyString(), availabilityStatus);

    assertNotNull(result);
    assertEquals(availabilityStatus, result.getAvailabilityStatus());
    verify(vehicleRepository).save(vehicle);
  }

  @Test
  public void shouldThrowExceptionWhenPlateIsNotFoundOnUpdateAvailabilityStatus() {
    VehicleStatus availabilityStatus = VehicleStatus.RESERVED;

    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.empty());
    when(messageService.getMessage(anyString())).thenReturn("");

    assertThrows(
        VehicleNotFoundException.class,
        () -> vehicleService.updateAvailabilityStatus(anyString(), availabilityStatus));
  }

  @Test
  public void shouldThrowExceptionWhenVehicleIsAlreadyDisabledOnUpdateAvailabilityStatus() {
    VehicleStatus availabilityStatus = VehicleStatus.RESERVED;
    Vehicle vehicle = createDummyVehicle();
    vehicle.setStatus(Status.DISABLED);

    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.of(vehicle));
    when(messageService.getMessage(anyString())).thenReturn("");

    assertThrows(
        OperationNotAllowedException.class,
        () -> vehicleService.updateAvailabilityStatus(anyString(), availabilityStatus));
  }

  @Test
  public void shouldUpdateStatusAndSaveVehicleWhenPlateIsFoundOnDelete() {
    Vehicle vehicle = createDummyVehicle();

    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.of(vehicle));
    when(creditApplicationRepository.existsVehicleDataByPlate(anyString())).thenReturn(false);
    when(vehicleRepository.save(any())).thenReturn(vehicle);

    vehicleService.delete(anyString());

    assertEquals(Status.DISABLED, vehicle.getStatus());
    assertEquals(VehicleStatus.UNAVAILABLE, vehicle.getAvailabilityStatus());
    verify(vehicleRepository).save(vehicle);
  }

  @Test
  public void shouldThrowExceptionWhenPlateIsNotFoundOnDelete() {
    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.empty());
    when(messageService.getMessage(anyString())).thenReturn("");

    assertThrows(VehicleNotFoundException.class, () -> vehicleService.delete(anyString()));
  }

  @Test
  public void shouldThrowExceptionWhenCreditApplicationsExistForVehicleOnDelete() {
    Vehicle vehicle = createDummyVehicle();

    when(vehicleRepository.getVehicleByPlate(anyString())).thenReturn(Optional.of(vehicle));
    when(creditApplicationRepository.existsVehicleDataByPlate(anyString())).thenReturn(true);
    when(messageService.getMessage(anyString())).thenReturn("");

    assertThrows(
        VehicleCreditApplicationRegisteredException.class,
        () -> vehicleService.delete(anyString()));
  }


  public Vehicle createDummyVehicle() {
    return Vehicle.builder().build();
  }

  public Brand createDummyBrand() {
    return Brand.builder().build();
  }

  private VehicleRequest createDummyVehicleRequest() {
    return VehicleRequest.builder().brandId(1L).build();
  }

  private VehicleResponse createDummyVehicleResponse() {
    return VehicleResponse.builder().build();
  }
}
