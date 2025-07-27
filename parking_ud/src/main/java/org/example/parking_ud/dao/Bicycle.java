package org.example.parking_ud.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bicycle")
public class Bicycle {
    @Id
    @Column(name = "bike_id", nullable = false)
    public Short id;

    @Size(max = 50)
    @NotNull
    @Column(name = "color", nullable = false, length = 50)
    public String color;

    @Size(max = 50)
    @NotNull
    @Column(name = "description", nullable = false, length = 50)
    public String description;

    @Size(max = 50)
    @NotNull
    @Column(name = "brand", nullable = false, length = 50)
    public String brand;

    @Size(max = 50)
    @NotNull
    @Column(name = "chasis_code", nullable = false, length = 50)
    public String chasisCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Usuario user;

}