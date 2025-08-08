package unitTests;

import org.example.parking_ud.ParkingUdApplication;
import org.example.parking_ud.dao.Bicycle;
import org.example.parking_ud.dao.CheckinLog;
import org.example.parking_ud.dao.Parking;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.CheckLogDTO;
import org.example.parking_ud.repositories.BicycleRepository;
import org.example.parking_ud.repositories.CheckinLogRepository;
import org.example.parking_ud.repositories.ParkingRepository;
import org.example.parking_ud.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ParkingUdApplication.class)
public class CheckinLogRepositoryTest {


        @Autowired
        private CheckinLogRepository checkinLogRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private BicycleRepository bicycleRepository;

        @Autowired
        private ParkingRepository parkingRepository;



        @Test
        void testFindLogsByUserId() {
            // Create and save user
            Usuario user = new Usuario();
            user.setId(11132);
            user.setNombre("Alice");
            user.setApellido("Smith");
            user.setEmail("alice@example.com");
            user.setPassword("hashed");
            user.setStudentId(BigDecimal.valueOf(123456));
            usuarioRepository.save(user);

            // Create and save bike
            Bicycle bike = new Bicycle();
            bike.id = 1;
            bike.brand = "Giant";
            bike.color = "Red";
            bike.description = "City Bike";
            bike.chasisCode = "CH123";
            bike.user = user;
            bicycleRepository.save(bike);

            // Create and save parking

            Parking parking = new Parking();
            parking.setId((short) 3);
            parking.setParkingName("Main Lot");
            parking.setParkingLocation("Center");
            parking.setAvCapacity((short) 10);
            parking.setCapacity((short) 100);
            parkingRepository.save(parking);

            // Create and save checkin log
            CheckinLog log = new CheckinLog();
            log.setId(1000);
            log.setUser(user);
            log.setBike(bike);
            log.setEventType("check in");
            log.setParking(parking);
            log.setTimestamp(Instant.now());
            checkinLogRepository.save(log);

            // Run the query
            List<CheckLogDTO> result = checkinLogRepository.findLogsByUserId(user.getId());

            // Assert result
            assertEquals(1, result.size());
            CheckLogDTO dto = result.get(0);
            assertEquals("check in", dto.eventType);
            assertEquals("CH123", dto.chasisCode);
            assertEquals("Main Lot", dto.parkingName);
            checkinLogRepository.delete(log);
            parkingRepository.delete(parking);
            bicycleRepository.delete(bike);
            usuarioRepository.delete(user);


        }
    }


