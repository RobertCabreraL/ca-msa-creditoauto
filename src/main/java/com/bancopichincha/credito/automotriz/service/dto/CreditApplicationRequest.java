package com.bancopichincha.credito.automotriz.service.dto;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
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
public class CreditApplicationRequest {
  String elaborationDate;
  String customerIdentification;
  Long carYardId;
  String vehiclePlate;
  Integer monthMax;
  Integer quotes;
  BigDecimal initialAmount;
  Long saleExecutiveId;
  String observation;
}
