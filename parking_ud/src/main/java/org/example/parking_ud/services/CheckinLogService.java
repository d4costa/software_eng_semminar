package org.example.parking_ud.services;

import org.example.parking_ud.dao.Bicycle;
import org.example.parking_ud.dao.CheckinLog;
import org.example.parking_ud.dao.Parking;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.CheckLogDTO;
import org.example.parking_ud.repositories.CheckinLogRepository;
import org.example.parking_ud.repositories.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CheckinLogService {
    @Autowired
    public  CheckinLogRepository checkinLogRepository;
    @Autowired
    public ParkingRepository parkingRepository;

    public  List<CheckLogDTO> getLogs(int userId) {
        return  checkinLogRepository.findLogsByUserId(userId);

    }

    public ResponseEntity<String> checkIn(Integer userId,int bikeId,short parkingId) {
        try {
            Optional<CheckinLog> lastLog = checkinLogRepository.findTopByBikeIdOrderByTimestampDesc(bikeId);
            //Optional<Bicycle> userBicycle = checkinLogRepository.findTopByUserIdOrderByTimestampDesc();
            if (lastLog.isPresent() && lastLog.get().getEventType().equalsIgnoreCase("check in")) {
                return ResponseEntity.badRequest().body("Bike already checked in.");
            }



            Optional<Parking> parkingOpt = parkingRepository.findById(parkingId);
            if (!parkingOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking not found");
            }

            Parking parking = parkingOpt.get();
            if (parking.getAvCapacity() <= 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Parking is full");
            }



            Usuario usr = new Usuario();
            usr.setId(userId);
            Bicycle ObjBicycle = new Bicycle();
            ObjBicycle.id = bikeId;
            Parking objParking = new Parking();
            objParking.setId((short) 1);
            CheckinLog log = new CheckinLog();
            log.setId((int)(checkinLogRepository.count() + 1));
            log.setBike(ObjBicycle);
            log.setUser(usr);
            log.setParking(objParking);
            log.setEventType("check in");
            log.setTimestamp(Instant.now());

            checkinLogRepository.save(log);
            // Update parking capacity
            parking.setAvCapacity((short)(parking.getAvCapacity() - 1));
            parkingRepository.save(parking);

            return ResponseEntity.ok("Bike checked in successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<String> checkOut(int bikeId) {
        try {
            // Optional: check if the bike is already checked in and not checked out
            Optional<CheckinLog> lastLog = checkinLogRepository.findTopByBikeIdOrderByTimestampDesc(bikeId);
            if (!lastLog.isPresent() || lastLog.get().getEventType().equalsIgnoreCase("check out") ) {
                return ResponseEntity.badRequest().body("La bicicleta no se encuentra ingresada en el parqueadero.");
            }



            CheckinLog log = new CheckinLog();
            Parking parking = lastLog.get().getParking();
            log.setId((int)(checkinLogRepository.count() + 1));
            log.setBike(lastLog.get().getBike());
            log.setUser(lastLog.get().getUser());
            log.setParking( lastLog.get().getParking());
            log.setEventType("check out");
            log.setTimestamp(Instant.now());

            checkinLogRepository.save(log);
            parking.setAvCapacity((short)(parking.getAvCapacity() + 1));
            parkingRepository.save(parking);

            return ResponseEntity.ok("Bike checked out successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }    }


    public ResponseEntity<String> getLastLog(int userId) {

        Optional<CheckinLog> lastLog = checkinLogRepository.findTopByUserIdOrderByTimestampDesc(userId);

        if (!lastLog.isPresent() || lastLog.get().getEventType().equalsIgnoreCase("check out")) {
            return ResponseEntity.ok("checkin"); // User should check in
        } else {
            return ResponseEntity.ok("checkout"); // User should check out
        }

    }
}
