/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.processes

import co.herod.adbwrapper.model.AdbDevice

fun parseAdbString(adbDeviceString: String): AdbDevice = adbDeviceString
        .split("\t".toRegex(), 2).let(::adbDevice)

private fun adbDevice(it: List<String>): AdbDevice = AdbDevice(it[0], it[1])