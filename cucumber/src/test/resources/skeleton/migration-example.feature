#Feature: Migration
#
#  Scenario: When I can launch 6.1
#    Given I have a connected device
#    When I uninstall the package "com.myunidays"
#    Then I have a connected device without the package "com.myunidays"
#    When I install the apk at "/Users/matthewherod/releases/app-live-release-6.1.0.apk"
#    Then I have a connected device with the package "com.myunidays" version "6.1.0"
#    When I launch the app "com.myunidays"
#    Then I am on the "com.myunidays" activity
#    Then I am on the "onboarding" activity
#    When I see the "Skip" text
#    Then I touch the "Skip" text
#    When I see the "Done" text
#    Then I touch the "Done" text
#    Then I am on the "com.myunidays" activity
#    Then I am not on the "onboarding" activity
#    Then I am on the "home" activity
#    Then I see the "View all" text
