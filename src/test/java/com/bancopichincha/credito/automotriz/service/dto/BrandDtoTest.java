package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BrandDtoTest {

  @Test
  void testGetterSetter() {
    String name = "Test Brand";
    BrandDto brandDto = BrandDto.builder().name(name).build();
    assertEquals(name, brandDto.getName());
  }
}
