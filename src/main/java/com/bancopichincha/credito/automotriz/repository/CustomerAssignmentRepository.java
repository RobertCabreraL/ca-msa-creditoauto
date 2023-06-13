package com.bancopichincha.credito.automotriz.repository;

import com.bancopichincha.credito.automotriz.domain.CustomerAssignment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerAssignmentRepository extends JpaRepository<CustomerAssignment, Long> {

  @Query(
      "SELECT ca FROM CustomerAssignment ca "
          + " WHERE 1=1 "
          + " AND (:customerId is null or ca.customer.id = :customerId) "
          + " AND (:carYardId is null or ca.carYard.id = :carYardId) ")
  List<CustomerAssignment> findByCustomerIdAndCarYardId(
      @Param("customerId") Long customerId, @Param("carYardId") Long carYardId);

  @Query(
      "SELECT CASE WHEN COUNT(ca) > 0 THEN TRUE ELSE FALSE END "
          + " FROM CustomerAssignment ca"
          + " WHERE ca.carYard.id = :carYardId")
  boolean existsCarYardInformation(Long carYardId);
}
