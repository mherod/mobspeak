#!/usr/bin/env bash
while adb emu kill; do sleep 0; done
sleep 2
killall emulator64-crash-service