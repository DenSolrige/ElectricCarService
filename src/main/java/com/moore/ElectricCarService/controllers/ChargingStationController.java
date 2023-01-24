package com.moore.ElectricCarService.controllers;

import com.moore.ElectricCarService.dtos.*;
import com.moore.ElectricCarService.entities.ChargingStation;
import com.moore.ElectricCarService.entities.Transaction;
import com.moore.ElectricCarService.services.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chargingstation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChargingStationController {
    @Autowired
    ChargingStationService chargingStationService;

    @PostMapping
    public ResponseEntity<ChargingStationLookupInfo> register(@RequestBody ChargingStationInfo chargingStationInfo){
        return ResponseEntity.ok().body(new ChargingStationLookupInfo(chargingStationService.registerChargingStation(new ChargingStation(chargingStationInfo))));
    }

    @DeleteMapping("/{identifier}")
    @Transactional
    public HttpStatus unregister(@PathVariable String identifier){
        chargingStationService.unregisterChargingStation(identifier);
        return HttpStatus.OK;
    }

    @GetMapping
    public ResponseEntity<List<ChargingStationLookupInfo>> get(@RequestParam(required = false) String address){
        if(address == null){
            return ResponseEntity.ok().body(chargingStationService.getAllChargingStations().stream().map(ChargingStationLookupInfo::new).collect(Collectors.toList()));
        }else{
            return ResponseEntity.ok().body(chargingStationService.getChargingStationsByAddress(address).stream().map(ChargingStationLookupInfo::new).collect(Collectors.toList()));
        }
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<ChargingStationLookupInfo> getByIdentifier(@PathVariable String identifier){
        Optional<ChargingStation> optionalChargingStation = chargingStationService.getChargingStationByIdentifier(identifier);
        return optionalChargingStation.map(
                chargingStation -> ResponseEntity.ok().body(new ChargingStationLookupInfo(chargingStation)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<ChargingStationLookupInfo> update(@RequestBody ChargingStationUpdateInfo chargingStationUpdateInfo, @PathVariable String identifier){
        Optional<ChargingStation> optionalChargingStation = chargingStationService.updateChargingStation(identifier,chargingStationUpdateInfo);
        return optionalChargingStation.map(
                chargingStation -> ResponseEntity.ok().body(new ChargingStationLookupInfo(chargingStation)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{identifier}/report")
    public ResponseEntity<ReportInfo> getReport(@PathVariable String identifier,@RequestParam(required = false) OffsetDateTime from,@RequestParam(required = false) OffsetDateTime to){
        Optional<ChargingStation> optionalChargingStation = chargingStationService.getChargingStationByIdentifier(identifier);
        if(optionalChargingStation.isPresent()){
            List<Transaction> transactions = optionalChargingStation.get().getTransactionList();
            if(from!=null){
                transactions = transactions.stream().filter(t->from.isBefore(t.getStopTime())).toList();
            }else{
                System.out.println(LocalDate.now(OffsetDateTime.now().getOffset()).atTime(OffsetTime.MIN));
                transactions = transactions.stream().filter(t->OffsetDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC).isBefore(t.getStopTime())).toList();
            }
            if(to!=null){
                transactions = transactions.stream().filter(t->to.isAfter(t.getStopTime())).toList();
            }else{
                transactions = transactions.stream().filter(t->OffsetDateTime.now().isAfter(t.getStopTime())).toList();
            }
            List<TransactionInfo> transactionInfoList = transactions.stream().map(TransactionInfo::new).toList();
            return ResponseEntity.ok().body(new ReportInfo(transactionInfoList));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
