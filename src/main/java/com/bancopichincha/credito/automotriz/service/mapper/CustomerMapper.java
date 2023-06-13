package com.bancopichincha.credito.automotriz.service.mapper;

import com.bancopichincha.credito.automotriz.domain.Customer;
import com.bancopichincha.credito.automotriz.service.dto.CustomerDto;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CustomerMapper {

  CustomerDto mapToDto(Customer customer);

  Customer mapToEntity(CustomerDto customerDto);

  List<CustomerDto> mapToDtoList(List<Customer> customers);

  List<Customer> mapToEntity(List<CustomerDto> customerDtos);
}
