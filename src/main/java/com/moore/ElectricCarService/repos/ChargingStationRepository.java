package com.moore.ElectricCarService.repos;

import com.moore.ElectricCarService.entities.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStation, Integer> {
    void deleteByIdentifier(String identifier);

    List<ChargingStation> findByAddress(String address);

    ChargingStation findByIdentifier(String identifier);
}