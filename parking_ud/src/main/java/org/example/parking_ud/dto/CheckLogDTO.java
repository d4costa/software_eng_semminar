package org.example.parking_ud.dto;

import java.time.Instant;

public class CheckLogDTO {
    public String eventType;
    public Instant timestamp;
    public String chasisCode;
    public String parkingName;

    public CheckLogDTO(String eventType, Instant timestamp, String chasisCode, String parkingName) {
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.chasisCode = chasisCode;
        this.parkingName = parkingName;
    }
}
