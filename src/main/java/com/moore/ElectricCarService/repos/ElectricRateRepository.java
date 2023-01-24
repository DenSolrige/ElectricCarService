package com.moore.ElectricCarService.repos;

import com.moore.ElectricCarService.entities.ElectricRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricRateRepository extends JpaRepository<ElectricRate,Integer> {
}
