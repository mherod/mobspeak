package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.pressKey

fun AdbDeviceTestHelper.backButton() {
    with(adbDevice) { pressKey().back() }
}