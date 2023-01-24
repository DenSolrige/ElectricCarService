package com.moore.ElectricCarService.dtos;

import com.moore.ElectricCarService.entities.Charge;
import com.moore.ElectricCarService.entities.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.util.Comparator;

@Data
@NoArgsConstructor
public class TransactionInfo {
    private TransactionStatus status;
    private String chargeTotal;
    private OffsetDateTime startTime;
    private OffsetDateTime stopTime;

    public TransactionInfo(Transaction transaction) {
        this.status = transaction.getStatus();
        double initialCharge = transaction.getChargeList().stream().min(Comparator.comparing(Charge::getChargeId)).get().getChargeAmount();
        double finalCharge = transaction.getChargeList().stream().max(Comparator.comparing(Charge::getChargeId)).get().getChargeAmount();
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.HALF_UP);
        this.chargeTotal = df.format((finalCharge-initialCharge)/1000);
        this.startTime = transaction.getStartTime();
        this.stopTime = transaction.getStopTime();
    }
}
