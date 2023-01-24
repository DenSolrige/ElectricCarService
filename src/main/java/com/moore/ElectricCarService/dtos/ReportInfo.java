package com.moore.ElectricCarService.dtos;

import lombok.Data;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

@Data
public class ReportInfo {
    private List<TransactionInfo> transactions;
    private String totalConsumption;

    public ReportInfo(List<TransactionInfo> transactions) {
        this.transactions = transactions;
        double messyTotal = transactions.stream().mapToDouble(t->Double.parseDouble(t.getChargeTotal())).sum();
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.HALF_UP);
        this.totalConsumption = df.format(messyTotal);
    }
}
