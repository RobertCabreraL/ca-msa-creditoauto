package com.bancopichincha.credito.automotriz.service;

import com.bancopichincha.credito.automotriz.domain.Brand;
import com.bancopichincha.credito.automotriz.service.dto.BrandDto;
import com.bancopichincha.credito.automotriz.service.dto.BrandResponse;
import java.util.List;
import java.util.Optional;

public interface BrandService {

  List<BrandResponse> findAll();

  void saveBrands(List<BrandDto> brands);

    Optional<Brand> findById(Long brandId);
}
