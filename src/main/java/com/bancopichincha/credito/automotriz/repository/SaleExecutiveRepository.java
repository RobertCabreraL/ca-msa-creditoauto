package com.bancopichincha.credito.automotriz.repository;

import com.bancopichincha.credito.automotriz.domain.SaleExecutive;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleExecutiveRepository extends JpaRepository<SaleExecutive, Long> {

  @Query(
      "SELECT CASE WHEN COUNT(se) > 0 THEN TRUE ELSE FALSE END "
          + " FROM SaleExecutive se"
          + " WHERE se.carYard.id = :carYardId")
  boolean existsCarYardInformation(Long carYardId);

  @Query("SELECT se FROM SaleExecutive se WHERE se.carYard.id = :carYardId")
  List<SaleExecutive> findAllByCarYardId(Long carYardId);

  boolean existsByIdentification(String identification);
}
