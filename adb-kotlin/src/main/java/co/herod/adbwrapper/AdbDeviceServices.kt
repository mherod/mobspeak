package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice

object AdbDeviceServices {

    private const val DISABLE = "disable"
    private const val ENABLE = "enable"

    private const val SERVICE = "svc"

    private const val SPACE = " "

    fun enableService(adbDevice: AdbDevice?, enable: Boolean, serviceType: String) {

        //noinspection StringBufferReplaceableByString,StringBufferWithoutInitialCapacity
        Adb.blocking(adbDevice, StringBuilder()
                .append(AdbCommand.SHELL)
                .append(SPACE)
                .append(SERVICE)
                .append(SPACE)
                .append(serviceType)
                .append(SPACE)
                .append(if (enable) ENABLE else DISABLE)
                .toString())
    }
}
