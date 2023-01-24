package com.moore.ElectricCarService.dtos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class PayloadInfo {
    private int transactionId;
    private String timestamp;
    private int meterStart;
    private int meterValue;
    private int meterStop;
    private String status;

    @Override
    public String toString() {
        return "PayloadInfo{" +
                "transactionId=" + transactionId +
                ", timestamp=" + timestamp +
                ", meterStart=" + meterStart +
                ", meterValue=" + meterValue +
                ", meterStop=" + meterStop +
                ", status='" + status + '\'' +
                '}';
    }
}
