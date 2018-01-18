/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.S.Companion.PROPERTY_SHOW_REQUESTED
import co.herod.adbwrapper.device.dump
import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.device.input.escape
import co.herod.adbwrapper.device.input.pressKey
import co.herod.adbwrapper.model.DumpsysKey
import co.herod.adbwrapper.model.isPropertyPositive

fun AdbDeviceTestHelper.dismissKeyboard() = with(adbDevice) {
    if (dumpsys().dump(dumpsysKey = DumpsysKey.INPUT_METHOD).isPropertyPositive(PROPERTY_SHOW_REQUESTED)) {
        pressKey().escape()
    }
}