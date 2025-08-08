Feature: Logs Visualization
  As a student, I need to be able to see all of my entries and exits from the parking

  Scenario: "the student presses the Reset Password Button"
    Given the student is logged in with email "test@parkingud.com" password "password123" and wants to see their logs
    When the student presses Profile button
    Then The logs are shown