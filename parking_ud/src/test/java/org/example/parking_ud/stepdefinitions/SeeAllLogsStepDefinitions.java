package org.example.parking_ud.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.parking_ud.dao.Bicycle;
import org.example.parking_ud.dao.CheckinLog;
import org.example.parking_ud.dao.Parking;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.BicycleDTO;
import org.example.parking_ud.dto.CheckLogDTO;
import org.example.parking_ud.repositories.BicycleRepository;
import org.example.parking_ud.repositories.CheckinLogRepository;
import org.example.parking_ud.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SeeAllLogsStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private CheckinLogRepository checkinLogRepository;

    @Autowired
    BicycleRepository bicycleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private ResponseEntity<List<CheckLogDTO>> logsResponse;
    private String authToken;
    private Long userId;
    Usuario user;

    @Before
    public void setup() {
        // Clean up test data before each scenario
        checkinLogRepository.deleteAll();
       // userRepository.deleteAll();
    }

    @Given("the student is logged in with email {string} password {string} and wants to see their logs")
    public void the_student_is_logged_in_with_email_password(String email, String password) {
        // Create and save user
        user = new Usuario();
        user = userRepository.findByEmail(email).get();
        this.userId = Long.valueOf(user.getId());


    }

    @When("the student presses Profile button")
    public void the_student_presses_profile_button() {

        CheckinLog log1 = new CheckinLog();
        log1.setId((int)(checkinLogRepository.count() + 1));

        Bicycle bike = bicycleRepository.findByUser_id(user.getId()).get();

        bike.setId(4);
        log1.setBike(bike);
        log1.setUser(user);
        Parking parking = new Parking();
        parking.setId((short) 1);
        log1.setParking( parking);
        log1.setEventType("check in");
        log1.setTimestamp(Instant.now());
        CheckinLog log2 = new CheckinLog();
        log2.setBike(bike);
        log2.setUser(user);

        parking.setId((short) 1);
        log2.setParking( parking);
        log2.setId((int)(checkinLogRepository.count() + 2));
        log2.setEventType("check out");
        log2.setTimestamp(Instant.now());

        checkinLogRepository.save(log1);
        checkinLogRepository.save(log2);


        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<?> request = new HttpEntity<>(headers);

        logsResponse = restTemplate.exchange(
                "/getAllLogs?userId=" + userId,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<CheckLogDTO>>() {}
        );
    }

    @Then("The logs are shown")
    public void the_logs_are_shown() {
        assertEquals(HttpStatus.OK, logsResponse.getStatusCode());
        assertNotNull(logsResponse.getBody());

        List<CheckLogDTO> logs = logsResponse.getBody();
        assertFalse(logs.isEmpty(), "Logs should not be empty");
        assertEquals(2, logs.size(), "Should retrieve 2 logs");


        CheckLogDTO firstLog = logs.get(0);
        assertEquals("check out", firstLog.eventType);
        assertEquals("Ingeniería", firstLog.parkingName);


    }


}

