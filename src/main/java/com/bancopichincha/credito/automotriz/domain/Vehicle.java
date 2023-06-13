package com.bancopichincha.credito.automotriz.domain;

import static lombok.AccessLevel.PRIVATE;

import com.bancopichincha.credito.automotriz.domain.enums.Status;
import com.bancopichincha.credito.automotriz.domain.enums.VehicleStatus;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(
    name = "vehicle",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_vehicle_plate",
          columnNames = {"plate"})
    })
public class Vehicle implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "plate", unique = true, nullable = false)
  String plate;

  @Column(name = "model", nullable = false)
  String model;

  @Column(name = "nro_chassis", nullable = false)
  String nroChassis;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
      name = "brand_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_vehicle_brand_id"),
      nullable = false)
   Brand brand;

  @Column(name = "type")
  String type;

  @Column(name = "engine_capacity", nullable = false)
  Integer engineCapacity;

  @Column(name = "appraisal", nullable = false)
  BigDecimal appraisal;

  @Column(name = "year", nullable = false)
  Integer year;

  @Builder.Default
  @Column(name = "availability_status", nullable = false)
  @Enumerated(EnumType.STRING)
  VehicleStatus availabilityStatus = VehicleStatus.AVAILABLE;

  @Builder.Default
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  Status status = Status.ENABLED;
}
