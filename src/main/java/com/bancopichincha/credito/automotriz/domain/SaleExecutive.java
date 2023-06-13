package com.bancopichincha.credito.automotriz.domain;

import static lombok.AccessLevel.PRIVATE;

import com.bancopichincha.credito.automotriz.domain.enums.Status;
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
@Table(name = "sale_executive")
public class SaleExecutive extends Person {

  @Column(name = "cellphone", nullable = false)
  String cellphone;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
      name = "car_yard_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_sale_executive_car_yard_id"),
      nullable = false)
  CarYard carYard;

  @Builder.Default
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  Status status = Status.ENABLED;
}
