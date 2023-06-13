package com.bancopichincha.credito.automotriz.service.impl;

import com.bancopichincha.credito.automotriz.domain.Brand;
import com.bancopichincha.credito.automotriz.repository.BrandRepository;
import com.bancopichincha.credito.automotriz.service.BrandService;
import com.bancopichincha.credito.automotriz.service.dto.BrandDto;
import com.bancopichincha.credito.automotriz.service.dto.BrandResponse;
import com.bancopichincha.credito.automotriz.service.mapper.BrandMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

  private final BrandRepository brandRepository;
  private final BrandMapper brandMapper;

  @Override
  public List<BrandResponse> findAll() {
    return brandMapper.brandDtoToEntities(brandRepository.findAll());
  }

  @Override
  public void saveBrands(List<BrandDto> brands) {
    List<Brand> brandList = brandMapper.brandDtoToEntityList(brands);
    List<Brand> brandNewsToSave =
        brandList.stream().filter(brand -> !alreadyExists(brand)).collect(Collectors.toList());

    brandRepository.saveAll(brandNewsToSave);
  }

  @Override
  public Optional<Brand> findById(Long brandId) {
    return brandRepository.findById(brandId);
  }

  private boolean alreadyExists(Brand brand) {
    return brandRepository.getBrandByName(brand.getName().trim()) != null;
  }
}
