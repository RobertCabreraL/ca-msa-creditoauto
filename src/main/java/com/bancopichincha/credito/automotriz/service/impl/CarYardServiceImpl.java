package com.bancopichincha.credito.automotriz.service.impl;

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
import com.bancopichincha.credito.automotriz.service.CarYardService;
import com.bancopichincha.credito.automotriz.service.dto.CarYardDto;
import com.bancopichincha.credito.automotriz.service.dto.CarYardRequest;
import com.bancopichincha.credito.automotriz.service.dto.CarYardResponse;
import com.bancopichincha.credito.automotriz.service.mapper.CarYardMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarYardServiceImpl implements CarYardService {

  private final MessageService messageService;
  private final CarYardRepository carYardRepository;
  private final CarYardMapper carYardMapper;
  private final CreditApplicationRepository creditApplicationRepository;
  private final CustomerAssignmentRepository customerAssignmentRepository;
  private final SaleExecutiveRepository saleExecutiveRepository;

  @Override
  public CarYardResponse create(CarYardRequest carYardRequest) {
    CarYard carYard = carYardMapper.fromRequest(carYardRequest);
    carYardRepository.save(carYard);

    return carYardMapper.fromEntity(carYard);
  }

  @Override
  public CarYardResponse update(Long id, CarYardRequest carYardRequest) {
    CarYard carYard =
        carYardRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new CarYardNotFoundException(messageService.getMessage("car_yard.not_found")));

    carYardMapper.update(carYardRequest, carYard);
    carYardRepository.save(carYard);

    return carYardMapper.fromEntity(carYard);
  }

  @Override
  public void delete(Long id) {
    CarYard carYard =
        carYardRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new CarYardNotFoundException(messageService.getMessage("car_yard.not_found")));

    if (carYard.getStatus().isDisabled()) {
      throw new CarYardAlreadyDisabledException(
          messageService.getMessage("global.already_disabled"));
    }

    if (creditApplicationRepository.existsCarYardInformation(id)
        || customerAssignmentRepository.existsCarYardInformation(id)
        || saleExecutiveRepository.existsCarYardInformation(id)) {
      throw new CarYardWithOperationsException(
          messageService.getMessage("car_yard.with_operations"));
    }

    carYard.setStatus(Status.DISABLED);

    carYardRepository.save(carYard);
  }

  @Override
  public CarYardResponse findById(Long id) {
    CarYard carYard =
        carYardRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new CarYardNotFoundException(messageService.getMessage("car_yard.not_found")));

    return carYardMapper.fromEntity(carYard);
  }

  @Override
  public List<CarYardResponse> findAll() {
    return carYardMapper.fromEntity(carYardRepository.findAll());
  }

  @Override
  public Optional<CarYard> getById(Long id) {
    return carYardRepository.findById(id);
  }

  @Override
  public CarYard findCarYardById(Long carYardId) {
    return carYardRepository.getById(carYardId);
  }

  @Override
  public void saveCarYards(List<CarYardDto> carYardDtos) {
    List<CarYard> carYards = carYardMapper.fromDtos(carYardDtos);

    List<CarYard> carYardsToSave =
        carYards.stream().filter(carYard -> !alreadyExists(carYard)).collect(Collectors.toList());

    carYardRepository.saveAll(carYardsToSave);
  }

  private boolean alreadyExists(CarYard carYard) {
    return carYardRepository.existsByName(carYard.getName());
  }
}
