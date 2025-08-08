Feature: Bicycle CheckOut
  As a student, I need to park my bicycle in the parking lot to keep a record of the entry.

  Scenario: the student presses the Check Out button
    Given the student is logged in with email "maria.gomez@example.com" password "secretpass" and has a checked in bicycle
    When the student presses the Check Out button
    Then the system creates an exit record
