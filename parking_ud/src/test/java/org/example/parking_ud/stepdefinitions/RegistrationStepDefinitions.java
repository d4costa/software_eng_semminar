package org.example.parking_ud.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class RegistrationStepDefinitions {

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        private ResponseEntity<Boolean> response;
        private String requestBody;

    @Given("the student provides valid registration details with email {string} and password {string} nombre {string} apellido {string} and studentId {string}")
    public void theStudentProvidesValidRegistrationDetailsWithEmailAndPasswordNombreApellidoAndStudentId(String email, String password, String nombre, String apellido, String studentId) {
        BigDecimal studentIdb = BigDecimal.valueOf(Long.parseLong(studentId));
        Optional<Usuario> borrar = usuarioRepository.findByEmail(email);
        if( borrar.isPresent())
            usuarioRepository.delete(borrar.get());
            requestBody = String.format(
                    "{\"nombre\": \"%s\", \"apellido\": \"%s\", \"studentId\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}",
                    nombre, apellido, studentIdb, email, password
            );
        }

    @Given("the student leaves the email field empty")
    public void the_student_leaves_the_email_field_empty() {
        requestBody = "{\"nombre\": \"Test\", \"apellido\": \"User\", \"studentId\": \"12345\", \"email\": \"\", \"password\": \"password123\"}";
    }

    @Given("a student is already registered with email {string}")
    public void a_student_is_already_registered_with_email(String email) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode("password123"));
        usuario.setNombre("Test");
        usuario.setApellido("User");
        usuario.setStudentId(BigDecimal.valueOf(12345678));
        usuario.setId(100045);
        Optional<Usuario> borrar = usuarioRepository.findByEmail(email);
        if( borrar.isEmpty())
            usuarioRepository.save(usuario);
    }

    @When("the student submits the registration form")
    public void the_student_submits_the_registration_form() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        System.out.println(requestBody);
        response = restTemplate.postForEntity("/usuario/register", request, Boolean.class);
    }

    @When("the student submits the registration form with email {string}")
    public void the_student_submits_the_registration_form_with_email(String email) {
        requestBody = String.format(
                "{\"nombre\": \"Test\", \"apellido\": \"User\", \"studentId\": \"12345\", \"email\": \"%s\", \"password\": \"password123\"}",
                email
        );
        the_student_submits_the_registration_form();
    }

    @Then("the system saves the data and allows login")
    public void the_system_saves_the_data_and_allows_login() {
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody());
        assertTrue(usuarioRepository.findByEmail("test@parkingud.com").isPresent());
    }

    @Then("the system displays a validation message {string}")
    public void the_system_displays_a_validation_message(String message) {
        assertEquals(400, response.getStatusCodeValue());
        assertFalse(response.getBody() != null && response.getBody()); // No body or false
    }

    @Then("the system displays a message {string}")
    public void the_system_displays_a_message(String message) {
        assertEquals(400, response.getStatusCodeValue());
        assertFalse(response.getBody() != null && response.getBody()); // No body or false
    }


}


