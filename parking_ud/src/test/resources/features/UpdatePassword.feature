Feature: Password Recovery
  As a student, I need to reset my password.

  Scenario: "the student presses the Reset Password Button"
    Given the student is logged in with email "test@parkingud.com" password "password123" and wants to change their password"
    When the student presses the Reset password button
    Then password is reset