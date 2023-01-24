package com.moore.ElectricCarService.controllers;

import com.moore.ElectricCarService.dtos.CompanyPriceInfo;
import com.moore.ElectricCarService.entities.CompanyPrice;
import com.moore.ElectricCarService.repos.CompanyPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyPriceController {
    @Autowired
    CompanyPriceRepository companyPriceRepository;

    @PutMapping
    public ResponseEntity<CompanyPrice> alter(@RequestBody CompanyPriceInfo companyPriceInfo){
        CompanyPrice companyPrice = new CompanyPrice(1, companyPriceInfo.getStartFee(), companyPriceInfo.getPerKwhFee());
        this.companyPriceRepository.save(companyPrice);
        return ResponseEntity.ok().body(companyPrice);
    }
}
