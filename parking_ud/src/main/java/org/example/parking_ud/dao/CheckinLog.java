package org.example.parking_ud.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "checkin_logs")
public class CheckinLog {
    @Id
    @Column(name = "log_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bike_id", nullable = false)
    private Bicycle bike;

    @Size(max = 10)
    @NotNull
    @Column(name = "event_type", nullable = false, length = 10)
    private String eventType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id")
    private Parking parking;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "\"timestamp\"", nullable = false)
    private Instant timestamp;
}

