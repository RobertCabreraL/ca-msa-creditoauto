package com.bancopichincha.credito.automotriz.service.mapper;

import com.bancopichincha.credito.automotriz.domain.CarYard;
import com.bancopichincha.credito.automotriz.domain.Customer;
import com.bancopichincha.credito.automotriz.domain.CustomerAssignment;
import com.bancopichincha.credito.automotriz.service.dto.CustomerAssignmentRequest;
import com.bancopichincha.credito.automotriz.service.dto.CustomerAssignmentResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerAssignmentMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "customer", source = "customer")
  @Mapping(target = "carYard", source = "carYard")
  @Mapping(
      target = "assignmentDate",
      source = "customerAssignment.assignmentDate",
      dateFormat = "yyyy-MM-dd")
  CustomerAssignment mapToEntity(
      CustomerAssignmentRequest customerAssignment, CarYard carYard, Customer customer);

  @Mapping(
      target = "assignmentDate",
      source = "customerAssignment.assignmentDate",
      dateFormat = "yyyy-MM-dd")
  CustomerAssignmentResponse mapToResponse(CustomerAssignment customerAssignment);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(
      target = "assignmentDate",
      source = "customerAssignmentRequest.assignmentDate",
      dateFormat = "yyyy-MM-dd")
  void update(
      CustomerAssignmentRequest customerAssignmentRequest,
      CarYard carYard,
      Customer customer,
      @MappingTarget CustomerAssignment customerAssignment);


  List<CustomerAssignmentResponse> mapToResponse(List<CustomerAssignment> customerAssignments);
}
