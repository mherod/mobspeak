Feature: Example

  Background:
    Given I have a connected device
    When I close the app "com.android.calculator2"
    When I launch the app "com.android.calculator2"
    Then I am on the "Calculator" activity
    When I dismiss the keyboard
    Then I see the "DEL" button
    And I see the "1" button
    And I see the "2" button
    And I see the "3" button
    And I see the "4" button
    And I see the "5" button
    And I see the "6" button
    And I see the "7" button
    And I see the "8" button
    And I see the "9" button
    And I see the "0" button

  Scenario: I can use the calculator
    When I touch the "5" button
    And I touch the "2" button
    And I touch the "4" button
    Then I see the "524" text
    When I touch the "DEL" button
    Then I see the "52" text
    And I do not see the "524" text
    When I touch the "DEL" button
    Then I see the "5" text
    And I do not see the "52" text
    When I close the app "com.android.calculator2"
    Then I am not on the "Calculator" activity

  Scenario: I can use the calculator with keyboard
    When I type the "524" text
    Then I see the "524" text
    When I touch the "DEL" text
    Then I see the "52" text
    And I do not see the "524" text
    When I touch the "DEL" text
    Then I see the "5" text
    And I do not see the "52" text
    When I close the app "com.android.calculator2"
    Then I am not on the "Calculator" activity