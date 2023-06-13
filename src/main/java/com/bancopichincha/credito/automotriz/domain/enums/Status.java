package com.bancopichincha.credito.automotriz.domain.enums;

import lombok.Getter;

@Getter
public enum Status {
  ENABLED,
  DISABLED;

  public boolean isDisabled() {
    return this.equals(DISABLED);
  }
}
