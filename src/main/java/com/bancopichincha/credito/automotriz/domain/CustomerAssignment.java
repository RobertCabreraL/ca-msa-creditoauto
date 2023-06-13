package com.bancopichincha.credito.automotriz.domain;

import static lombok.AccessLevel.PRIVATE;

import com.bancopichincha.credito.automotriz.domain.enums.Status;
import java.io.Serializable;
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
@Table(name = "customer_assignment")
public class CustomerAssignment implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
      name = "customer_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_customer_assignment_customer_id"), nullable = false)
   Customer customer;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
      name = "car_yard_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_customer_assignment_car_yard_id"),
      nullable = false)
   CarYard carYard;

  @Column(name = "assignment_date", nullable = false)
  Date assignmentDate;

  @Builder.Default
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  Status status = Status.ENABLED;
}
