#!/usr/bin/env bash

adb install -r app-debug.apk || ( adb uninstall com.github.uiautomator; adb install -r app-debug.apk )
adb install -r app-debug-androidTest.apk || ( adb uninstall com.github.uiautomator.test; adb install -r app-debug-androidTest.apk )

adb forward tcp:9008 tcp:9008
adb shell am instrument -w -e package com.github.uiautomator com.github.uiautomator.test/android.support.test.runner.AndroidJUnitRunner
