package com.moore.ElectricCarService.entities;

import com.moore.ElectricCarService.dtos.ChargingStationInfo;
import com.moore.ElectricCarService.dtos.ChargingStationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(
        name = "charging_station",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"address","number"})}
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ChargingStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "identifier", unique = true)
    private String identifier;
    @Column(name = "address")
    private String address;
    @Column(name = "number")
    private int number;
    @Column(name = "status")
    private ChargingStationStatus status = ChargingStationStatus.AVAILABLE;

    public ChargingStation(ChargingStationInfo chargingStationInfo){
        this.identifier = chargingStationInfo.getIdentifier();
        this.address = chargingStationInfo.getAddress();
        this.number = chargingStationInfo.getNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChargingStation that = (ChargingStation) o;
        return id != 0 && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
