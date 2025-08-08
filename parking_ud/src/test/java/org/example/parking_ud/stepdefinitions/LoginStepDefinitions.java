package org.example.parking_ud.stepdefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.UsuarioDto;
import org.example.parking_ud.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class LoginStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ResponseEntity<UsuarioDto> response;

    @Given("a student is registered with email {string} and password {string}")
    public void a_student_is_registered_with_email_and_password(String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password)); // Encode password
        usuario.setNombre("Test");
        usuario.setApellido("User");
        usuario.setStudentId(BigDecimal.valueOf(202000));
        usuario.setId(120000000);
        usuarioRepository.save(usuario);

    }

    @When("the student logs in with email {string} and password {string}")
    public void the_student_logs_in_with_email_and_password(String email, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        response = restTemplate.postForEntity("/usuario/login", request, UsuarioDto.class);
    }

    @Then("the system redirects to the main page")
    public void the_system_redirects_to_the_main_page() {
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("Test", response.getBody().getNombre());
        assertEquals("User", response.getBody().getApellido());
    }

    @Then("the system displays an error message {string}")
    public void the_system_displays_an_error_message(String message) {
        assertEquals(401, response.getStatusCodeValue());
        assertNull(response.getBody()); // Controller returns no body for 401
    }
}
