package com.bancopichincha.credito.automotriz.domain;

import static lombok.AccessLevel.PRIVATE;

import com.bancopichincha.credito.automotriz.domain.enums.Status;
import java.io.Serializable;
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
@Table(name = "car_yard")
public class CarYard implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "name", nullable = false)
  String name;

  @Column(name = "address", nullable = false)
  String address;

  @Column(name = "phone", nullable = false)
  String phone;

  @Column(name = "nro_sales_point", nullable = false)
  Integer nroSalesPoint;

  @Builder.Default
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  Status status = Status.ENABLED;
}
