package com.moore.ElectricCarService.repos;

import com.moore.ElectricCarService.entities.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeRepository extends JpaRepository<Charge,Integer> {
}
