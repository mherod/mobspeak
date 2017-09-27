@file:Suppress("unused")

package co.herod.adbwrapper.device

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbKeyEvent
import co.herod.adbwrapper.pressKey
import co.herod.kotlin.ext.blockingSilent

fun AdbDevice.pressKey() = AdbDeviceKeyPress(this)

class AdbDeviceKeyPress(val adbDevice: AdbDevice) {

    fun home() {
        adbDevice.pressKey(AdbKeyEvent.KEY_EVENT_HOME).blockingSilent()
    }

    fun back() {
        adbDevice.pressKey(AdbKeyEvent.KEY_EVENT_BACK).blockingSilent()
    }

    fun backspace() {
        adbDevice.pressKey(AdbKeyEvent.KEY_EVENT_BACKSPACE).blockingSilent()
    }

    fun power() {
        adbDevice.pressKey(AdbKeyEvent.KEY_EVENT_POWER).blockingSilent()
    }

    fun escape() {
        adbDevice.pressKey(AdbKeyEvent.KEY_EVENT_ESCAPE).blockingSilent()
    }
}