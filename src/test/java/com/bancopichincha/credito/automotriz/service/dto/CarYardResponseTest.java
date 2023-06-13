package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CarYardResponseTest {

  @Test
  void testCarYardResponse() {
    Long id = 1L;
    String name = "Test Yard";
    String address = "Test Address";
    String phone = "Test Phone";
    Integer nroSalesPoint = 5;
    String status = "Active";

    CarYardResponse carYardResponse =
        CarYardResponse.builder()
            .id(id)
            .name(name)
            .address(address)
            .phone(phone)
            .nroSalesPoint(nroSalesPoint)
            .status(status)
            .build();

    assertEquals(id, carYardResponse.getId());
    assertEquals(name, carYardResponse.getName());
    assertEquals(address, carYardResponse.getAddress());
    assertEquals(phone, carYardResponse.getPhone());
    assertEquals(nroSalesPoint, carYardResponse.getNroSalesPoint());
    assertEquals(status, carYardResponse.getStatus());
  }
}
