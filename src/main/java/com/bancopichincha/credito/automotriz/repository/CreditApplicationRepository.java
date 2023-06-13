package com.bancopichincha.credito.automotriz.repository;

import com.bancopichincha.credito.automotriz.domain.CreditApplication;
import com.bancopichincha.credito.automotriz.domain.enums.CreditApplicationStatus;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {

  @Query(
      "SELECT CASE WHEN COUNT(capp) > 0 THEN TRUE ELSE FALSE END "
          + " FROM CreditApplication capp"
          + " WHERE capp.customer.identification = :identification"
          + " AND capp.elaborationDate = :elaboDate"
          + " AND capp.status = :status")
  boolean existsBy(String identification, Date elaboDate, CreditApplicationStatus status);

  @Query(
      "SELECT CASE WHEN COUNT(capp) > 0 THEN TRUE ELSE FALSE END "
          + " FROM CreditApplication capp"
          + " WHERE capp.vehicle.plate = :plate")
  boolean existsVehicleDataByPlate(String plate);

  @Query(
      "SELECT CASE WHEN COUNT(capp) > 0 THEN TRUE ELSE FALSE END "
          + " FROM CreditApplication capp"
          + " WHERE capp.carYard.id = :carYardId")
  boolean existsCarYardInformation(Long carYardId);
}
