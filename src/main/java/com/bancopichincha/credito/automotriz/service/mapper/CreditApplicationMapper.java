package com.bancopichincha.credito.automotriz.service.mapper;

import com.bancopichincha.credito.automotriz.domain.*;
import com.bancopichincha.credito.automotriz.service.dto.CreditApplicationRequest;
import com.bancopichincha.credito.automotriz.service.dto.CreditApplicationResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CreditApplicationMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "customer", source = "customer")
  @Mapping(target = "carYard", source = "carYard")
  @Mapping(target = "vehicle", source = "vehicle")
  @Mapping(target = "saleExecutive", source = "saleExecutive")
  @Mapping(
      target = "elaborationDate",
      source = "creditApplicationRequest.elaborationDate",
      dateFormat = "yyyy-MM-dd")
  CreditApplication mapToEntity(
      CreditApplicationRequest creditApplicationRequest,
      Customer customer,
      CarYard carYard,
      Vehicle vehicle,
      SaleExecutive saleExecutive);

  @Mapping(target = "customer", source = "customer")
  @Mapping(target = "carYard", source = "carYard")
  @Mapping(target = "vehicle", source = "vehicle")
  @Mapping(target = "saleExecutive", source = "saleExecutive")
  @Mapping(target = "elaborationDate", source = "elaborationDate", dateFormat = "yyyy-MM-dd")
  CreditApplicationResponse mapToResponse(CreditApplication creditApplication);

  List<CreditApplicationResponse> mapToResponse(List<CreditApplication> creditApplications);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(
      target = "elaborationDate",
      source = "creditApplicationRequest.elaborationDate",
      dateFormat = "yyyy-MM-dd")
  void update(
      CreditApplicationRequest creditApplicationRequest,
      Customer customer,
      CarYard carYard,
      Vehicle vehicle,
      SaleExecutive saleExecutive,
      @MappingTarget CreditApplication creditApplication);
}
