package com.bancopichincha.credito.automotriz.configuration;

import com.bancopichincha.credito.automotriz.service.CarYardService;
import com.bancopichincha.credito.automotriz.service.dto.CarYardDto;
import com.bancopichincha.credito.automotriz.util.CsvReaderUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarYardsLoader {

  private final CarYardService carYardService;

  @Value("${csv.files.car-yards}")
  private String csvFinalPath;

  public void loadCarYardData() {
    try {
      List<String[]> data = CsvReaderUtil.loadCsvData(csvFinalPath);
      List<CarYardDto> carYards = mapDataToCarYardDto(data);
      carYardService.saveCarYards(carYards);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<CarYardDto> mapDataToCarYardDto(List<String[]> csvData) {
    List<CarYardDto> carYards = new ArrayList<>();

    for (String[] data : csvData) {
      if (carYards.stream().noneMatch(carYard -> Objects.equals(carYard.getName(), data[0]))) {
        carYards.add(mapDataToCarYardDto(data));
      }
    }

    return carYards;
  }

  private CarYardDto mapDataToCarYardDto(String[] data) {
    return CarYardDto.builder()
        .address(data[0])
        .name(data[1])
        .nroSalesPoint(Integer.valueOf(data[2]))
        .phone(data[3])
        .build();
  }
}
