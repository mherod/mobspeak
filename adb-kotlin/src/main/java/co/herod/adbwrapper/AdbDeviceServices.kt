package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice

object AdbDeviceServices {

    fun AdbDevice.enableService(enable: Boolean, serviceType: String) {

        //noinspection StringBufferReplaceableByString,StringBufferWithoutInitialCapacity
        Adb.now(this, StringBuilder()
                .append(S.SHELL)
                .append(S.SPACE)
                .append(S.SERVICE)
                .append(S.SPACE)
                .append(serviceType)
                .append(S.SPACE)
                .append(if (enable) S.ENABLE else S.DISABLE)
                .toString()
        )
    }
}
