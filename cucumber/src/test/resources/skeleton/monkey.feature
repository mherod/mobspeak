#Feature: Walking around a zoo
#
#  Scenario: A monkey snatches my device
#    Given I have a connected device
#    When I uninstall the package "com.myunidays"
#    Then I have a connected device without the package "com.myunidays"
#    When I install the apk at "/Users/matthewherod/releases/app-live-release-6.1.0.apk"
#    Then I have a connected device with the package "com.myunidays" version "6.1.0"
#    When a monkey snatches my device
#    When I have a connected device
#    Then I set the device to default settings
