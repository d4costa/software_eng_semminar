Feature: Bicycle Registration
  As a student, I want to register my bicycle’s information to be able to enter or retrieve it from the parking lot.

  Scenario: Successful bicycle registration
    Given the student is logged in with email "test@parkingud.com"
    When the student submits the bicycle registration form with chassisCode "ABC1234" color "blue" brand "GW" description "MountainBike"
    Then the system registers the bicycle successfully

  Scenario: Bicycle already registered
    Given the student is logged in with email "test@parkingud.com" and a bicycle with chassis code "ABC1234" is registered
    When the student submits the bicycle registration form with chassisCode "ABC1234" color "blue" brand "GW" description "MountainBike"
    Then the system sends message "Bicycle already registered"

  Scenario: Incomplete or invalid data
    Given the student is logged in with email "test@parkingud.com"
    When the student submits the bicycle registration form with empty chassis code
    Then the system notifies "All fields must be filled out"