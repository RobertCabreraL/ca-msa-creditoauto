package com.bancopichincha.credito.automotriz.service;

import com.bancopichincha.credito.automotriz.domain.SaleExecutive;
import com.bancopichincha.credito.automotriz.service.dto.SaleExecutiveDto;
import com.bancopichincha.credito.automotriz.service.dto.SaleExecutiveResponse;
import java.util.List;
import java.util.Optional;

public interface SaleExecutiveService {
  Optional<SaleExecutive> getById(Long saleExecutiveId);

  List<SaleExecutiveResponse> findAll();

  List<SaleExecutiveResponse> findAllBy(Long carYardId);

  void saveSaleExecutives(List<SaleExecutiveDto> saleExecutives);
}
