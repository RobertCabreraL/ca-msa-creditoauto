package com.bancopichincha.credito.automotriz.service.impl;

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
import com.bancopichincha.credito.automotriz.service.CustomerAssignmentService;
import com.bancopichincha.credito.automotriz.service.CustomerService;
import com.bancopichincha.credito.automotriz.service.dto.CustomerAssignmentRequest;
import com.bancopichincha.credito.automotriz.service.dto.CustomerAssignmentResponse;
import com.bancopichincha.credito.automotriz.service.mapper.CustomerAssignmentMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerAssignmentServiceImpl implements CustomerAssignmentService {
  private final MessageService messageService;
  private final CustomerService customerService;
  private final CarYardService carYardService;
  private final CustomerAssignmentMapper customerAssignmentMapper;
  private final CustomerAssignmentRepository customerAssignmentRepository;

  @Override
  public CustomerAssignmentResponse create(CustomerAssignmentRequest request) {
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

    CustomerAssignment customerAssignment =
        customerAssignmentMapper.mapToEntity(request, carYard, customer);
    customerAssignmentRepository.save(customerAssignment);

    return customerAssignmentMapper.mapToResponse(customerAssignment);
  }

  @Override
  public CustomerAssignmentResponse update(Long id, CustomerAssignmentRequest request) {
    CustomerAssignment customerAssignment =
        customerAssignmentRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new CustomerAssignmentNotFoundException(
                        messageService.getMessage("customer_assignment.not_found")));

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

    customerAssignmentMapper.update(request, carYard, customer, customerAssignment);
    customerAssignmentRepository.save(customerAssignment);

    return customerAssignmentMapper.mapToResponse(customerAssignment);
  }

  @Override
  public void delete(Long id) {
    CustomerAssignment customerAssignment =
        customerAssignmentRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new CustomerAssignmentNotFoundException(
                        messageService.getMessage("customer_assignment.not_found")));

    if (customerAssignment.getStatus().isDisabled()) {
      throw new CustomerAssignmentDisabledException(
          messageService.getMessage("global.already_disabled"));
    }

    customerAssignment.setStatus(Status.DISABLED);
    customerAssignmentRepository.save(customerAssignment);
  }

  @Override
  public List<CustomerAssignmentResponse> findBy(Long customerId, Long carYardId) {
    List<CustomerAssignment> customerAssignments =
        customerAssignmentRepository.findByCustomerIdAndCarYardId(customerId, carYardId);
    return customerAssignmentMapper.mapToResponse(customerAssignments);
  }
}
