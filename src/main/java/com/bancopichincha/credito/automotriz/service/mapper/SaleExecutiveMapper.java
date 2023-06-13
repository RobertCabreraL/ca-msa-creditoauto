package com.bancopichincha.credito.automotriz.service.mapper;

import com.bancopichincha.credito.automotriz.domain.*;
import com.bancopichincha.credito.automotriz.service.CarYardService;
import com.bancopichincha.credito.automotriz.service.dto.SaleExecutiveDto;
import com.bancopichincha.credito.automotriz.service.dto.SaleExecutiveResponse;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class SaleExecutiveMapper {

  @Autowired private CarYardService carYardService;

  public abstract List<SaleExecutiveResponse> mapToResponse(List<SaleExecutive> saleExecutives);

  @Mapping(target = "carYard", expression = "java(mapCarYard(saleExecutiveDto.getCarYardId()))")
  public abstract SaleExecutive mapToEntity(SaleExecutiveDto saleExecutiveDto);

  public abstract List<SaleExecutive> mapToEntity(List<SaleExecutiveDto> saleExecutivesDto);

  protected CarYard mapCarYard(Long carYardId) {
    if (carYardId == null) {
      return null;
    }
    return carYardService.findCarYardById(carYardId);
  }
}
