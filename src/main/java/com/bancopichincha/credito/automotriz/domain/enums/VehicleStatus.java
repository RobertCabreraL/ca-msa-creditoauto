package com.bancopichincha.credito.automotriz.domain.enums;

import lombok.Getter;

@Getter
public enum VehicleStatus {
  AVAILABLE,
  UNAVAILABLE,
  RESERVED,
  SOLD;

  public boolean isAvailable() {
    return this.equals(AVAILABLE);
  }
}
