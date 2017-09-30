package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.screen
import co.herod.adbwrapper.device.turnOff

fun AdbDeviceTestHelper.turnScreenOff() = with(adbDevice) {
    screen().turnOff()
}