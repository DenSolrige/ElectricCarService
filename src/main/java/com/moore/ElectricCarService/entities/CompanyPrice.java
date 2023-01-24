package com.moore.ElectricCarService.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_price")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class CompanyPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyPriceId;

    private double startFee;
    private double perKwhFee;
}
