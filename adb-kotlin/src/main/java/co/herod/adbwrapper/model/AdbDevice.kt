package co.herod.adbwrapper.model

import co.herod.adbwrapper.DEVICE_CONNECTED_DEVICE
import co.herod.adbwrapper.DEVICE_EMULATOR

class AdbDevice {

    var deviceIdentifier: String? = null
        private set
    var type: String? = null
        private set

    var preferredUiAutomatorStrategy = 0

    override fun toString() = "AdbDevice{deviceIdentifier='$deviceIdentifier', type='$type'}"

    fun isConnectedDevice(): Boolean = type == DEVICE_CONNECTED_DEVICE
    fun isEmulator(): Boolean = type == DEVICE_EMULATOR

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
