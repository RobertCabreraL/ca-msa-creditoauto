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
public class VehicleResponse {
  String plate;
  String model;
  String nroChassis;
  Long brandId;
  String type;
  Integer engineCapacity;
  String availabilityStatus;
  BigDecimal appraisal;
}
