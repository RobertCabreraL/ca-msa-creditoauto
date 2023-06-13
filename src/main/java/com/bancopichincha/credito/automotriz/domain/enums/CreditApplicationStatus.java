package com.bancopichincha.credito.automotriz.domain.enums;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = PRIVATE)
public enum CreditApplicationStatus {
  //todo change this to a constant?
  ACTIVE("Activa"),
  REGISTERED("Registrada"),
  SENT("Despachada"),
  CANCELLED("Cancelada");

  final String value;

  CreditApplicationStatus(String value) {
    this.value = value;
  }
}
