#Feature: Example
#
#  Scenario: I can use the calculator
#    Given I have a connected device
#    When I launch the app "com.android.calculator2"
#    Then I am on the "Calculator" activity
#    And I see the "DEL" text
#    And I see the "1" text
#    And I see the "2" text
#    And I see the "3" text
#    And I see the "4" text
#    And I see the "5" text
#    And I see the "6" text
#    And I see the "7" text
#    And I see the "8" text
#    And I see the "9" text
#    And I see the "0" text
#    When I touch the "5" text
#    And I touch the "2" text
#    And I touch the "4" text
#    Then I see the "524" text
#    When I touch the "DEL" text
#    Then I see the "52" text
#    And I do not see the "524" text
#    When I touch the "DEL" text
#    Then I see the "5" text
#    And I do not see the "52" text
#    When I close the app "com.android.calculator2"
#    Then I am not on the "Calculator" activity
#
#  Scenario: I can use the calculator with keyboard
#    Given I have a connected device
#    When I launch the app "com.android.calculator2"
#    Then I am on the "Calculator" activity
#    And I see the "DEL" text
#    When I type the "524" text
#    Then I see the "524" text
#    When I touch the "DEL" text
#    Then I see the "52" text
#    And I do not see the "524" text
#    When I touch the "DEL" text
#    Then I see the "5" text
#    And I do not see the "52" text
#    When I close the app "com.android.calculator2"
#    Then I am not on the "Calculator" activity