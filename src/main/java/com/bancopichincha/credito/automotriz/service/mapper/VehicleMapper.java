package com.bancopichincha.credito.automotriz.service.mapper;

import com.bancopichincha.credito.automotriz.domain.Brand;
import com.bancopichincha.credito.automotriz.domain.Vehicle;
import com.bancopichincha.credito.automotriz.service.dto.VehicleRequest;
import com.bancopichincha.credito.automotriz.service.dto.VehicleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

  @Mapping(source = "brand", target = "brand")
  @Mapping(target = "availabilityStatus", ignore = true)
  Vehicle mapFromRequest(VehicleRequest vehicle, Brand brand);

  @Mapping(source = "brand.id", target = "brandId")
  VehicleResponse mapFromEntity(Vehicle vehicle);

  void mapFromRequest(VehicleRequest vehicleRequest, @MappingTarget Vehicle vehicle);
}
