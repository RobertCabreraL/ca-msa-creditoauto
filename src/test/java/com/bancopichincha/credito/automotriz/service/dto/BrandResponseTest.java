package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BrandResponseTest {

  @Test
  void testBrandResponse() {
    Long id = 1L;
    String name = "Test Brand";
    String status = "Active";

    BrandResponse brandResponse = BrandResponse.builder().id(id).name(name).status(status).build();

    assertEquals(id, brandResponse.getId());
    assertEquals(name, brandResponse.getName());
    assertEquals(status, brandResponse.getStatus());
  }
}
