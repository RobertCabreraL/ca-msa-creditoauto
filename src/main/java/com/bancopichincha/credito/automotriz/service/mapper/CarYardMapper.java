package com.bancopichincha.credito.automotriz.service.mapper;

import com.bancopichincha.credito.automotriz.domain.CarYard;
import com.bancopichincha.credito.automotriz.service.dto.CarYardDto;
import com.bancopichincha.credito.automotriz.service.dto.CarYardRequest;
import com.bancopichincha.credito.automotriz.service.dto.CarYardResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CarYardMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  CarYard fromRequest(CarYardRequest carYardRequest);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  void update(CarYardRequest carYardRequest, @MappingTarget CarYard carYard);

  CarYardResponse fromEntity(CarYard carYard);

  List<CarYardResponse> fromEntity(List<CarYard> carYards);

  List<CarYard> fromDtos(List<CarYardDto> carYards);
}
