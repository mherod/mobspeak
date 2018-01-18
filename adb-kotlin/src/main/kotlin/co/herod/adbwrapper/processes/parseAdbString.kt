/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.processes

import co.herod.adbwrapper.model.AdbDevice

fun parseAdbString(adbDeviceString: String): AdbDevice = adbDeviceString.split(
        regex = "\t".toRegex(),
        limit = 2
).let {
    AdbDevice(
            deviceIdentifier = it[0],
            type = it[1]
    )
}