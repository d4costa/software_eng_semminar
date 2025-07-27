package org.example.parking_ud.repositories;

import org.example.parking_ud.dao.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BicycleRepository extends JpaRepository<Bicycle, Short> {

    // If each user has only one bike:
    Optional<Bicycle> findByUser_id(Integer userId);
}