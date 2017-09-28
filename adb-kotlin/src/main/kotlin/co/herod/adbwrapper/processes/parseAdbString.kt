package co.herod.adbwrapper.processes

import co.herod.adbwrapper.model.AdbDevice

fun parseAdbString(adbDeviceString: String): AdbDevice {

    val adbDevice = AdbDevice()

    val split = adbDeviceString.split("\t".toRegex(), 2)

    adbDevice.deviceIdentifier = split[0]
    adbDevice.type = split[1]

    return adbDevice
}