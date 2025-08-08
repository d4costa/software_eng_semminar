package org.example.parking_ud.repositories;

import org.example.parking_ud.dao.CheckinLog;
import org.example.parking_ud.dto.CheckLogDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckinLogRepository extends JpaRepository<CheckinLog, Integer> {
    Optional<CheckinLog> findTopByBikeIdOrderByTimestampDesc(int bikeId);
    Optional<CheckinLog> findTopByUserIdOrderByTimestampDesc(int userId);

    @Query("SELECT new org.example.parking_ud.dto.CheckLogDTO(cl.eventType, cl.timestamp, b.chasisCode, p.parkingName) " +
            "FROM CheckinLog cl " +
            "JOIN cl.bike b " +
            "LEFT JOIN cl.parking p " +
            "WHERE cl.user.id = :userId"+
            " ORDER BY cl.timestamp DESC")
    List<CheckLogDTO> findLogsByUserId(@Param("userId") Integer userId);
}