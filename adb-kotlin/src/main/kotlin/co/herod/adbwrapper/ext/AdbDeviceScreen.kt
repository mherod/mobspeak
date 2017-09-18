@file:Suppress("unused")

package co.herod.adbwrapper.ext

import co.herod.adbwrapper.Adb
import co.herod.adbwrapper.S
import co.herod.adbwrapper.execute
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

fun AdbDevice.pullCapture() = with(Adb) {
    this@pullCapture.execute("shell screencap -p /sdcard/screen.png")
    this@pullCapture.execute("pull /sdcard/screen.png")
    this@pullCapture.execute("shell rm /sdcard/screen.png")
}