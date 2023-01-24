package com.moore.ElectricCarService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "charge")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chargeId;

    private OffsetDateTime timestamp;
    private double chargeAmount;

    @ManyToOne
    @JoinColumn(name = "transactionId")
    private Transaction transaction;

    public Charge(OffsetDateTime timestamp, double chargeAmount, Transaction transaction) {
        this.timestamp = timestamp;
        this.chargeAmount = chargeAmount;
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "Charge{" +
                "chargeId=" + chargeId +
                ", timestamp=" + timestamp +
                ", chargeAmount=" + chargeAmount +
                '}';
    }
}
