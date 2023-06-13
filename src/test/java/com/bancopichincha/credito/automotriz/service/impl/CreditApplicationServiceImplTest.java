package com.bancopichincha.credito.automotriz.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bancopichincha.credito.automotriz.domain.Brand;
import com.bancopichincha.credito.automotriz.domain.CarYard;
import com.bancopichincha.credito.automotriz.domain.CreditApplication;
import com.bancopichincha.credito.automotriz.domain.Customer;
import com.bancopichincha.credito.automotriz.domain.SaleExecutive;
import com.bancopichincha.credito.automotriz.domain.Vehicle;
import com.bancopichincha.credito.automotriz.domain.enums.CreditApplicationStatus;
import com.bancopichincha.credito.automotriz.domain.enums.Status;
import com.bancopichincha.credito.automotriz.domain.enums.VehicleStatus;
import com.bancopichincha.credito.automotriz.exception.CarYardNotFoundException;
import com.bancopichincha.credito.automotriz.exception.CreditApplicationNotFoundException;
import com.bancopichincha.credito.automotriz.exception.CreditApplicationRegisteredException;
import com.bancopichincha.credito.automotriz.exception.CustomerNotFoundException;
import com.bancopichincha.credito.automotriz.exception.SaleExecutiveNotFoundException;
import com.bancopichincha.credito.automotriz.exception.VehicleNotAvailableException;
import com.bancopichincha.credito.automotriz.exception.VehicleNotFoundException;
import com.bancopichincha.credito.automotriz.helper.MessageService;
import com.bancopichincha.credito.automotriz.repository.CreditApplicationRepository;
import com.bancopichincha.credito.automotriz.service.CarYardService;
import com.bancopichincha.credito.automotriz.service.CustomerService;
import com.bancopichincha.credito.automotriz.service.SaleExecutiveService;
import com.bancopichincha.credito.automotriz.service.VehicleService;
import com.bancopichincha.credito.automotriz.service.dto.CreditApplicationRequest;
import com.bancopichincha.credito.automotriz.service.dto.CreditApplicationResponse;
import com.bancopichincha.credito.automotriz.service.mapper.CreditApplicationMapper;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreditApplicationServiceImplTest {

  @Mock private MessageService messageService;
  @Mock private CustomerService customerService;
  @Mock private CarYardService carYardService;
  @Mock private VehicleService vehicleService;
  @Mock private SaleExecutiveService saleExecutiveService;
  @Mock private CreditApplicationMapper creditApplicationMapper;
  @Mock private CreditApplicationRepository creditApplicationRepository;
  @InjectMocks private CreditApplicationServiceImpl creditApplicationService;

  @Test
  public void shouldThrowExceptionWhenCreditApplicationAlreadyExistsOnCreateAction() {
    CreditApplicationRequest createDummyRequest = createDummyRequest();

    when(creditApplicationRepository.existsBy(any(), any(), any())).thenReturn(true);
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        CreditApplicationRegisteredException.class,
        () -> creditApplicationService.create(createDummyRequest));
  }

  @Test
  public void shouldThrowExceptionWhenVehicleNotFoundOnCreateAction() {
    CreditApplicationRequest createDummyRequest = createDummyRequest();

    when(creditApplicationRepository.existsBy(any(), any(), any())).thenReturn(false);
    when(vehicleService.getByPlate(any())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        VehicleNotFoundException.class, () -> creditApplicationService.create(createDummyRequest));
  }

  @Test
  public void shouldThrowExceptionWhenVehicleNotAvailableOnCreateAction() {
    CreditApplicationRequest createDummyRequest = createDummyRequest();
    Vehicle vehicle = createDummyVehicle(VehicleStatus.UNAVAILABLE);

    when(creditApplicationRepository.existsBy(any(), any(), any())).thenReturn(false);
    when(vehicleService.getByPlate(any())).thenReturn(Optional.of(vehicle));
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        VehicleNotAvailableException.class,
        () -> creditApplicationService.create(createDummyRequest));
  }

  @Test
  public void shouldThrowExceptionWhenCustomerNotFoundOnCreateAction() {
    CreditApplicationRequest request = createDummyRequest();
    Vehicle vehicle = createDummyVehicle(VehicleStatus.AVAILABLE);

    when(creditApplicationRepository.existsBy(any(), any(), any())).thenReturn(false);
    when(vehicleService.getByPlate(any())).thenReturn(Optional.of(vehicle));
    when(customerService.getByIdentification(any())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(CustomerNotFoundException.class, () -> creditApplicationService.create(request));
  }

  @Test
  public void shouldThrowExceptionWhenCarYardNotFoundOnCreateAction() {
    CreditApplicationRequest request = createDummyRequest();
    Vehicle vehicle = createDummyVehicle(VehicleStatus.AVAILABLE);
    Customer customer = createDummyCustomer();

    when(creditApplicationRepository.existsBy(any(), any(), any())).thenReturn(false);
    when(vehicleService.getByPlate(any())).thenReturn(Optional.of(vehicle));
    when(customerService.getByIdentification(any())).thenReturn(Optional.of(customer));
    when(carYardService.getById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(CarYardNotFoundException.class, () -> creditApplicationService.create(request));
  }

  @Test
  public void shouldThrowExceptionWhenSaleExecutiveNotFoundOnCreateAction() {
    CreditApplicationRequest request = createDummyRequest();
    Vehicle vehicle = createDummyVehicle(VehicleStatus.AVAILABLE);
    Customer customer = createDummyCustomer();
    CarYard carYard = createDummyCarYard();

    when(creditApplicationRepository.existsBy(any(), any(), any())).thenReturn(false);
    when(vehicleService.getByPlate(any())).thenReturn(Optional.of(vehicle));

    when(customerService.getByIdentification(any())).thenReturn(Optional.of(customer));
    when(carYardService.getById(anyLong())).thenReturn(Optional.of(carYard));
    when(saleExecutiveService.getById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        SaleExecutiveNotFoundException.class, () -> creditApplicationService.create(request));
  }

  @Test
  public void shouldSaveCreditApplicationAndReturnResponseOnCreateAction() {
    CreditApplicationRequest request = createDummyRequest();
    Vehicle vehicle = createDummyVehicle(VehicleStatus.AVAILABLE);
    Customer customer = createDummyCustomer();
    CarYard carYard = createDummyCarYard();
    SaleExecutive saleExecutive = createDummySaleExecutive();
    CreditApplication creditApplication = createDummyCreditApplication();
    CreditApplicationResponse expectedResponse = createDummyCreditApplicationResponse();

    when(creditApplicationRepository.existsBy(any(), any(), any())).thenReturn(false);
    when(vehicleService.getByPlate(any())).thenReturn(Optional.of(vehicle));
    when(customerService.getByIdentification(any())).thenReturn(Optional.of(customer));
    when(carYardService.getById(anyLong())).thenReturn(Optional.of(carYard));
    when(saleExecutiveService.getById(anyLong())).thenReturn(Optional.of(saleExecutive));
    when(creditApplicationMapper.mapToEntity(any(), any(), any(), any(), any()))
        .thenReturn(creditApplication);
    when(creditApplicationMapper.mapToResponse(creditApplication)).thenReturn(expectedResponse);

    CreditApplicationResponse actualResponse = creditApplicationService.create(request);

    verify(creditApplicationRepository).save(creditApplication);
    verify(vehicleService).reserve(any());
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldThrowExceptionWhenCreditApplicationNotFoundOnUpdate() {
    CreditApplicationRequest request = createDummyRequest();

    when(creditApplicationRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        CreditApplicationNotFoundException.class,
        () -> creditApplicationService.update(anyLong(), request));
  }

  @Test
  public void shouldThrowExceptionWhenCustomerNotFoundOnUpdate() {
    CreditApplicationRequest request = createDummyRequest();
    CreditApplication creditApplication = createDummyCreditApplication();

    when(creditApplicationRepository.findById(anyLong()))
        .thenReturn(Optional.of(creditApplication));
    when(customerService.getByIdentification(any())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        CustomerNotFoundException.class, () -> creditApplicationService.update(anyLong(), request));
  }

  @Test
  public void shouldThrowExceptionWhenCarYardNotFoundOnUpdate() {
    CreditApplicationRequest request = createDummyRequest();
    CreditApplication creditApplication = createDummyCreditApplication();
    Customer customer = createDummyCustomer();

    when(creditApplicationRepository.findById(anyLong()))
        .thenReturn(Optional.of(creditApplication));
    when(customerService.getByIdentification(any())).thenReturn(Optional.of(customer));
    when(carYardService.getById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        CarYardNotFoundException.class, () -> creditApplicationService.update(anyLong(), request));
  }

  @Test
  public void shouldThrowExceptionWhenVehicleNotFoundOnUpdate() {
    CreditApplicationRequest request = createDummyRequest();
    CreditApplication creditApplication = createDummyCreditApplication();
    Customer customer = createDummyCustomer();
    CarYard carYard = createDummyCarYard();

    when(creditApplicationRepository.findById(anyLong()))
        .thenReturn(Optional.of(creditApplication));
    when(customerService.getByIdentification(any())).thenReturn(Optional.of(customer));
    when(carYardService.getById(anyLong())).thenReturn(Optional.of(carYard));
    when(vehicleService.getByPlate(any())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        VehicleNotFoundException.class, () -> creditApplicationService.update(anyLong(), request));
  }

  @Test
  public void shouldThrowExceptionWhenSaleExecutiveNotFoundOnUpdate() {
    CreditApplicationRequest request = createDummyRequest();
    CreditApplication creditApplication = createDummyCreditApplication();
    Customer customer = createDummyCustomer();
    CarYard carYard = createDummyCarYard();
    Vehicle vehicle = createDummyVehicle(VehicleStatus.AVAILABLE);

    when(creditApplicationRepository.findById(anyLong()))
        .thenReturn(Optional.of(creditApplication));
    when(customerService.getByIdentification(any())).thenReturn(Optional.of(customer));
    when(carYardService.getById(anyLong())).thenReturn(Optional.of(carYard));
    when(vehicleService.getByPlate(any())).thenReturn(Optional.of(vehicle));
    when(saleExecutiveService.getById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        SaleExecutiveNotFoundException.class,
        () -> creditApplicationService.update(anyLong(), request));
  }

  @Test
  public void shouldUpdateCreditApplicationAndReturnResponseOnUpdate() {
    CreditApplicationRequest request = createDummyRequest();
    CreditApplication creditApplication = createDummyCreditApplication();
    Customer customer = createDummyCustomer();
    CarYard carYard = createDummyCarYard();
    Vehicle vehicle = createDummyVehicle(VehicleStatus.AVAILABLE);
    SaleExecutive saleExecutive = createDummySaleExecutive();
    CreditApplicationResponse expectedResponse = createDummyCreditApplicationResponse();

    when(creditApplicationRepository.findById(anyLong()))
        .thenReturn(Optional.of(creditApplication));
    when(customerService.getByIdentification(any())).thenReturn(Optional.of(customer));
    when(carYardService.getById(anyLong())).thenReturn(Optional.of(carYard));
    when(vehicleService.getByPlate(any())).thenReturn(Optional.of(vehicle));
    when(saleExecutiveService.getById(anyLong())).thenReturn(Optional.of(saleExecutive));

    when(creditApplicationMapper.mapToResponse(creditApplication)).thenReturn(expectedResponse);

    CreditApplicationResponse actualResponse = creditApplicationService.update(anyLong(), request);

    verify(creditApplicationRepository).save(creditApplication);
    assertEquals(expectedResponse, actualResponse, "Should return the expected response");
  }

  @Test
  public void shouldThrowExceptionWhenCreditApplicationNotFoundOnDelete() {
    when(creditApplicationRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        CreditApplicationNotFoundException.class, () -> creditApplicationService.delete(anyLong()));
  }

  @Test
  public void shouldSetCreditApplicationStatusToCancelledOnDelete() {
    CreditApplication creditApplication = createDummyCreditApplication();

    when(creditApplicationRepository.findById(anyLong()))
        .thenReturn(Optional.of(creditApplication));

    creditApplicationService.delete(anyLong());

    assertEquals(CreditApplicationStatus.CANCELLED, creditApplication.getStatus());
    verify(creditApplicationRepository).save(creditApplication);
  }

  @Test
  public void shouldReturnListOfCreditApplicationResponses() {
    List<CreditApplication> creditApplications =
        Collections.singletonList(createDummyCreditApplication());
    List<CreditApplicationResponse> expectedResponses =
        Collections.singletonList(createDummyCreditApplicationResponse());

    when(creditApplicationRepository.findAll()).thenReturn(creditApplications);
    when(creditApplicationMapper.mapToResponse(anyList())).thenReturn(expectedResponses);

    List<CreditApplicationResponse> actualResponses = creditApplicationService.findAll();

    assertEquals(expectedResponses, actualResponses);
  }

  private CreditApplicationRequest createDummyRequest() {
    return CreditApplicationRequest.builder()
        .customerIdentification("1234567890")
        .elaborationDate("2023-06-13")
        .vehiclePlate("ABC123")
        .carYardId(1L)
        .saleExecutiveId(1L)
        .build();
  }

  private Vehicle createDummyVehicle(VehicleStatus status) {
    return Vehicle.builder()
        .id(1L)
        .plate("ABC-0000")
        .model("SUV")
        .nroChassis("ABC-13a2")
        .brand(new Brand())
        .type("")
        .engineCapacity(1500)
        .appraisal(new BigDecimal(3000))
        .year(2024)
        .availabilityStatus(status)
        .status(Status.ENABLED)
        .build();
  }

  private Customer createDummyCustomer() {
    return Customer.builder().build();
  }

  private CarYard createDummyCarYard() {
    return CarYard.builder().build();
  }

  private SaleExecutive createDummySaleExecutive() {
    return SaleExecutive.builder().build();
  }

  private CreditApplication createDummyCreditApplication() {
    return CreditApplication.builder().id(1L).build();
  }

  private CreditApplicationResponse createDummyCreditApplicationResponse() {
    return CreditApplicationResponse.builder().build();
  }
}
