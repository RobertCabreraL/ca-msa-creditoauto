package com.bancopichincha.credito.automotriz.service.impl;

import com.bancopichincha.credito.automotriz.domain.*;
import com.bancopichincha.credito.automotriz.domain.enums.CreditApplicationStatus;
import com.bancopichincha.credito.automotriz.exception.*;
import com.bancopichincha.credito.automotriz.helper.MessageService;
import com.bancopichincha.credito.automotriz.repository.CreditApplicationRepository;
import com.bancopichincha.credito.automotriz.service.*;
import com.bancopichincha.credito.automotriz.service.dto.CreditApplicationRequest;
import com.bancopichincha.credito.automotriz.service.dto.CreditApplicationResponse;
import com.bancopichincha.credito.automotriz.service.mapper.CreditApplicationMapper;
import com.bancopichincha.credito.automotriz.util.DateUtil;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditApplicationServiceImpl implements CreditApplicationService {
  private final MessageService messageService;
  private final CustomerService customerService;
  private final CarYardService carYardService;
  private final VehicleService vehicleService;
  private final SaleExecutiveService saleExecutiveService;
  private final CreditApplicationMapper creditApplicationMapper;
  private final CreditApplicationRepository creditApplicationRepository;

  @Override
  @Transactional
  public CreditApplicationResponse create(CreditApplicationRequest request) {
    if (creditApplicationRepository.existsBy(
        request.getCustomerIdentification(),
        DateUtil.parseToDate(request.getElaborationDate()),
        CreditApplicationStatus.ACTIVE)) {
      throw new CreditApplicationRegisteredException(
          messageService.getMessage("credit_application.already_registered"));
    }
    Vehicle vehicle =
        vehicleService
            .getByPlate(request.getVehiclePlate())
            .orElseThrow(
                () -> new VehicleNotFoundException(messageService.getMessage("vehicle.not_found")));

    if (!vehicle.getAvailabilityStatus().isAvailable()) {
      throw new VehicleNotAvailableException(messageService.getMessage("vehicle.not_available"));
    }

    Customer customer =
        customerService
            .getByIdentification(request.getCustomerIdentification())
            .orElseThrow(
                () ->
                    new CustomerNotFoundException(messageService.getMessage("customer.not_found")));

    CarYard carYard =
        carYardService
            .getById(request.getCarYardId())
            .orElseThrow(
                () ->
                    new CarYardNotFoundException(messageService.getMessage("car_yard.not_found")));

    SaleExecutive saleExecutive =
        saleExecutiveService
            .getById(request.getSaleExecutiveId())
            .orElseThrow(
                () ->
                    new SaleExecutiveNotFoundException(
                        messageService.getMessage("sale_executive.not_found")));

    CreditApplication creditApplication =
        creditApplicationMapper.mapToEntity(request, customer, carYard, vehicle, saleExecutive);
    creditApplicationRepository.save(creditApplication);
    vehicleService.reserve(request.getVehiclePlate());

    return creditApplicationMapper.mapToResponse(creditApplication);
  }

  @Override
  public CreditApplicationResponse update(
      Long id, CreditApplicationRequest creditApplicationRequest) {

    CreditApplication creditApplication =
        creditApplicationRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new CreditApplicationNotFoundException(
                        messageService.getMessage("credit_application.not_found")));

    Customer customer =
        customerService
            .getByIdentification(creditApplicationRequest.getCustomerIdentification())
            .orElseThrow(
                () ->
                    new CustomerNotFoundException(messageService.getMessage("customer.not_found")));

    CarYard carYard =
        carYardService
            .getById(creditApplicationRequest.getCarYardId())
            .orElseThrow(
                () ->
                    new CarYardNotFoundException(messageService.getMessage("car_yard.not_found")));

    Vehicle vehicle =
        vehicleService
            .getByPlate(creditApplicationRequest.getVehiclePlate())
            .orElseThrow(
                () -> new VehicleNotFoundException(messageService.getMessage("vehicle.not_found")));

    SaleExecutive saleExecutive =
        saleExecutiveService
            .getById(creditApplicationRequest.getSaleExecutiveId())
            .orElseThrow(
                () ->
                    new SaleExecutiveNotFoundException(
                        messageService.getMessage("sale_executive.not_found")));

    creditApplicationMapper.update(
        creditApplicationRequest, customer, carYard, vehicle, saleExecutive, creditApplication);

    creditApplicationRepository.save(creditApplication);

    return creditApplicationMapper.mapToResponse(creditApplication);
  }

  @Override
  public void delete(Long id) {
    CreditApplication creditApplication =
        creditApplicationRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new CreditApplicationNotFoundException(
                        messageService.getMessage("credit_application.not_found")));

    creditApplication.setStatus(CreditApplicationStatus.CANCELLED);
    creditApplicationRepository.save(creditApplication);
  }

  @Override
  public List<CreditApplicationResponse> findAll() {
    List<CreditApplication> creditApplications = creditApplicationRepository.findAll();
    return creditApplicationMapper.mapToResponse(creditApplications);
  }
}
