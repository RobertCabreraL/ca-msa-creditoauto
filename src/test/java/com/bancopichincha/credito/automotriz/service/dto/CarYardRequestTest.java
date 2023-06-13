package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CarYardRequestTest {

  @Test
  void testCarYardRequest() {
    String name = "Test Yard";
    String address = "Test Address";
    String phone = "Test Phone";
    Integer nroSalesPoint = 5;

    CarYardRequest carYardRequest =
        CarYardRequest.builder()
            .name(name)
            .address(address)
            .phone(phone)
            .nroSalesPoint(nroSalesPoint)
            .build();

    assertEquals(name, carYardRequest.getName());
    assertEquals(address, carYardRequest.getAddress());
    assertEquals(phone, carYardRequest.getPhone());
    assertEquals(nroSalesPoint, carYardRequest.getNroSalesPoint());
  }
}
