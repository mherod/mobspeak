package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.typeText

fun AdbDeviceTestHelper.typeText(text: String) = adbDevice.typeText(text)