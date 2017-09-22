#!/usr/bin/env bash
EMULATOR=`which emulator`
AVD_NAME=`${EMULATOR} -list-avds | tail -n1`
adb emu || ${EMULATOR} -avd ${AVD_NAME} -no-audio -no-window &
echo "wait for emulator"
adb wait-for-device shell 'while [[ -z $(getprop sys.boot_completed) ]]; do sleep 1; done; input keyevent 82'
echo "emulator ready"