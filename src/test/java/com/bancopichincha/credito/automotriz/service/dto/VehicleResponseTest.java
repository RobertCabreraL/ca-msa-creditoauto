package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class VehicleResponseTest {

  @Test
  void testVehicleResponse() {
    String plate = "ABC123";
    String model = "Test Model";
    String nroChassis = "123456789";
    Long brandId = 1L;
    String type = "Test Type";
    Integer engineCapacity = 2000;
    String availabilityStatus = "Available";
    BigDecimal appraisal = BigDecimal.valueOf(5000.00);

    VehicleResponse response =
        VehicleResponse.builder()
            .plate(plate)
            .model(model)
            .nroChassis(nroChassis)
            .brandId(brandId)
            .type(type)
            .engineCapacity(engineCapacity)
            .availabilityStatus(availabilityStatus)
            .appraisal(appraisal)
            .build();

    assertEquals(plate, response.getPlate());
    assertEquals(model, response.getModel());
    assertEquals(nroChassis, response.getNroChassis());
    assertEquals(brandId, response.getBrandId());
    assertEquals(type, response.getType());
    assertEquals(engineCapacity, response.getEngineCapacity());
    assertEquals(availabilityStatus, response.getAvailabilityStatus());
    assertEquals(appraisal, response.getAppraisal());
  }
}
