package com.bancopichincha.credito.automotriz.repository;

import com.bancopichincha.credito.automotriz.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

  Brand getBrandByName(String name);
}
