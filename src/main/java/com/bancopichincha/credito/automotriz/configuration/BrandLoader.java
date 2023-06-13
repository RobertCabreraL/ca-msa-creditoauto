package com.bancopichincha.credito.automotriz.configuration;

import com.bancopichincha.credito.automotriz.service.BrandService;
import com.bancopichincha.credito.automotriz.service.dto.BrandDto;
import com.bancopichincha.credito.automotriz.util.CsvReaderUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandLoader {

  private final BrandService brandService;

  @Value("${csv.files.brands}")
  private String csvFinalPath;

  public void loadBrandData() {
    try {
      List<String[]> data = CsvReaderUtil.loadCsvData(csvFinalPath);
      List<BrandDto> brands = mapDataToBrandDto(data);
      brandService.saveBrands(brands);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<BrandDto> mapDataToBrandDto(List<String[]> csvData) {
    List<BrandDto> brands = new ArrayList<>();

    for (String[] data : csvData) {
      if (brands.stream().noneMatch(brand -> Objects.equals(brand.getName(), data[0]))) {
        brands.add(mapDataToBrandDto(data));
      }
    }

    return brands;
  }

  private BrandDto mapDataToBrandDto(String[] data) {
    return BrandDto.builder().name(data[0]).build();
  }
}
