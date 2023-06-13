package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CustomerAssignmentRequestTest {

  @Test
  void testCustomerAssignmentRequest() {
    String customerIdentification = "123456789";
    Long carYardId = 1L;
    String assignmentDate = "2023-06-12";

    CustomerAssignmentRequest request =
        CustomerAssignmentRequest.builder()
            .customerIdentification(customerIdentification)
            .carYardId(carYardId)
            .assignmentDate(assignmentDate)
            .build();

    assertEquals(customerIdentification, request.getCustomerIdentification());
    assertEquals(carYardId, request.getCarYardId());
    assertEquals(assignmentDate, request.getAssignmentDate());
  }
}
