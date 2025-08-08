package org.example.parking_ud.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.parking_ud.dao.Bicycle;
import org.example.parking_ud.dao.CheckinLog;
import org.example.parking_ud.dto.UsuarioDto;
import org.example.parking_ud.repositories.CheckinLogRepository;
import org.example.parking_ud.repositories.ParkingRepository;
import org.example.parking_ud.services.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckOutStepDefinitions {
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

    @Given("the student is logged in with email {string} password {string} and has a checked in bicycle")
    public void the_student_is_logged_in_and_has_a_registered_bicycle(String email, String password) {
        // 1. Login the student
        requestBody = String.format("{\"email\": \"%s\",\"password\": \"%s\"}", email,password);
        System.out.println(requestBody);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> loginRequest = new HttpEntity<>(requestBody, headers);
        ResponseEntity<UsuarioDto> loginResponse = restTemplate.postForEntity("/usuario/login", loginRequest, UsuarioDto.class);
        loggedUser = loginResponse.getBody();
        Optional<Bicycle> bike = bicycleService.bicycleRepository.findByUser_id(loggedUser.getId());
        System.out.println(loggedUser.getId());
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestBody = String.format("{\"userId\": \"%s\",\"bikeId\": \"%s\",\"parkingId\": \"%s\"}", loggedUser.getId(),bike.get().id,1);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        response = restTemplate.postForEntity("/CheckIn", request, String.class);
        // 2. Register the bicycle
        //headers.set("Authorization", "Bearer " + loggedUser.getToken());
        //requestBody = String.format("{\"userId\": %d, \"chassisCode\": \"%s\"}", loggedUser.getId(), chassisCode);
        //HttpEntity<String> bikeRequest = new HttpEntity<>(requestBody, headers);
        //restTemplate.postForEntity("/registerBike", bikeRequest, String.class);

        // 2. Set parking lot capacity to full (assuming there's an admin endpoint for this)

    }
    @When("the student presses the Check Out button")
    public void the_student_presses_the_button() {
        Optional<Bicycle> bike = bicycleService.bicycleRepository.findByUser_id(loggedUser.getId());
        System.out.println(loggedUser.getId());
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestBody = String.format("{\"userId\": \"%s\",\"bikeId\": \"%s\",\"parkingId\": \"%s\"}", loggedUser.getId(),bike.get().id,1);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        response = restTemplate.postForEntity("/CheckOut", request, String.class);

    }
    @Then("the system creates an exit record")
    public void the_system_creates_an_exit_record() {

        assertEquals(200, response.getStatusCodeValue());


    }

}
