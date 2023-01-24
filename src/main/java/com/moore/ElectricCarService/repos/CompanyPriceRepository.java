package com.moore.ElectricCarService.repos;

import com.moore.ElectricCarService.entities.CompanyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyPriceRepository extends JpaRepository<CompanyPrice, Integer> {
}
