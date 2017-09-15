package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.testing.AdbTestHelper
import co.herod.adbwrapper.testing.NoConnectedAdbDeviceException

inline fun <R> withAdbDevice(block: AdbDevice.() -> R): R {
    return AdbTestHelper.adbDevice?.block() ?: throw NoConnectedAdbDeviceException()
}