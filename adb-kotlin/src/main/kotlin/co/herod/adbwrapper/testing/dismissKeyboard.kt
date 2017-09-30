package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.dump
import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.device.pressKey
import co.herod.adbwrapper.model.DumpsysKey
import co.herod.adbwrapper.model.isPropertyPositive

fun AdbDeviceTestHelper.dismissKeyboard() = with(adbDevice) {
    if (dumpsys().dump(dumpsysKey = DumpsysKey.INPUT_METHOD).isPropertyPositive("mShowRequested")) {
        pressKey().escape()
    }
}