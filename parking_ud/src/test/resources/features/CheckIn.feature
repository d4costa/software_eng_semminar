Feature: Bicycle Parking
  As a student, I need to park my bicycle in the parking lot to keep a record of the entry.

  Scenario: Successful entry
    Given the student is logged in with email "test@parkingud.com" and has a registered bicycle with chassis code "ABC123"
    When the student presses the Check In button
    Then the system creates an entry record

  Scenario: Bicycle already checked in
    Given the student is logged in with email "test@parkingud.com" and their bicycle with chassis code "ABC123" is checked in
    When the student presses the Check In button
    Then the system displays a warning "Bike already checked in."

  Scenario: Parking lot full
    Given the student is logged in with email "test@parkingud.com" and the parking lot is full
    When the student presses the Check In button
    Then the system informs parking status Parking is full