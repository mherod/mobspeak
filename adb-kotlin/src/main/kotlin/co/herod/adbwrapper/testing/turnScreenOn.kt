/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.screen
import co.herod.adbwrapper.device.turnOn

fun AdbDeviceTestHelper.turnScreenOn() = with(adbDevice) {
    screen().turnOn()
}