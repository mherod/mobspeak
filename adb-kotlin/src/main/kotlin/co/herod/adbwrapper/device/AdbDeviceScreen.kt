@file:Suppress("unused")

package co.herod.adbwrapper.device

import co.herod.adbwrapper.S.Companion.PROPERTY_SCREEN_STATE
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysKey
import co.herod.adbwrapper.model.filterProperty
import co.herod.adbwrapper.model.hasPositiveValue

class AdbDeviceScreen(val adbDevice: AdbDevice)

fun AdbDevice.screen() = AdbDeviceScreen(this)

fun AdbDeviceScreen.turnOn() = with(adbDevice) {
    if (isOn().not()) pressKey().power()
}

fun AdbDeviceScreen.turnOff() = with(adbDevice) {
    if (isOn()) pressKey().power()
}

fun AdbDeviceScreen.isOn() = with(adbDevice) {
    dumpsys().dump(dumpsysKey = DumpsysKey.DISPLAY)
            .filterProperty(PROPERTY_SCREEN_STATE)
            .hasPositiveValue()
}
