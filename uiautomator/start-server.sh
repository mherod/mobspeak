#!/usr/bin/env bash

adb install -r app-debug-androidTest.apk || adb uninstall com.github.uiautomator; adb install -r app-debug-androidTest.apk
adb shell am instrument -w -e package com.github.uiautomator com.github.uiautomator.test/android.support.test.runner.AndroidJUnitRunner &
adb forward tcp:9008 tcp:9008
