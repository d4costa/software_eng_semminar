package org.example.parking_ud.stepdefinitions;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.parking_ud.dao.Bicycle;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.UsuarioDto;
import org.example.parking_ud.repositories.BicycleRepository;
import org.example.parking_ud.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BicycleRegistrationStepDefinitions {

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
    UsuarioRepository usuarioRepository;

        @Autowired
        BicycleRepository bicycleRepository;
        private ResponseEntity<String> response;
        private String requestBody;
        Usuario loggedUser;


        @Given("the student is logged in with email {string}")
        public void the_student_is_logged_in_with_email(String email) {
           loggedUser = usuarioRepository.findByEmail(email).get();
        }

        @Given("the student is logged in with email {string} and a bicycle with chassis code {string} is registered")
        public void the_student_is_logged_in_and_bicycle_registered(String email, String chassisCode) {
            Optional<Bicycle> bike = bicycleRepository.findByChasisCode(chassisCode);
            loggedUser = usuarioRepository.findByEmail(email).get();
            if (bike.isEmpty()){
              Bicycle savedBike = new Bicycle();
                savedBike.setId(11001);
                savedBike.chasisCode = chassisCode;
                savedBike.user = loggedUser;
                savedBike.description =" a";
                savedBike.color = "blue";
                bicycleRepository.save(savedBike);
            }
        }

        @When("the student submits the bicycle registration form with chassisCode {string} color {string} brand {string} description {string}")
        public void the_student_submits_the_bicycle_registration_form_with_chassis_code(String chassisCode,String color,String brand,String description) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            int userId = loggedUser.getId();
            // Create JSON with all fields including userId
            requestBody = String.format("{\"userId\": %d, \"chasisCode\": \"%s\", \"color\": \"%s\", \"brand\": \"%s\", \"description\": \"%s\"}",
                    userId, chassisCode, color, brand, description);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            response = restTemplate.postForEntity("/registerBike", request, String.class);
        }

        @When("the student submits the bicycle registration form with empty chassis code")
        public void the_student_submits_the_bicycle_registration_form_with_empty_chassis_code() {
            requestBody = "{\"chassisCode\": \"\"}";
            the_student_submits_the_bicycle_registration_form_with_chassis_code("","blue","gw","mountainBike");
        }

        @Then("the system registers the bicycle successfully")
        public void the_system_registers_the_bicycle_successfully() {
            assertEquals(200, response.getStatusCodeValue());
        }

        @Then("the system notifies {string}")
        public void the_system_notifies(String message) {
            assertEquals(400, response.getStatusCodeValue());
            //assertTrue(response.getBody().contains(message));
        }

    @Then("the system sends message {string}")
    public void the_system_message(String message) {
        assertEquals(400, response.getStatusCodeValue());
        //assertTrue(response.getBody().contains(message));
    }
    }


