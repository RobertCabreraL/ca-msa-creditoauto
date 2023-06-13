package com.bancopichincha.credito.automotriz.service.mapper;

import com.bancopichincha.credito.automotriz.domain.Brand;
import com.bancopichincha.credito.automotriz.service.dto.BrandDto;
import com.bancopichincha.credito.automotriz.service.dto.BrandResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  Brand brandDtoToEntity(BrandDto brandDto);

  List<Brand> brandDtoToEntityList(List<BrandDto> brands);

  List<BrandResponse> brandDtoToEntities(List<Brand> all);

}
