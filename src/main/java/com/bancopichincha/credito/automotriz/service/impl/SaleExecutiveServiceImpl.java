package com.bancopichincha.credito.automotriz.service.impl;

import com.bancopichincha.credito.automotriz.domain.SaleExecutive;
import com.bancopichincha.credito.automotriz.repository.SaleExecutiveRepository;
import com.bancopichincha.credito.automotriz.service.SaleExecutiveService;
import com.bancopichincha.credito.automotriz.service.dto.SaleExecutiveDto;
import com.bancopichincha.credito.automotriz.service.dto.SaleExecutiveResponse;
import com.bancopichincha.credito.automotriz.service.mapper.SaleExecutiveMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleExecutiveServiceImpl implements SaleExecutiveService {
  private final SaleExecutiveRepository saleExecutiveRepository;
  private final SaleExecutiveMapper saleExecutiveMapper;

  @Override
  public Optional<SaleExecutive> getById(Long id) {
    return saleExecutiveRepository.findById(id);
  }

  @Override
  public List<SaleExecutiveResponse> findAll() {
    return saleExecutiveMapper.mapToResponse(saleExecutiveRepository.findAll());
  }

  @Override
  public List<SaleExecutiveResponse> findAllBy(Long carYardId) {
    return saleExecutiveMapper.mapToResponse(saleExecutiveRepository.findAllByCarYardId(carYardId));
  }

  @Override
  public void saveSaleExecutives(List<SaleExecutiveDto> saleExecutiveDtos) {
    List<SaleExecutive> saleExecutives = saleExecutiveMapper.mapToEntity(saleExecutiveDtos);
    List<SaleExecutive> saleExecutivesToSave =
        saleExecutives.stream()
            .filter(saleExecutive -> !alreadyExists(saleExecutive))
            .collect(Collectors.toList());

    saleExecutiveRepository.saveAll(saleExecutivesToSave);
  }

  private boolean alreadyExists(SaleExecutive saleExecutive) {
    return saleExecutiveRepository.existsByIdentification(saleExecutive.getIdentification());
  }
}
