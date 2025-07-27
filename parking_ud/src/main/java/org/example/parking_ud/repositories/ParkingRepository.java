package org.example.parking_ud.repositories;

import org.example.parking_ud.dao.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Short> {
}