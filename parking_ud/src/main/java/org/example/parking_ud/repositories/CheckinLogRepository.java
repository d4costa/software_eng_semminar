package org.example.parking_ud.repositories;

import org.example.parking_ud.dao.CheckinLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinLogRepository extends JpaRepository<CheckinLog, Integer> {
}