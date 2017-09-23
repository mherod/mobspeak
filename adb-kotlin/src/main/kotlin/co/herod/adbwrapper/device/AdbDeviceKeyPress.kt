@file:Suppress("unused")

package co.herod.adbwrapper.device

import co.herod.adbwrapper.S
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.pressKey
import co.herod.kotlin.ext.blocking
import java.util.concurrent.TimeUnit

fun AdbDevice.pressKey() = AdbDeviceKeyPress(this)

class AdbDeviceKeyPress @JvmOverloads constructor(
        val adbDevice: AdbDevice,
        private val defaultTimeout: Int = 10,
        private val defaultTimeUnit: TimeUnit = TimeUnit.SECONDS
) {

    fun home() {
        pressKey(adbDevice, S.KEY_EVENT_HOME)
                .blocking(defaultTimeout, defaultTimeUnit)
    }

    fun back() {
        pressKey(adbDevice, S.KEY_EVENT_BACK)
                .blocking(defaultTimeout, defaultTimeUnit)
    }

    fun backspace() {
        pressKey(adbDevice, S.KEY_EVENT_BACKSPACE)
                .blocking(defaultTimeout, defaultTimeUnit)
    }

    fun power() {
        pressKey(adbDevice, S.KEY_EVENT_POWER)
                .blocking(defaultTimeout, defaultTimeUnit)
    }

    fun escape() {
        pressKey(adbDevice, S.KEY_EVENT_ESCAPE)
                .blocking(defaultTimeout, defaultTimeUnit)
    }
}