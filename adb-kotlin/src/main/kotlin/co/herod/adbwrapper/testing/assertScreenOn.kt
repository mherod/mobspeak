/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.isOn
import co.herod.adbwrapper.device.screen

fun AdbDeviceTestHelper.assertScreenOn() = with(adbDevice) {
    assert(screen().isOn()) { "Screen was not on" }
}