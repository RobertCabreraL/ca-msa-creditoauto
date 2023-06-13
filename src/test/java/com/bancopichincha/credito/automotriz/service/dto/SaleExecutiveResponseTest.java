package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bancopichincha.credito.automotriz.domain.enums.Status;
import org.junit.jupiter.api.Test;

class SaleExecutiveResponseTest {

  @Test
  void testSaleExecutiveResponse() {
    Long id = 1L;
    String identification = "123456789";
    String name = "John";
    String lastname = "Doe";
    String address = "Test Address";
    String phone = "123456789";
    Integer age = 30;
    String cellphone = "987654321";
    CarYardResponse carYard = new CarYardResponse();
    Status status = Status.ENABLED;

    SaleExecutiveResponse response =
        SaleExecutiveResponse.builder()
            .id(id)
            .identification(identification)
            .name(name)
            .lastname(lastname)
            .address(address)
            .phone(phone)
            .age(age)
            .cellphone(cellphone)
            .carYard(carYard)
            .status(status)
            .build();

    assertEquals(id, response.getId());
    assertEquals(identification, response.getIdentification());
    assertEquals(name, response.getName());
    assertEquals(lastname, response.getLastname());
    assertEquals(address, response.getAddress());
    assertEquals(phone, response.getPhone());
    assertEquals(age, response.getAge());
    assertEquals(cellphone, response.getCellphone());
    assertEquals(carYard, response.getCarYard());
    assertEquals(status, response.getStatus());
  }
}
