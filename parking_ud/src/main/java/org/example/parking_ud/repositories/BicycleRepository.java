package org.example.parking_ud.repositories;

import org.example.parking_ud.dao.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BicycleRepository extends JpaRepository<Bicycle, Short> {
}