package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CreditApplicationRequestTest {

  @Test
  void testCreditApplicationRequest() {
    String elaborationDate = "2023-06-12";
    String customerIdentification = "123456789";
    Long carYardId = 1L;
    String vehiclePlate = "ABC123";
    Integer monthMax = 36;
    Integer quotes = 12;
    BigDecimal initialAmount = BigDecimal.valueOf(5000.00);
    Long saleExecutiveId = 2L;
    String observation = "Test observation";

    CreditApplicationRequest request =
        CreditApplicationRequest.builder()
            .elaborationDate(elaborationDate)
            .customerIdentification(customerIdentification)
            .carYardId(carYardId)
            .vehiclePlate(vehiclePlate)
            .monthMax(monthMax)
            .quotes(quotes)
            .initialAmount(initialAmount)
            .saleExecutiveId(saleExecutiveId)
            .observation(observation)
            .build();

    assertEquals(elaborationDate, request.getElaborationDate());
    assertEquals(customerIdentification, request.getCustomerIdentification());
    assertEquals(carYardId, request.getCarYardId());
    assertEquals(vehiclePlate, request.getVehiclePlate());
    assertEquals(monthMax, request.getMonthMax());
    assertEquals(quotes, request.getQuotes());
    assertEquals(initialAmount, request.getInitialAmount());
    assertEquals(saleExecutiveId, request.getSaleExecutiveId());
    assertEquals(observation, request.getObservation());
  }
}
