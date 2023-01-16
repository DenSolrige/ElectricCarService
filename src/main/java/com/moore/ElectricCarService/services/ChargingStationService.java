package com.moore.ElectricCarService.services;

import com.moore.ElectricCarService.dtos.ChargingStationUpdateInfo;
import com.moore.ElectricCarService.entities.ChargingStation;
import com.moore.ElectricCarService.repos.ChargingStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChargingStationService {
    @Autowired
    ChargingStationRepository chargingStationRepository;

    public ChargingStation registerChargingStation(ChargingStation chargingStation){
        return chargingStationRepository.save(chargingStation);
    }

    public void unregisterChargingStation(String identifier){
        chargingStationRepository.deleteByIdentifier(identifier);
    }

    public List<ChargingStation> getAllChargingStations(){
        return chargingStationRepository.findAll();
    }

    public List<ChargingStation> getChargingStationsByAddress(String address){
        return chargingStationRepository.findByAddress(address);
    }

    public Optional<ChargingStation> getChargingStationByIdentifier(String identifier){
        return chargingStationRepository.findByIdentifier(identifier);
    }

    public Optional<ChargingStation> updateChargingStation(String identifier, ChargingStationUpdateInfo chargingStationUpdateInfo){
        Optional<ChargingStation> optionalChargingStation = chargingStationRepository.findByIdentifier(identifier);
        if(optionalChargingStation.isPresent()){
            ChargingStation chargingStation = optionalChargingStation.get();
            chargingStation.setAddress(chargingStationUpdateInfo.getAddress());
            chargingStation.setNumber(chargingStationUpdateInfo.getNumber());
            return Optional.of(chargingStationRepository.save(chargingStation));
        }else{
            return Optional.empty();
        }
    }
}
