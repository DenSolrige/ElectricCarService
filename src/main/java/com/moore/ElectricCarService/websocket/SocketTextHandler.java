package com.moore.ElectricCarService.websocket;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Optional;

import com.google.gson.Gson;
import com.moore.ElectricCarService.controllers.ChargingStationController;
import com.moore.ElectricCarService.dtos.ChargingStationStatus;
import com.moore.ElectricCarService.dtos.PayloadInfo;
import com.moore.ElectricCarService.dtos.TransactionStatus;
import com.moore.ElectricCarService.entities.Charge;
import com.moore.ElectricCarService.entities.ChargingStation;
import com.moore.ElectricCarService.entities.CompanyPrice;
import com.moore.ElectricCarService.entities.Transaction;
import com.moore.ElectricCarService.repos.ChargeRepository;
import com.moore.ElectricCarService.repos.ChargingStationRepository;
import com.moore.ElectricCarService.repos.CompanyPriceRepository;
import com.moore.ElectricCarService.repos.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    @Autowired
    ChargingStationRepository chargingStationRepository;

    @Autowired
    ChargeRepository chargeRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CompanyPriceRepository companyPriceRepository;

    private static final Logger log = LoggerFactory.getLogger(ChargingStationController.class);

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {

        String[] payload = message.getPayload().split(" ",2);
        String identifier = (String)session.getAttributes().get("identifier");
        Optional<ChargingStation> optionalChargingStation = chargingStationRepository.findByIdentifier(identifier);

        if(optionalChargingStation.isPresent()){
            Gson gson = new Gson();
            PayloadInfo payloadInfo = gson.fromJson(payload[1], PayloadInfo.class);
//            System.out.println(payloadInfo);
            Optional<Transaction> optionalTransaction = transactionRepository.findById(payloadInfo.getTransactionId());

            ChargingStation cs = optionalChargingStation.get();
            log.atInfo().log("Websocket received for "+identifier+": "+payload[0]+" "+payload[1]);

                if(payload[0].equals("start")){
                    if(!cs.getStatus().equals(ChargingStationStatus.AVAILABLE)) {
                        session.close();
                    }

                    Optional<CompanyPrice> optionalCompanyPrice = companyPriceRepository.findById(1);
                    Transaction transaction;
                    if(optionalCompanyPrice.isPresent()){
                        CompanyPrice companyPrice = optionalCompanyPrice.get();
                        transaction = new Transaction(payloadInfo.getTransactionId(), companyPrice.getStartFee(), companyPrice.getPerKwhFee(), cs);
                    }else{
                        transaction = new Transaction(payloadInfo.getTransactionId(), 0, 0, cs);
                    }
                    transaction.setStartTime(OffsetDateTime.parse(payloadInfo.getTimestamp()));
                    transactionRepository.save(transaction);

                    Charge charge = new Charge(OffsetDateTime.now(), payloadInfo.getMeterStart(), transaction);
                    chargeRepository.save(charge);

                    cs.setStatus(ChargingStationStatus.CHARGING);
                    chargingStationRepository.save(cs);
                    session.sendMessage(new TextMessage("start ack"));
                }

                if(payload[0].equals("charging") && optionalTransaction.isPresent()){
                    Charge charge = new Charge(OffsetDateTime.parse(payloadInfo.getTimestamp()), payloadInfo.getMeterValue(), optionalTransaction.get());
                    chargeRepository.save(charge);
                }

                if(payload[0].equals("status") && optionalTransaction.isPresent()){
                    String status = payloadInfo.getStatus();
                    Transaction transaction = optionalTransaction.get();
                    if(status.equals("Suspended")){
                        cs.setStatus(ChargingStationStatus.SUSPENDED);
                        transaction.setStatus(TransactionStatus.SUSPENDED);
                    }
                    if(status.equals("Charging")){
                        cs.setStatus(ChargingStationStatus.CHARGING);
                        transaction.setStatus(TransactionStatus.CHARGING);
                    }
                    chargingStationRepository.save(cs);
                    transactionRepository.save(transaction);
                }

                if(payload[0].equals("stop") && optionalTransaction.isPresent()){
                    cs.setStatus(ChargingStationStatus.AVAILABLE);
                    chargingStationRepository.save(cs);

                    Transaction transaction = optionalTransaction.get();
                    transaction.setStatus(TransactionStatus.FINISHED);
                    transaction.setStopTime(OffsetDateTime.parse(payloadInfo.getTimestamp()));
                    transactionRepository.save(transaction);

                    Charge charge = new Charge(OffsetDateTime.now(), payloadInfo.getMeterStop(), optionalTransaction.get());
                    chargeRepository.save(charge);

                    session.sendMessage(new TextMessage("ack stop"));
                }
        }else{
            log.atInfo().log("Websocket received for unknown charging station "+identifier+": "+payload[0]+" "+payload[1]+" closing session");
            session.close();
        }
    }
}
