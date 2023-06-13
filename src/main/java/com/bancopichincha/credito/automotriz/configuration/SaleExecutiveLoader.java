package com.bancopichincha.credito.automotriz.configuration;

import com.bancopichincha.credito.automotriz.service.SaleExecutiveService;
import com.bancopichincha.credito.automotriz.service.dto.SaleExecutiveDto;
import com.bancopichincha.credito.automotriz.util.CsvReaderUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleExecutiveLoader {

  private final SaleExecutiveService saleExecutiveService;

  @Value("${csv.files.sale-executives}")
  private String csvFinalPath;

  public void loadSaleExecutiveData() {
    try {
      List<String[]> data = CsvReaderUtil.loadCsvData(csvFinalPath);
      List<SaleExecutiveDto> saleExecutives = mapDataToSaleExecutiveDto(data);
      saleExecutiveService.saveSaleExecutives(saleExecutives);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<SaleExecutiveDto> mapDataToSaleExecutiveDto(List<String[]> csvData) {
    List<SaleExecutiveDto> saleExecutives = new ArrayList<>();

    for (String[] data : csvData) {
      if (saleExecutives.stream()
          .noneMatch(saleExecutive -> Objects.equals(saleExecutive.getName(), data[0]))) {
        saleExecutives.add(mapDataToSaleExecutiveDto(data));
      }
    }

    return saleExecutives;
  }

  private SaleExecutiveDto mapDataToSaleExecutiveDto(String[] data) {
    return SaleExecutiveDto.builder()
        .identification(data[0])
        .name(data[1])
        .lastname(data[2])
        .address(data[3])
        .phone(data[4])
        .age(Integer.valueOf(data[5]))
        .cellphone(data[6])
        .carYardId(Long.valueOf(data[7]))
        .build();
  }
}
