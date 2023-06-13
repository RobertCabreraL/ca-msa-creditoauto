package com.bancopichincha.credito.automotriz.service;

import com.bancopichincha.credito.automotriz.domain.CarYard;
import com.bancopichincha.credito.automotriz.service.dto.CarYardDto;
import com.bancopichincha.credito.automotriz.service.dto.CarYardRequest;
import com.bancopichincha.credito.automotriz.service.dto.CarYardResponse;
import java.util.List;
import java.util.Optional;

public interface CarYardService {

  CarYardResponse create(CarYardRequest carYardRequest);

  CarYardResponse update(Long id, CarYardRequest carYardRequest);

  void delete(Long id);

  CarYardResponse findById(Long id);
  List<CarYardResponse> findAll();

  Optional<CarYard> getById(Long carYardId);

  CarYard findCarYardById(Long carYardId);

  void saveCarYards(List<CarYardDto> carYards);
}
