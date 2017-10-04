#!/usr/bin/env bash

until adb shell pm list packages | grep "uiautomator" | grep "uiautomator.test"; do
	adb install -r app-debug.apk || ( adb uninstall com.github.uiautomator; adb install -r app-debug.apk )
	adb install -r app-debug-androidTest.apk || ( adb uninstall com.github.uiautomator.test; adb install -r app-debug-androidTest.apk )
done

adb forward tcp:9008 tcp:9008
while adb shell am instrument -w -e package com.github.uiautomator com.github.uiautomator.test/android.support.test.runner.AndroidJUnitRunner | grep -v "INSTRUMENTATION"; do sleep 0; done
