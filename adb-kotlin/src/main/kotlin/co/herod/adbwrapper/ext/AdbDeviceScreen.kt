@file:Suppress("unused")

package co.herod.adbwrapper.ext

import co.herod.adbwrapper.S
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.isPositive
import co.herod.adbwrapper.model.property

class AdbDeviceScreen(val adbDevice: AdbDevice)

fun AdbDevice.screen() = AdbDeviceScreen(this)

fun AdbDeviceScreen.turnOn() = with(adbDevice) {
    if (isOn().not()) pressKey().power()
}

fun AdbDeviceScreen.turnOff() = with(adbDevice) {
    if (isOn()) pressKey().power()
}

fun AdbDeviceScreen.isOn() = with(adbDevice) {
    dumpsys().display().property(S.KEY_SCREEN_STATE).isPositive()
}