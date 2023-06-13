package com.bancopichincha.credito.automotriz.service.dto;

import static lombok.AccessLevel.PRIVATE;

import com.bancopichincha.credito.automotriz.domain.enums.CustomerCreditSubject;
import java.util.Date;
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
public class CustomerResponse {
  String identification;
  String name;
  String lastname;
  Integer age;
  Date birthday;
  String address;
  String phone;
  String maritalStatus;
  String partnerIdentification;
  String partnerName;
  CustomerCreditSubject creditSubject;
}
