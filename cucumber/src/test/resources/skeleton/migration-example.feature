Feature: Migration

  Scenario: When I can launch 6.1
    Given I have a connected device
    When I uninstall the package "com.myunidays"
    Then I have a connected device without the package "com.myunidays"
    When I install the apk at "/Users/matthewherod/releases/app-live-release-6.1.0.apk"
    Then I have a connected device with the package "com.myunidays" version "6.1.0"
    Then I launch the app "com.myunidays"
    When I see the "Skip" text
    Then I touch the "Skip" text
    When I see the "Done" text
    Then I touch the "Done" text
    Then I do not see the "Error" text
    Then I see the "View all" text

