package com.bancopichincha.credito.automotriz.repository;

import com.bancopichincha.credito.automotriz.domain.Vehicle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

  boolean existsByPlate(String plate);

  Optional<Vehicle> getVehicleByPlate(String plate);

  @Query(
      "SELECT v FROM Vehicle v "
          + " WHERE 1=1 "
          + " AND (:brandId is null or v.brand.id = :brandId) "
          + " AND (:type is null or v.type = :type) "
          + " AND (:year is null or v.year = :year)")
  List<Vehicle> findVehiclesByBrandAndTypeAndYear(
      @Param("brandId") Long brandId, @Param("type") String type, @Param("year") Integer year);
}
