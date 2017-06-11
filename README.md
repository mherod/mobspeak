# adb-java-wrapper

A Java wrapper for the Android Debugging Bridge

## Getting started

Import the library to your Java project using Gradle

~~~~
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    compile 'com.github.mherod:adb-java-wrapper:v0.1-alpha'
}
~~~~

## Usage Examples

Output all adb commands in your session to console
~~~~
AdbStreams.streamAdbCommands().subscribe(System.out::println);
~~~~

Get the first connected device
~~~~
AdbDevice connectedAdbDevice = AdbDeviceManager.getConnectedDevice();
~~~~

Turn it's screen on
~~~~
AdbDeviceActions.turnDeviceScreenOn(connectedAdbDevice);
~~~~

Press the home button
~~~~
AdbDeviceActions.pressHomeButton(connectedAdbDevice);
~~~~