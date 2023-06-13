package com.bancopichincha.credito.automotriz.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bancopichincha.credito.automotriz.domain.SaleExecutive;
import com.bancopichincha.credito.automotriz.repository.SaleExecutiveRepository;
import com.bancopichincha.credito.automotriz.service.dto.SaleExecutiveDto;
import com.bancopichincha.credito.automotriz.service.dto.SaleExecutiveResponse;
import com.bancopichincha.credito.automotriz.service.mapper.SaleExecutiveMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SaleExecutiveServiceImplTest {
  @Mock private SaleExecutiveRepository saleExecutiveRepository;

  @Mock private SaleExecutiveMapper saleExecutiveMapper;
  @InjectMocks private SaleExecutiveServiceImpl saleExecutiveService;

  @Test
  public void shouldReturnSaleExecutiveWhenFoundOnGetById() {
    SaleExecutive saleExecutive = createDummySaleExecutive();

    when(saleExecutiveRepository.findById(anyLong())).thenReturn(Optional.of(saleExecutive));

    Optional<SaleExecutive> result = saleExecutiveService.getById(anyLong());

    assertTrue(result.isPresent());
    assertEquals(saleExecutive, result.get());
  }

  @Test
  public void shouldReturnEmptyOptionalWhenNotFoundOnGetById() {
    when(saleExecutiveRepository.findById(anyLong())).thenReturn(Optional.empty());

    Optional<SaleExecutive> result = saleExecutiveService.getById(anyLong());

    assertTrue(result.isEmpty());
  }

  @Test
  public void shouldReturnListOfSaleExecutiveResponsesOnFindAll() {
    List<SaleExecutive> saleExecutives = Collections.singletonList(createDummySaleExecutive());
    List<SaleExecutiveResponse> expectedResponses =
        Collections.singletonList(createDummySaleExecutiveResponse());

    when(saleExecutiveRepository.findAll()).thenReturn(saleExecutives);
    when(saleExecutiveMapper.mapToResponse(saleExecutives)).thenReturn(expectedResponses);

    List<SaleExecutiveResponse> actualResponses = saleExecutiveService.findAll();

    assertEquals(expectedResponses, actualResponses);
  }

  @Test
  public void shouldReturnListOfSaleExecutiveResponsesOnFindAllBy() {
    List<SaleExecutive> saleExecutives = Collections.singletonList(createDummySaleExecutive());
    List<SaleExecutiveResponse> expectedResponses =
        Collections.singletonList(createDummySaleExecutiveResponse());

    when(saleExecutiveRepository.findAllByCarYardId(anyLong())).thenReturn(saleExecutives);
    when(saleExecutiveMapper.mapToResponse(anyList())).thenReturn(expectedResponses);

    List<SaleExecutiveResponse> actualResponses = saleExecutiveService.findAllBy(anyLong());

    assertEquals(expectedResponses, actualResponses);
  }

  @Test
  public void shouldSaveSaleExecutivesAndFilterDuplicatesOnSaveSaleExecutives() {

    List<SaleExecutiveDto> saleExecutiveDtos =
        Collections.singletonList(createDummySaleExecutiveDto());
    List<SaleExecutive> saleExecutives = Collections.singletonList(createDummySaleExecutive());
    List<SaleExecutive> saleExecutivesToSave =
        Collections.singletonList(createDummySaleExecutive());

    when(saleExecutiveMapper.mapToEntity(anyList())).thenReturn(saleExecutives);
    when(saleExecutiveRepository.existsByIdentification(any())).thenReturn(false);
    when(saleExecutiveRepository.saveAll(anyList())).thenReturn(saleExecutivesToSave);

    saleExecutiveService.saveSaleExecutives(saleExecutiveDtos);

    verify(saleExecutiveRepository).saveAll(saleExecutivesToSave);
  }

  private SaleExecutive createDummySaleExecutive() {
    return SaleExecutive.builder().build();
  }

  private SaleExecutiveDto createDummySaleExecutiveDto() {
    return SaleExecutiveDto.builder().build();
  }

  private SaleExecutiveResponse createDummySaleExecutiveResponse() {
    return SaleExecutiveResponse.builder().build();
  }
}
