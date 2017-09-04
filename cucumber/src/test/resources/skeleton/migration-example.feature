Feature: Migration

  Scenario: When I migrate from 6.1 to 6.2
    Given I have a connected device
    When I uninstall the package "com.myunidays"
    Then I have a connected device without the package "com.myunidays"
    When I install the apk at "app-live-release-6.1.apk"
    Then I have a connected device with the package "com.myunidays" version "6.1.0"
    Then I launch the app "com.myunidays"
    Then I see the "Skip" text
    Then I do not see the "Error" text
    When I update the app with the apk at "app-live-release-6.2.apk"
    Then I have a connected device with the package "com.myunidays" version "6.2.0"
    Then I launch the app "com.myunidays"
    Then I see the "Skip" text
    Then I do not see the "Error" text
