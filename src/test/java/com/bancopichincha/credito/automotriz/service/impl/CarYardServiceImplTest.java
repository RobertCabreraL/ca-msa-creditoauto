package com.bancopichincha.credito.automotriz.service.impl;

import static org.mockito.Mockito.*;

import com.bancopichincha.credito.automotriz.domain.CarYard;
import com.bancopichincha.credito.automotriz.domain.enums.Status;
import com.bancopichincha.credito.automotriz.exception.CarYardAlreadyDisabledException;
import com.bancopichincha.credito.automotriz.exception.CarYardNotFoundException;
import com.bancopichincha.credito.automotriz.exception.CarYardWithOperationsException;
import com.bancopichincha.credito.automotriz.helper.MessageService;
import com.bancopichincha.credito.automotriz.repository.CarYardRepository;
import com.bancopichincha.credito.automotriz.repository.CreditApplicationRepository;
import com.bancopichincha.credito.automotriz.repository.CustomerAssignmentRepository;
import com.bancopichincha.credito.automotriz.repository.SaleExecutiveRepository;
import com.bancopichincha.credito.automotriz.service.dto.CarYardRequest;
import com.bancopichincha.credito.automotriz.service.dto.CarYardResponse;
import com.bancopichincha.credito.automotriz.service.mapper.CarYardMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CarYardServiceImplTest {

  @Mock private MessageService messageService;
  @Mock private CarYardRepository carYardRepository;
  @Mock private CarYardMapper carYardMapper;
  @Mock private CreditApplicationRepository creditApplicationRepository;
  @Mock private CustomerAssignmentRepository customerAssignmentRepository;
  @Mock private SaleExecutiveRepository saleExecutiveRepository;
  @InjectMocks private CarYardServiceImpl carYardService;

  @Test
  public void shouldSaveCarYardAndReturnResponse() {
    CarYardRequest carYardRequest = CarYardRequest.builder().build();
    CarYard carYard = CarYard.builder().build();
    CarYardResponse expectedResponse = CarYardResponse.builder().build();

    when(carYardMapper.fromRequest(carYardRequest)).thenReturn(carYard);
    when(carYardMapper.fromEntity(carYard)).thenReturn(expectedResponse);

    CarYardResponse actualResponse = carYardService.create(carYardRequest);

    verify(carYardRepository).save(carYard);
    Assertions.assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldUpdateExistingCarYardAndReturnResponse() {
    CarYardRequest carYardRequest = CarYardRequest.builder().build();
    CarYard carYard = CarYard.builder().build();
    CarYardResponse expectedResponse = CarYardResponse.builder().build();

    when(carYardRepository.findById(anyLong())).thenReturn(Optional.of(carYard));
    when(carYardMapper.fromEntity(carYard)).thenReturn(expectedResponse);

    CarYardResponse actualResponse = carYardService.update(anyLong(), carYardRequest);

    verify(carYardMapper).update(carYardRequest, carYard);
    verify(carYardRepository).save(carYard);
    Assertions.assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldThrowNotFoundExceptionWhenUpdateCarYard() {
    CarYardRequest carYardRequest = CarYardRequest.builder().build();

    when(carYardRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    Assertions.assertThrows(
        CarYardNotFoundException.class, () -> carYardService.update(anyLong(), carYardRequest));
  }

  @Test
  public void shouldDisableIfCarYardExistsWhenDeleteIsPerformed() {
    CarYard carYard = CarYard.builder().build();

    when(carYardRepository.findById(anyLong())).thenReturn(Optional.of(carYard));
    when(creditApplicationRepository.existsCarYardInformation(anyLong())).thenReturn(false);
    when(customerAssignmentRepository.existsCarYardInformation(anyLong())).thenReturn(false);
    when(saleExecutiveRepository.existsCarYardInformation(anyLong())).thenReturn(false);

    carYardService.delete(anyLong());

    verify(carYardRepository).save(carYard);
    Assertions.assertEquals(Status.DISABLED, carYard.getStatus());
  }

  @Test
  public void shouldThrowAlreadyDisabledExceptionWhenDeleteIsPerformed() {
    CarYard carYard = CarYard.builder().build();
    carYard.setStatus(Status.DISABLED);

    when(carYardRepository.findById(anyLong())).thenReturn(Optional.of(carYard));
    when(messageService.getMessage(any())).thenReturn("");

    Assertions.assertThrows(
        CarYardAlreadyDisabledException.class, () -> carYardService.delete(anyLong()));
  }

  @Test
  public void shouldThrowNotFoundExceptionWhenDeleteIsPerformed() {

    when(carYardRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    Assertions.assertThrows(
        CarYardNotFoundException.class, () -> carYardService.delete(anyLong()));
  }


  @Test
  public void shouldThrowWithOperationsExceptionWhenDeleteIsPerformed() {
    CarYard carYard = CarYard.builder().build();

    when(carYardRepository.findById(anyLong())).thenReturn(Optional.of(carYard));
    when(creditApplicationRepository.existsCarYardInformation(anyLong())).thenReturn(true);
    when(customerAssignmentRepository.existsCarYardInformation(anyLong())).thenReturn(true);
    when(saleExecutiveRepository.existsCarYardInformation(anyLong())).thenReturn(true);
    when(messageService.getMessage(any())).thenReturn("");

    // When-Then
    Assertions.assertThrows(
        CarYardWithOperationsException.class, () -> carYardService.delete(anyLong()));
  }

  @Test
  public void shouldReturnResponseWhenFindByIdIsPerformed() {
    CarYard carYard = CarYard.builder().build();
    CarYardResponse expectedResponse = CarYardResponse.builder().build();

    when(carYardRepository.findById(anyLong())).thenReturn(Optional.of(carYard));
    when(carYardMapper.fromEntity(carYard)).thenReturn(expectedResponse);

    CarYardResponse actualResponse = carYardService.findById(anyLong());

    Assertions.assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldThrowNotFoundExceptionWhenFindByIdIsPerformed() {
    when(carYardRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(messageService.getMessage(any())).thenReturn("");

    Assertions.assertThrows(
        CarYardNotFoundException.class, () -> carYardService.findById(anyLong()));
  }

  @Test
  public void shouldReturnListOfCarYardResponsesWhenFindAllIsPerformed() {
    List<CarYard> carYards = new ArrayList<>();
    carYards.add(CarYard.builder().build());
    List<CarYardResponse> expectedResponses = new ArrayList<>();
    expectedResponses.add(CarYardResponse.builder().build());

    when(carYardRepository.findAll()).thenReturn(carYards);
    when(carYardMapper.fromEntity(anyList())).thenReturn(expectedResponses);

    List<CarYardResponse> actualResponses = carYardService.findAll();

    Assertions.assertEquals(expectedResponses, actualResponses);
  }

  @Test
  public void shouldReturnOptionalOfCarYardWhenGetByIdIsPerformed() {
    CarYard carYard = CarYard.builder().build();
    when(carYardRepository.findById(anyLong())).thenReturn(Optional.of(carYard));

    Optional<CarYard> result = carYardService.getById(anyLong());

    Assertions.assertEquals(Optional.of(carYard), result);
  }

  @Test
  public void shouldReturnCarYardWhenFindCarYardByIdIsPerformed() {
    CarYard expectedCarYard = CarYard.builder().build();
    when(carYardRepository.getById(anyLong())).thenReturn(expectedCarYard);

    CarYard actualCarYard = carYardService.findCarYardById(anyLong());

    Assertions.assertEquals(expectedCarYard, actualCarYard);
  }
}
