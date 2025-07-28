package org.example.parking_ud.repositories;

import org.example.parking_ud.dao.CheckinLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckinLogRepository extends JpaRepository<CheckinLog, Integer> {
    Optional<CheckinLog> findTopByBikeIdOrderByTimestampDesc(short bikeId);
    Optional<CheckinLog> findTopByUserIdOrderByTimestampDesc(int userId);
}