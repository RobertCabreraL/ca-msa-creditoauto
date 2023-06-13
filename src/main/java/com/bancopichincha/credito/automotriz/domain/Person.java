package com.bancopichincha.credito.automotriz.domain;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Entity
@Table(
    name = "person",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_person_identification",
          columnNames = {"identification"})
    })
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "identification", nullable = false, unique = true)
  String identification;

  @Column(name = "name", nullable = false)
  String name;

  @Column(name = "lastname", nullable = false)
  String lastname;

  @Column(name = "address", nullable = false)
  String address;

  @Column(name = "phone", nullable = false)
  String phone;

  @Column(name = "age", nullable = false)
  Integer age;
}
