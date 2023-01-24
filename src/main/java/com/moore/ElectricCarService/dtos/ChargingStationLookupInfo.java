package com.moore.ElectricCarService.dtos;

import com.moore.ElectricCarService.entities.ChargingStation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChargingStationLookupInfo {
    private String identifier;
    private String address;
    private int number;
    private ChargingStationStatus status;

    public ChargingStationLookupInfo(ChargingStation chargingStation) {
        this.identifier = chargingStation.getIdentifier();
        this.address = chargingStation.getAddress();
        this.number = chargingStation.getNumber();
        this.status = chargingStation.getStatus();
    }
}
