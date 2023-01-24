package com.moore.ElectricCarService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "electric_rate")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ElectricRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int electricRateId;
    private OffsetDateTime timestamp;
    private double value;
}
