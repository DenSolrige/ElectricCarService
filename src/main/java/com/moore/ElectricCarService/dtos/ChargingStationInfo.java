package com.moore.ElectricCarService.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChargingStationInfo {
    private String identifier;
    private String address;
    private int number;
}
