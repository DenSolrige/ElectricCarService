package com.moore.ElectricCarService.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChargingStationUpdateInfo {
    private String address;
    private int number;
}
