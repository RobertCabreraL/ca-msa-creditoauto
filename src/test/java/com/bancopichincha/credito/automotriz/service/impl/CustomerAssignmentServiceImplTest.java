package com.bancopichincha.credito.automotriz.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bancopichincha.credito.automotriz.domain.CarYard;
import com.bancopichincha.credito.automotriz.domain.Customer;
import com.bancopichincha.credito.automotriz.domain.CustomerAssignment;
import com.bancopichincha.credito.automotriz.domain.enums.Status;
import com.bancopichincha.credito.automotriz.exception.CarYardNotFoundException;
import com.bancopichincha.credito.automotriz.exception.CustomerAssignmentDisabledException;
import com.bancopichincha.credito.automotriz.exception.CustomerAssignmentNotFoundException;
import com.bancopichincha.credito.automotriz.exception.CustomerNotFoundException;
import com.bancopichincha.credito.automotriz.helper.MessageService;
import com.bancopichincha.credito.automotriz.repository.CustomerAssignmentRepository;
import com.bancopichincha.credito.automotriz.service.CarYardService;
import com.bancopichincha.credito.automotriz.service.CustomerService;
import com.bancopichincha.credito.automotriz.service.dto.CustomerAssignmentRequest;
import com.bancopichincha.credito.automotriz.service.dto.CustomerAssignmentResponse;
import com.bancopichincha.credito.automotriz.service.mapper.CustomerAssignmentMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerAssignmentServiceImplTest {
  @Mock private MessageService messageService;
  @Mock private CustomerService customerService;
  @Mock private CarYardService carYardService;
  @Mock private CustomerAssignmentMapper customerAssignmentMapper;
  @Mock private CustomerAssignmentRepository customerAssignmentRepository;
  @InjectMocks private CustomerAssignmentServiceImpl customerAssignmentService;

  @Test
  public void shouldThrowExceptionWhenCustomerNotFoundOnCreate() {
    CustomerAssignmentRequest request = createDummyRequest();

    when(customerService.getByIdentification(any())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(CustomerNotFoundException.class, () -> customerAssignmentService.create(request));
  }

  @Test
  public void shouldThrowExceptionWhenCarYardNotFoundOnCreate() {
    CustomerAssignmentRequest request = createDummyRequest();
    Customer customer = createDummyCustomer();

    when(customerService.getByIdentification(any())).thenReturn(Optional.of(customer));
    when(carYardService.getById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(CarYardNotFoundException.class, () -> customerAssignmentService.create(request));
  }

  @Test
  public void shouldSaveCustomerAssignmentAndReturnResponseOnCreate() {
    CustomerAssignmentRequest request = createDummyRequest();
    Customer customer = createDummyCustomer();
    CarYard carYard = createDummyCarYard();
    CustomerAssignment customerAssignment = createDummyCustomerAssignment();
    CustomerAssignmentResponse customerAssignmentResponse = createDummyResponse();

    when(customerService.getByIdentification(any())).thenReturn(Optional.of(customer));
    when(carYardService.getById(anyLong())).thenReturn(Optional.of(carYard));
    when(customerAssignmentMapper.mapToEntity(any(), any(), any())).thenReturn(customerAssignment);
    when(customerAssignmentMapper.mapToResponse(customerAssignment))
        .thenReturn(customerAssignmentResponse);

    CustomerAssignmentResponse actualResponse = customerAssignmentService.create(request);

    verify(customerAssignmentRepository).save(customerAssignment);
    assertInstanceOf(CustomerAssignmentResponse.class, actualResponse);
  }

  @Test
  public void shouldThrowExceptionWhenCustomerAssignmentNotFoundOnUpdate() {
    CustomerAssignmentRequest request = createDummyRequest();

    when(customerAssignmentRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        CustomerAssignmentNotFoundException.class,
        () -> customerAssignmentService.update(anyLong(), request));
  }

  @Test
  public void shouldThrowExceptionWhenCustomerNotFoundOnUpdate() {
    CustomerAssignmentRequest request = createDummyRequest();
    CustomerAssignment customerAssignment = createDummyCustomerAssignment();

    when(customerAssignmentRepository.findById(anyLong()))
        .thenReturn(Optional.of(customerAssignment));
    when(customerService.getByIdentification(any())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        CustomerNotFoundException.class,
        () -> customerAssignmentService.update(anyLong(), request));
  }

  @Test
  public void shouldThrowExceptionWhenCarYardNotFoundOnUpdate() {
    CustomerAssignmentRequest request = createDummyRequest();
    CustomerAssignment customerAssignment = createDummyCustomerAssignment();
    Customer customer = createDummyCustomer();

    when(customerAssignmentRepository.findById(anyLong()))
        .thenReturn(Optional.of(customerAssignment));
    when(customerService.getByIdentification(any())).thenReturn(Optional.of(customer));
    when(carYardService.getById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        CarYardNotFoundException.class, () -> customerAssignmentService.update(anyLong(), request));
  }

  @Test
  public void shouldUpdateCustomerAssignmentAndReturnResponseOnUpdate() {
    CustomerAssignmentRequest request = createDummyRequest();
    CustomerAssignment customerAssignment = createDummyCustomerAssignment();
    Customer customer = createDummyCustomer();
    CarYard carYard = createDummyCarYard();
    CustomerAssignmentResponse expectedResponse = createDummyResponse();

    when(customerAssignmentRepository.findById(anyLong()))
        .thenReturn(Optional.of(customerAssignment));
    when(customerService.getByIdentification(any())).thenReturn(Optional.of(customer));
    when(carYardService.getById(anyLong())).thenReturn(Optional.of(carYard));
    when(customerAssignmentMapper.mapToResponse(customerAssignment)).thenReturn(expectedResponse);

    CustomerAssignmentResponse actualResponse =
        customerAssignmentService.update(anyLong(), request);

    verify(customerAssignmentRepository).save(customerAssignment);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldThrowExceptionWhenCustomerAssignmentNotFoundOnDelete() {

    when(customerAssignmentRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        CustomerAssignmentNotFoundException.class,
        () -> customerAssignmentService.delete(anyLong()));
  }

  @Test
  public void shouldThrowExceptionWhenCustomerAssignmentAlreadyDisabledOnDelete() {
    CustomerAssignment customerAssignment = createDummyCustomerAssignment();
    customerAssignment.setStatus(Status.DISABLED);

    when(customerAssignmentRepository.findById(anyLong()))
        .thenReturn(Optional.of(customerAssignment));
    when(messageService.getMessage(any())).thenReturn("");

    assertThrows(
        CustomerAssignmentDisabledException.class,
        () -> customerAssignmentService.delete(anyLong()));
  }

  @Test
  public void shouldSetCustomerAssignmentStatusToDisabled() {
    CustomerAssignment customerAssignment = createDummyCustomerAssignment();

    when(customerAssignmentRepository.findById(anyLong()))
        .thenReturn(Optional.of(customerAssignment));

    customerAssignmentService.delete(anyLong());

    assertEquals(Status.DISABLED, customerAssignment.getStatus());
    verify(customerAssignmentRepository).save(customerAssignment);
  }

  @Test
  public void shouldReturnListOfCustomerAssignmentResponses() {
    List<CustomerAssignment> customerAssignments =
        Collections.singletonList(createDummyCustomerAssignment());
    List<CustomerAssignmentResponse> expectedResponses =
        Collections.singletonList(createDummyResponse());

    when(customerAssignmentRepository.findByCustomerIdAndCarYardId(anyLong(), anyLong()))
        .thenReturn(customerAssignments);

    when(customerAssignmentMapper.mapToResponse(anyList())).thenReturn(expectedResponses);

    List<CustomerAssignmentResponse> actualResponses =
        customerAssignmentService.findBy(anyLong(), anyLong());

    assertEquals(expectedResponses, actualResponses);
  }

  private CustomerAssignmentRequest createDummyRequest() {
    return CustomerAssignmentRequest.builder()
        .customerIdentification("123456")
        .carYardId(1L)
        .build();
  }

  private Customer createDummyCustomer() {
    return Customer.builder().build();
  }

  private CustomerAssignmentResponse createDummyResponse() {
    return CustomerAssignmentResponse.builder().build();
  }

  private CustomerAssignment createDummyCustomerAssignment() {
    return CustomerAssignment.builder().build();
  }

  private CarYard createDummyCarYard() {
    return CarYard.builder().id(1L).build();
  }
}
