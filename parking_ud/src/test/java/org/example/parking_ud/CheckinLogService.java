package org.example.parking_ud;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.Optional;

import org.example.parking_ud.dao.Bicycle;
import org.example.parking_ud.dao.CheckinLog;
import org.example.parking_ud.dao.Parking;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.repositories.CheckinLogRepository;
import org.example.parking_ud.repositories.ParkingRepository;
import org.example.parking_ud.services.CheckinLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CheckinLogServiceTest {

    @Mock private CheckinLogRepository checkinLogRepository;
    @Mock private ParkingRepository parkingRepository;

    @InjectMocks private CheckinLogService checkinLogService;

    // Caso: Check-in exitoso
    @Test
    void checkIn_Success_ReturnsOk() {
        when(checkinLogRepository.findTopByBikeIdOrderByTimestampDesc(anyInt()))
                .thenReturn(Optional.empty());

        Parking parking = new Parking();
        parking.setAvCapacity((short) 10);
        when(parkingRepository.findById(anyShort())).thenReturn(Optional.of(parking));

        ResponseEntity<String> response = checkinLogService.checkIn(1, 1, (short) 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Caso: Bicicleta ya registrada
    @Test
    void checkIn_AlreadyCheckedIn_ReturnsBadRequest() {
        CheckinLog lastLog = new CheckinLog();
        lastLog.setEventType("check in");

        when(checkinLogRepository.findTopByBikeIdOrderByTimestampDesc(anyInt()))
                .thenReturn(Optional.of(lastLog));

        ResponseEntity<String> response = checkinLogService.checkIn(1, 1, (short) 1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Caso: Parqueadero lleno
    @Test
    void checkIn_ParkingFull_ReturnsConflict() {
        when(checkinLogRepository.findTopByBikeIdOrderByTimestampDesc(anyInt()))
                .thenReturn(Optional.empty());

        Parking parking = new Parking();
        parking.setAvCapacity((short) 0);
        when(parkingRepository.findById(anyShort())).thenReturn(Optional.of(parking));

        ResponseEntity<String> response = checkinLogService.checkIn(1, 1, (short) 1);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    // Caso: Check-out exitoso
    @Test
    void checkOut_Success_ReturnsOk() {
        CheckinLog lastLog = new CheckinLog();
        lastLog.setEventType("check in");
        Parking parking = new Parking();
        parking.setAvCapacity((short) 5);
        lastLog.setParking(parking);

        when(checkinLogRepository.findTopByBikeIdOrderByTimestampDesc(anyInt()))
                .thenReturn(Optional.of(lastLog));

        ResponseEntity<String> response = checkinLogService.checkOut(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Caso: Bicicleta no ingresada
    @Test
    void checkOut_NotCheckedIn_ReturnsBadRequest() {
        when(checkinLogRepository.findTopByBikeIdOrderByTimestampDesc(anyInt()))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = checkinLogService.checkOut(1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Caso: GetLastLog debería hacer check-in
    @Test
    void getLastLog_ShouldCheckIn_ReturnsCheckin() {
        when(checkinLogRepository.findTopByUserIdOrderByTimestampDesc(anyInt()))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = checkinLogService.getLastLog(1);
        assertEquals("checkin", response.getBody());
    }

    // Caso: GetLastLog debería hacer check-out
    @Test
    void getLastLog_ShouldCheckOut_ReturnsCheckout() {
        CheckinLog log = new CheckinLog();
        log.setEventType("check in");
        when(checkinLogRepository.findTopByUserIdOrderByTimestampDesc(anyInt()))
                .thenReturn(Optional.of(log));

        ResponseEntity<String> response = checkinLogService.getLastLog(1);
        assertEquals("checkout", response.getBody());
    }
}