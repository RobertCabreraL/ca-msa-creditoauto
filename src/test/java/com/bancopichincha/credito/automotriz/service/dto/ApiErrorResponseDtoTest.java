package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ApiErrorResponseDtoTest {

  @Test
  void generateDtoTest() {
    String error = "Test Error";
    ApiErrorResponseDto dto = ApiErrorResponseDto.generateDto(error);
    assertEquals(error, dto.getError());
  }

  @Test
  void getterSetterTest() {
    String error = "Test Error";
    ApiErrorResponseDto dto = ApiErrorResponseDto.builder().error(error).build();

    assertEquals(error, dto.getError());
  }
}
