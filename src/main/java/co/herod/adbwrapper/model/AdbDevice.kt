package co.herod.adbwrapper.model

/**
 * Created by matthewherod on 23/04/2017.
 */
class AdbDevice {

    private val DEVICE_CONNECTED_DEVICE = "device"
    private val DEVICE_EMULATOR = "emulator"

    var deviceIdentifier: String? = null
        private set
    var type: String? = null
        private set

    override fun toString() = "AdbDevice{deviceIdentifier='$deviceIdentifier', type='$type'}"

    fun isConnectedDevice(): Boolean = type == DEVICE_CONNECTED_DEVICE
    fun isEmulator(): Boolean = type == DEVICE_EMULATOR

    companion object {
        fun parseAdbString(adbDeviceString: String): AdbDevice {

            val adbDevice = AdbDevice()

            val split = adbDeviceString.split("\t".toRegex(), 2).toTypedArray()

            adbDevice.deviceIdentifier = split[0]
            adbDevice.type = split[1]

            return adbDevice
        }
    }

}
