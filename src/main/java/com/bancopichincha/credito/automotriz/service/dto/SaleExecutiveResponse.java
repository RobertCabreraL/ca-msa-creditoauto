package com.bancopichincha.credito.automotriz.service.dto;

import static lombok.AccessLevel.PRIVATE;

import com.bancopichincha.credito.automotriz.domain.enums.Status;
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
public class SaleExecutiveResponse {
  Long id;
  String identification;
  String name;
  String lastname;
  String address;
  String phone;
  Integer age;
  String cellphone;
  CarYardResponse carYard;
  Status status;
}
