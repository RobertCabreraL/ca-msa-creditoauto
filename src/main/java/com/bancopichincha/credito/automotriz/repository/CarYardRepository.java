package com.bancopichincha.credito.automotriz.repository;

import com.bancopichincha.credito.automotriz.domain.CarYard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarYardRepository extends JpaRepository<CarYard, Long> {

  boolean existsByName(String name);
}
