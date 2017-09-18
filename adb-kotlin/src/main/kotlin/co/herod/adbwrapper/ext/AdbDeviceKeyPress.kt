@file:Suppress("unused")

package co.herod.adbwrapper.ext

import co.herod.adbwrapper.Adb
import co.herod.adbwrapper.S
import co.herod.adbwrapper.model.AdbDevice

fun AdbDevice.pressKey() = AdbDeviceKeyPress(this)

class AdbDeviceKeyPress(val adbDevice: AdbDevice) {

    fun home() {
        Adb.pressKeyBlocking(adbDevice, S.KEY_EVENT_HOME)
    }

    fun back() {
        Adb.pressKeyBlocking(adbDevice, S.KEY_EVENT_BACK)
    }

    fun backspace() {
        Adb.pressKeyBlocking(adbDevice, S.KEY_EVENT_BACKSPACE)
    }

    fun power() {
        Adb.pressKeyBlocking(adbDevice, S.KEY_EVENT_POWER)
    }

    fun escape() {
        Adb.pressKeyBlocking(adbDevice, S.KEY_EVENT_ESCAPE)
    }
}