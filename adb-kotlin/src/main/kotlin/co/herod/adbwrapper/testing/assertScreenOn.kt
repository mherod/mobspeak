package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.isOn
import co.herod.adbwrapper.device.screen

fun AdbDeviceTestHelper.assertScreenOn() = with(adbDevice) {
    if (screen().isOn().not()) {
        throw AssertionError("Screen was not on")
    }
}