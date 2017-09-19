#!/usr/bin/env bash
EMULATOR=`which emulator`
AVD_NAME=`${EMULATOR} -list-avds | tail -n1`
${EMULATOR} -avd ${AVD_NAME} &
