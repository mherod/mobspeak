package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.input.back
import co.herod.adbwrapper.device.input.pressKey

fun AdbDeviceTestHelper.backButton() {
    with(adbDevice) { pressKey().back() }
}