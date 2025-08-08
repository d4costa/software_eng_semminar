Feature: Student Registration
  As a student, I need to register in the application to be able to log in and interact with other features.

  Scenario: Successful registration
    Given the student provides valid registration details with email "test@parkingud.com" and password "password123" nombre "UserName" apellido "testLastName" and studentId "20222089015"
    When the student submits the registration form
    Then the system saves the data and allows login

  Scenario: Required fields are empty
    Given the student leaves the email field empty
    When the student submits the registration form
    Then the system displays a validation message "Email is required"

  Scenario: Email already registered
    Given a student is already registered with email "test@parkingud.com"
    When the student submits the registration form with email "test@parkingud.com"
    Then the system displays a message "Email already in use"