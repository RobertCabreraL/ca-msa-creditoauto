package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.Test;

class CreditApplicationResponseTest {

  @Test
  void testCreditApplicationResponse() {
    Date elaborationDate = new Date();
    CustomerResponse customer = new CustomerResponse();
    CarYardResponse carYard = new CarYardResponse();
    VehicleResponse vehicle = new VehicleResponse();
    Integer monthMax = 36;
    Integer quotes = 12;
    BigDecimal initialAmount = BigDecimal.valueOf(5000.00);
    SaleExecutiveResponse saleExecutive = new SaleExecutiveResponse();
    String observation = "Test observation";

    CreditApplicationResponse response =
        CreditApplicationResponse.builder()
            .elaborationDate(elaborationDate)
            .customer(customer)
            .carYard(carYard)
            .vehicle(vehicle)
            .monthMax(monthMax)
            .quotes(quotes)
            .initialAmount(initialAmount)
            .saleExecutive(saleExecutive)
            .observation(observation)
            .build();

    assertEquals(elaborationDate, response.getElaborationDate());
    assertEquals(customer, response.getCustomer());
    assertEquals(carYard, response.getCarYard());
    assertEquals(vehicle, response.getVehicle());
    assertEquals(monthMax, response.getMonthMax());
    assertEquals(quotes, response.getQuotes());
    assertEquals(initialAmount, response.getInitialAmount());
    assertEquals(saleExecutive, response.getSaleExecutive());
    assertEquals(observation, response.getObservation());
  }
}
