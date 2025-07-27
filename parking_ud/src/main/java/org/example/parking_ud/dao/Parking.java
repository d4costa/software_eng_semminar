package org.example.parking_ud.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parking")
public class Parking {
    @Id
    @Column(name = "parking_id", nullable = false)
    private Short id;

    @Size(max = 50)
    @NotNull
    @Column(name = "parking_name", nullable = false, length = 50)
    private String parkingName;

    @Size(max = 50)
    @NotNull
    @Column(name = "parking_location", nullable = false, length = 50)
    private String parkingLocation;

}