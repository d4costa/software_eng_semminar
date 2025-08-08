Feature: Student Login
  As a student, I need to log in to the application to view the main page, enter, or retrieve my bicycle from the parking lot.

  Scenario: Successful login
    Given a student is registered with email "testlogin@parkingud.com" and password "password123"
    When the student logs in with email "testlogin@parkingud.com" and password "password123"
    Then the system redirects to the main page

  Scenario: Invalid credentials
    Given a student is registered with email "testlogin@parkingud.com" and password "password123"
    When the student logs in with email "testlogin@parkingud.com" and password "wrongpassword"
    Then the system displays an error message "Invalid credentials"