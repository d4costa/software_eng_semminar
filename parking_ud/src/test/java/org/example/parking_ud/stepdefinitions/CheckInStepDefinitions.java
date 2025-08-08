package org.example.parking_ud.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckInStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    @Given("the student is logged in with email {string} and has a registered bicycle with chassis code {string}")
    public void the_student_is_logged_in_and_has_a_registered_bicycle(String email, String chassisCode) {
        // Setup: Mock login and register bicycle
    }

    @Given("the student is logged in with email {string} and their bicycle with chassis code {string} is checked in")
    public void the_student_is_logged_in_and_bicycle_checked_in(String email, String chassisCode) {
        // Setup: Mock login and mark bicycle as checked in
    }

    @Given("the student is logged in with email {string} and the parking lot is full")
    public void the_student_is_logged_in_and_parking_lot_is_full(String email) {
        // Setup: Mock login and set parking lot capacity to full
    }

    @When("the student presses the Check In button")
    public void the_student_presses_the_button(String button) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        response = restTemplate.postForEntity("/api/bicycles/enter", request, String.class);
    }

    @Then("the system creates an entry record")
    public void the_system_creates_an_entry_record() {
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("the system displays a warning {string}")
    public void the_system_displays_a_warning(String message) {
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains(message));
    }

    @Then("the system informs parking status Parking is full")
    public void the_system_notifies(String message) {
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains(message));
    }


}
