package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CustomerAssignmentResponseTest {

  @Test
  void testCustomerAssignmentResponse() {
    Long id = 1L;
    CustomerDto customer = new CustomerDto();
    CarYardResponse carYard = new CarYardResponse();
    String assignmentDate = "2023-06-12";

    CustomerAssignmentResponse response =
        CustomerAssignmentResponse.builder()
            .id(id)
            .customer(customer)
            .carYard(carYard)
            .assignmentDate(assignmentDate)
            .build();

    assertEquals(id, response.getId());
    assertEquals(customer, response.getCustomer());
    assertEquals(carYard, response.getCarYard());
    assertEquals(assignmentDate, response.getAssignmentDate());
  }
}
