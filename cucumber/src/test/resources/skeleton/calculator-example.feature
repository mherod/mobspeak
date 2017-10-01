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

  Scenario Outline: I can use the calculator
    When I type the "<op1>" text
    Then I see the "<op1>" text
    When I type the "<operator>" text
    Then I type the "<op2>" text
    Then I see the "<op1>" text
    Then I see the "<op2>" text
    Then I touch the "=" button
    Then I see the "<result>" text
    Then I take a screenshot
    Then I do not see the "<op1>" text
    Then I do not see the "<op2>" text
    When I touch the "CLR" button
    When I do not see the text "CLR"
    When I see the text "DEL"
    When I close the app "com.android.calculator2"
    Then I am not on the "Calculator" activity

    Examples:
      | op1    | operator | op2    | result |
      | 111    | +        | 222    | 333    |
      | 401    | +        | 98     | 499    |
      | 99,999 | -        | 66,666 | 33,333 |
      | 8,888  | +        | 202    | 9,090  |
      | 780    | +        | 25     | 805    |
      | 44,999 | -        | 22,666 | 22,333 |

  Scenario: I can use the calculator by touch
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