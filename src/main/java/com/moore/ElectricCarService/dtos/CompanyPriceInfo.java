package com.moore.ElectricCarService.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyPriceInfo {
    private double startFee;
    private double perKwhFee;
}
