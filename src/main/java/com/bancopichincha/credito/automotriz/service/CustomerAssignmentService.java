package com.bancopichincha.credito.automotriz.service;

import com.bancopichincha.credito.automotriz.service.dto.CustomerAssignmentRequest;
import com.bancopichincha.credito.automotriz.service.dto.CustomerAssignmentResponse;
import java.util.List;

public interface CustomerAssignmentService {
  CustomerAssignmentResponse create(CustomerAssignmentRequest customerAssignmentRequest);

  CustomerAssignmentResponse update(Long id, CustomerAssignmentRequest customerAssignmentRequest);

  void delete(Long id);

  List<CustomerAssignmentResponse> findBy(Long customerId, Long carYardId);
}
