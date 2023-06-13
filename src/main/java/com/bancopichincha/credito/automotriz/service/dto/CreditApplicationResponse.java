package com.bancopichincha.credito.automotriz.service.dto;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreditApplicationResponse {
  Date elaborationDate;
  CustomerResponse customer;
  CarYardResponse carYard;
  VehicleResponse vehicle;
  Integer monthMax;
  Integer quotes;
  BigDecimal initialAmount;
  SaleExecutiveResponse saleExecutive;
  String observation;
}
