package co.herod.adbwrapper.device

import co.herod.adbwrapper.execute
import co.herod.adbwrapper.model.AdbDevice

fun AdbDevice.pullCapture() {
    execute("shell screencap -p /sdcard/screen.png")
    execute("pull /sdcard/screen.png")
    execute("shell rm /sdcard/screen.png")
}