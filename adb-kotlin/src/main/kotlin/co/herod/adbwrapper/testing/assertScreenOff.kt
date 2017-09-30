package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.isOn
import co.herod.adbwrapper.device.screen

fun AdbDeviceTestHelper.assertScreenOff() = with(adbDevice) {
    if (screen().isOn()) {
        throw AssertionError("Screen was on")
    }
}