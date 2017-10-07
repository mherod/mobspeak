@file:Suppress("unused")

package co.herod.adbwrapper.device.input

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbKeyEvent
import co.herod.adbwrapper.pressKey
import co.herod.kotlin.ext.blockingSilent

class AdbDeviceKeyPress(val adbDevice: AdbDevice)

fun AdbDevice.pressKey() = AdbDeviceKeyPress(this)

fun AdbDeviceKeyPress.escape() {
    adbDevice.pressKey(AdbKeyEvent.KEY_EVENT_ESCAPE).blockingSilent()
}

fun AdbDeviceKeyPress.power() {
    adbDevice.pressKey(AdbKeyEvent.KEY_EVENT_POWER).blockingSilent()
}

fun AdbDeviceKeyPress.backspace() {
    adbDevice.pressKey(AdbKeyEvent.KEY_EVENT_BACKSPACE).blockingSilent()
}

fun AdbDeviceKeyPress.back() {
    adbDevice.pressKey(AdbKeyEvent.KEY_EVENT_BACK).blockingSilent()
}

fun AdbDeviceKeyPress.home() {
    adbDevice.pressKey(AdbKeyEvent.KEY_EVENT_HOME).blockingSilent()
}