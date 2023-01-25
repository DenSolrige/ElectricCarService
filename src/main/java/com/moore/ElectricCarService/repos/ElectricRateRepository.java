package com.moore.ElectricCarService.repos;

import com.moore.ElectricCarService.entities.ElectricRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface ElectricRateRepository extends JpaRepository<ElectricRate,Integer> {
    ElectricRate findTopByOrderByElectricRateIdDesc();
    Optional<ElectricRate> findTopByTimestampLessThanOrderByTimestampDesc(OffsetDateTime timestamp);

}
