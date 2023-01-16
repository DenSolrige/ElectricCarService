package com.moore.ElectricCarService.controllers;

import com.moore.ElectricCarService.dtos.ChargingStationInfo;
import com.moore.ElectricCarService.entities.ChargingStation;
import com.moore.ElectricCarService.services.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chargingstation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChargingStationController {
    @Autowired
    ChargingStationService chargingStationService;

    @PostMapping
    public ResponseEntity<ChargingStation> register(@RequestBody ChargingStationInfo chargingStationInfo){
        return ResponseEntity.ok().body(chargingStationService.registerChargingStation(new ChargingStation(chargingStationInfo)));
    }

    @DeleteMapping("/{identifier}")
    @Transactional
    public HttpStatus unregister(@PathVariable String identifier){
        chargingStationService.unregisterChargingStation(identifier);
        return HttpStatus.OK;
    }

    @GetMapping
    public ResponseEntity<List<ChargingStation>> get(@RequestParam(required = false) String address){
        if(address == null){
            return ResponseEntity.ok().body(chargingStationService.getAllChargingStations());
        }else{
            return ResponseEntity.ok().body(chargingStationService.getChargingStationsByAddress(address));
        }
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<ChargingStation> getByIdentifier(@PathVariable String identifier){
        return ResponseEntity.ok().body(chargingStationService.getChargingStationByIdentifier(identifier));
    }
}
