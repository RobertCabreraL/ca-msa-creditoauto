package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class VehicleRequestTest {

  @Test
  void testVehicleRequest() {
    String plate = "ABC123";
    String model = "Test Model";
    String nroChassis = "123456789";
    Long brandId = 1L;
    String type = "Test Type";
    Integer engineCapacity = 2000;
    BigDecimal appraisal = BigDecimal.valueOf(5000.00);
    Integer year = 2022;

    VehicleRequest request =
        VehicleRequest.builder()
            .plate(plate)
            .model(model)
            .nroChassis(nroChassis)
            .brandId(brandId)
            .type(type)
            .engineCapacity(engineCapacity)
            .appraisal(appraisal)
            .year(year)
            .build();

    assertEquals(plate, request.getPlate());
    assertEquals(model, request.getModel());
    assertEquals(nroChassis, request.getNroChassis());
    assertEquals(brandId, request.getBrandId());
    assertEquals(type, request.getType());
    assertEquals(engineCapacity, request.getEngineCapacity());
    assertEquals(appraisal, request.getAppraisal());
    assertEquals(year, request.getYear());
  }
}
