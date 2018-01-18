/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.device.shell

import co.herod.adbwrapper.model.AdbDevice

fun AdbDevice.shell(): AdbDeviceShell =
        AdbDeviceShell(adbDevice = this)