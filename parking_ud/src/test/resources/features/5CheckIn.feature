Feature: Bicycle Parking
  As a student, I need to park my bicycle in the parking lot to keep a record of the entry.

  Scenario: Successful entry
    Given the student is logged in with email "juan.perez@example.com" password "pass123" and has a registered bicycle with chassis code "ABC1234"
    When the student presses the Check In button
    Then the system creates an entry record



  Scenario: Parking lot full or already checked in
    Given  the student is logged in with email "juan.perez@example.com" and password "pass123" and the parking lot is full
    When the student presses the Check In button
    Then the system informs parking status Parking is full