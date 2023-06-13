package com.bancopichincha.credito.automotriz.domain;

import static lombok.AccessLevel.PRIVATE;

import com.bancopichincha.credito.automotriz.domain.enums.CustomerCreditSubject;
import com.bancopichincha.credito.automotriz.domain.enums.Status;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
@Entity
@Table(name = "customer")
public class Customer extends Person {

  @Column(name = "birthday")
  Date birthday;

  @Column(name = "marital_status", nullable = false)
  String maritalStatus;

  @Column(name = "partner_identification")
  String partnerIdentification;

  @Column(name = "partner_name")
  String partnerName;

  @Column(name = "credit_subject", nullable = false)
  CustomerCreditSubject creditSubject;

  @Builder.Default
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  Status status = Status.ENABLED;
}
