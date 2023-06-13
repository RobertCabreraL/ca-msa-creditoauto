package com.bancopichincha.credito.automotriz.domain;

import static lombok.AccessLevel.PRIVATE;

import com.bancopichincha.credito.automotriz.domain.enums.CreditApplicationStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
@Entity
@Table(name = "credit_application")
public class CreditApplication implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "elaboration_date")
  Date elaborationDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
      name = "customer_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_credit_application_customer_id"))
  Customer customer;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
      name = "car_yard_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_credit_application_car_yard_id"))
  CarYard carYard;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
      name = "vehicle_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_credit_application_vehicle_id"),
      nullable = false)
  Vehicle vehicle;

  @Column(name = "month_max", nullable = false)
  Integer monthMax;

  @Column(name = "quotes", nullable = false)
  Integer quotes;

  @Column(name = "initial_amount", nullable = false)
  BigDecimal initialAmount;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
      name = "sale_executive_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_credit_application_sale_executive_id"),
      nullable = false)
  SaleExecutive saleExecutive;

  @Column(name = "observation")
  String observation;

  @Builder.Default
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  CreditApplicationStatus status = CreditApplicationStatus.ACTIVE;
}
