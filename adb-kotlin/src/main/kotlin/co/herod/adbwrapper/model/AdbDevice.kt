@file:Suppress("unused", "MemberVisibilityCanPrivate")

package co.herod.adbwrapper.model

import co.herod.adbwrapper.S
import co.herod.adbwrapper.getWindowBounds

class AdbDevice(
        var deviceIdentifier: String? = null,
        var type: String? = null
) {

    var preferredUiAutomatorStrategy = 0

    val windowBounds: UiBounds by lazy {
        @Suppress("DEPRECATION")
        getWindowBounds()
    }

    val physical: Boolean by lazy {
        @Suppress("DEPRECATION")
        isConnectedDevice()
    }

    val emulator: Boolean by lazy {
        @Suppress("DEPRECATION")
        isEmulator()
    }

    @Deprecated(replaceWith = ReplaceWith("physical"), message = "Use the 'physical' property")
    fun isConnectedDevice(): Boolean = type == S.DEVICE_CONNECTED_DEVICE

    @Deprecated(replaceWith = ReplaceWith("emulator"), message = "Use the 'emulator' property")
    fun isEmulator(): Boolean = type == S.DEVICE_EMULATOR

    override fun toString(): String {
        return "AdbDevice(deviceIdentifier=$deviceIdentifier, type=$type, preferredUiAutomatorStrategy=$preferredUiAutomatorStrategy)"
    }

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
