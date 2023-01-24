package com.moore.ElectricCarService.entities;

import com.moore.ElectricCarService.dtos.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    private int transactionId;

    private TransactionStatus status = TransactionStatus.CHARGING;
    private double companyFlatCharge;
    private double companyRate;
    private boolean isProcessed = false;
    private OffsetDateTime startTime;
    private OffsetDateTime stopTime;

    @OneToMany(mappedBy = "transaction",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Charge> chargeList;

    @ManyToOne
    @JoinColumn(name = "chargingStationId")
    private ChargingStation chargingStation;

    public Transaction(int transactionId,double companyFlatCharge, double companyRate, ChargingStation chargingStation) {
        this.transactionId = transactionId;
        this.companyFlatCharge = companyFlatCharge;
        this.companyRate = companyRate;
        this.chargingStation = chargingStation;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", status=" + status +
                ", companyFlatCharge=" + companyFlatCharge +
                ", companyRate=" + companyRate +
                ", isProcessed=" + isProcessed +
                '}';
    }
}
