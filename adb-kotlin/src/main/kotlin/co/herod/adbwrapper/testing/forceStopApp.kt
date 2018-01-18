/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.forceStop
import co.herod.adbwrapper.device.pm

fun AdbDeviceTestHelper.forceStopApp(packageName: String) {
    adbDevice.pm().forceStop(packageName)
}