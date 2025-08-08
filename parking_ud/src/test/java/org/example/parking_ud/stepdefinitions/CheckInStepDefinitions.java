package org.example.parking_ud.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.parking_ud.dao.Bicycle;
import org.example.parking_ud.dao.CheckinLog;
import org.example.parking_ud.dao.Parking;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.BicycleDTO;
import org.example.parking_ud.dto.UsuarioDto;
import org.example.parking_ud.repositories.CheckinLogRepository;
import org.example.parking_ud.repositories.ParkingRepository;
import org.example.parking_ud.services.BicycleService;
import org.example.parking_ud.services.CheckinLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.swing.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckInStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    BicycleService bicycleService;

    @Autowired
    ParkingRepository parkingRepository;
    @Autowired
    CheckinLogRepository checkinLogRepository;

    private ResponseEntity<String> response;

    private HttpHeaders headers = new HttpHeaders();
    private String requestBody;
    private UsuarioDto loggedUser;

    @Given("the student is logged in with email {string} password {string} and has a registered bicycle with chassis code {string}")
    public void the_student_is_logged_in_and_has_a_registered_bicycle(String email, String password, String chassisCode) {
        // 1. Login the student
        requestBody = String.format("{\"email\": \"%s\",\"password\": \"%s\"}", email,password);
        System.out.println(requestBody);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> loginRequest = new HttpEntity<>(requestBody, headers);
        ResponseEntity<UsuarioDto> loginResponse = restTemplate.postForEntity("/usuario/login", loginRequest, UsuarioDto.class);
        loggedUser = loginResponse.getBody();

        // 2. Register the bicycle
        //headers.set("Authorization", "Bearer " + loggedUser.getToken());
        //requestBody = String.format("{\"userId\": %d, \"chassisCode\": \"%s\"}", loggedUser.getId(), chassisCode);
        //HttpEntity<String> bikeRequest = new HttpEntity<>(requestBody, headers);
        //restTemplate.postForEntity("/registerBike", bikeRequest, String.class);

        // 2. Set parking lot capacity to full (assuming there's an admin endpoint for this)

    }
/*
    @Given("the student is logged in with email {string} password {string} and their bicycle with chassis code {string} is checked in")
    public void the_student_is_logged_in_and_bicycle_checked_in(String email,String password, String chassisCode) {
        // 1. First ensure the student is logged in and has the bicycle registered
        the_student_is_logged_in_and_has_a_registered_bicycle(email, password, chassisCode);
        Parking p =  parkingRepository.findById((short) 1).get();

        if (p.getAvCapacity() ==0){
            p.setAvCapacity((short) (2));
            parkingRepository.save(p);}
        // 2. Then check in the bicycle
        Optional<Bicycle> bike = bicycleService.bicycleRepository.findByUser_id(loggedUser.getId());
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestBody = String.format("{\"userId\": \"%s\",\"bikeId\": \"%s\",\"parkingId\": \"%s\"}", loggedUser.getId(),bike.get().id,1);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        response = restTemplate.postForEntity("/CheckIn", request, String.class);
    }
*/
    @Given("the student is logged in with email {string} and password {string} and the parking lot is full")
    public void theStudentIsLoggedInWithEmailAndPasswordAndTheParkingLotIsFull(String email, String password) {
        // 1. Login the student
        requestBody = String.format("{\"email\": \"%s\",\"password\": \"%s\"}", email,password);
        HttpEntity<String> loginRequest = new HttpEntity<>(requestBody, headers);
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<UsuarioDto> loginResponse = restTemplate.postForEntity("/usuario/login", loginRequest, UsuarioDto.class);
        loggedUser = loginResponse.getBody();

        // 2. Set parking lot capacity to full (assuming there's an admin endpoint for this)
        Parking p =  parkingRepository.findById((short) 1).get();

        if (p.getAvCapacity() >=0){
            p.setAvCapacity((short) (0));
            parkingRepository.save(p);}
    }

    @When("the student presses the Check In button")
    public void the_student_presses_the_button() {
       Optional<Bicycle> bike = bicycleService.bicycleRepository.findByUser_id(loggedUser.getId());
       System.out.println(loggedUser.getId());
        headers.setContentType(MediaType.APPLICATION_JSON);
       requestBody = String.format("{\"userId\": \"%s\",\"bikeId\": \"%s\",\"parkingId\": \"%s\"}", loggedUser.getId(),bike.get().id,1);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        response = restTemplate.postForEntity("/CheckIn", request, String.class);

    }

    @Then("the system creates an entry record")
    public void the_system_creates_an_entry_record() {

        assertEquals(200, response.getStatusCodeValue());

        Optional<CheckinLog> lastLog = checkinLogRepository.findTopByUserIdOrderByTimestampDesc(loggedUser.getId());
        CheckinLog l = lastLog.get();
        checkinLogRepository.delete(l);
    }

    @Then("the system displays a warning {string}")
    public void the_system_displays_a_warning(String message) {

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains(message));
    }

    @Then("the system informs parking status Parking is full")
    public void the_system_notifies() {
        Parking p =  parkingRepository.findById((short) 1).get();
        if (p.getAvCapacity() ==0){
            p.setAvCapacity((short) (200));
            parkingRepository.save(p);}
        assertEquals(409, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Parking is full"));
    }


}
