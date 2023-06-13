package com.bancopichincha.credito.automotriz.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.bancopichincha.credito.automotriz.domain.Brand;
import com.bancopichincha.credito.automotriz.repository.BrandRepository;
import com.bancopichincha.credito.automotriz.service.dto.BrandDto;
import com.bancopichincha.credito.automotriz.service.dto.BrandResponse;
import com.bancopichincha.credito.automotriz.service.mapper.BrandMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BrandServiceImplTest {

  @Mock private BrandRepository brandRepository;
  @Mock private BrandMapper brandMapper;
  @InjectMocks private BrandServiceImpl brandService;

  @Test
  void shouldReturnListWhenFindAllIsExecuted() {

    Brand toyota = Brand.builder().id(1L).name("Toyota").build();
    Brand audi = Brand.builder().id(2L).name("Audi").build();
    Brand ferrari = Brand.builder().id(3L).name("Ferrari").build();

    BrandResponse toyotaResponse = BrandResponse.builder().id(1L).name("Toyota").build();
    BrandResponse audiResponse = BrandResponse.builder().id(2L).name("Audi").build();
    BrandResponse ferrariResponse = BrandResponse.builder().id(3L).name("Ferrari").build();

    List<Brand> brandList = Arrays.asList(toyota, audi, ferrari);
    when(brandRepository.findAll()).thenReturn(brandList);
    when(brandMapper.brandDtoToEntities(anyList()))
        .thenReturn(Arrays.asList(toyotaResponse, audiResponse, ferrariResponse));

    List<BrandResponse> result = brandService.findAll();

    Assertions.assertEquals(brandList.size(), result.size());
    for (int i = 0; i < brandList.size(); i++) {
      Brand brand = brandList.get(i);
      BrandResponse response = result.get(i);
      Assertions.assertEquals(brand.getId(), response.getId());
      Assertions.assertEquals(brand.getName(), response.getName());
    }

    verify(brandRepository, times(1)).findAll();
  }

  @Test
  void shouldSaveBrandAndCallItOneTime() {

    BrandDto toyota = BrandDto.builder().name("Toyota").build();
    BrandDto audi = BrandDto.builder().name("Audi").build();
    BrandDto ferrari = BrandDto.builder().name("Ferrari").build();

    List<BrandDto> brandDtoList = Arrays.asList(toyota, audi, ferrari);

    List<Brand> brandList = brandMapper.brandDtoToEntityList(brandDtoList);

    when(brandMapper.brandDtoToEntityList(any())).thenReturn(brandList);
    when(brandRepository.saveAll(any())).thenReturn(brandList);

    brandService.saveBrands(brandDtoList);

    verify(brandRepository, times(1)).saveAll(brandList);
  }

  @Test
  void shouldReturnBrandWhenFindByIdIsPerformed() {
    Long brandId = 1L;
    Brand brand = Brand.builder().id(brandId).name("Totyota").build();
    when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));

    Optional<Brand> result = brandService.findById(brandId);

    Assertions.assertTrue(result.isPresent());
    Brand brandSaved = result.get();
    Assertions.assertEquals(brand.getName(), brandSaved.getName());

    verify(brandRepository, times(1)).findById(brandId);
  }

  @Test
  void shouldReturnOptionalEmptyWhenFindByIdIsPerformed() {
    Long brandId = 1L;
    when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

    Optional<Brand> result = brandService.findById(brandId);

    Assertions.assertFalse(result.isPresent());

    verify(brandRepository, times(1)).findById(brandId);
  }
}
