package com.moore.ElectricCarService.entities;

import com.moore.ElectricCarService.dtos.ChargingStationInfo;
import com.moore.ElectricCarService.dtos.ChargingStationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "charging_station",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"address","number"})}
)
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ChargingStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chargingStationId;

    @Column(unique = true)
    private String identifier;
    private String address;
    private int number;
    private ChargingStationStatus status = ChargingStationStatus.AVAILABLE;

    @OneToMany(mappedBy = "chargingStation",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Transaction> transactionList;

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
        return chargingStationId != 0 && Objects.equals(chargingStationId, that.chargingStationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ChargingStation{" +
                "chargingStationId=" + chargingStationId +
                ", identifier='" + identifier + '\'' +
                ", address='" + address + '\'' +
                ", number=" + number +
                ", status=" + status +
                '}';
    }
}
