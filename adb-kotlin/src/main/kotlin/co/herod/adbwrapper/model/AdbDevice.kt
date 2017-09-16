package co.herod.adbwrapper.model

import co.herod.adbwrapper.S

class AdbDevice(
        var deviceIdentifier: String? = null,
        var type: String? = null
) {

    var preferredUiAutomatorStrategy = 0

    override fun toString() = "AdbDevice{deviceIdentifier='$deviceIdentifier', type='$type'}"

    fun isConnectedDevice(): Boolean = type == S.DEVICE_CONNECTED_DEVICE
    fun isEmulator(): Boolean = type == S.DEVICE_EMULATOR

    companion object {

        fun parseAdbString(adbDeviceString: String): AdbDevice {

            val adbDevice = AdbDevice()

            val split = adbDeviceString.split("\t".toRegex(), 2)

            adbDevice.deviceIdentifier = split[0]
            adbDevice.type = split[1]

            return adbDevice
        }
    }
}
