@file:Suppress("unused")

package co.herod.adbwrapper.device

import co.herod.adbwrapper.*
import co.herod.adbwrapper.model.AdbDevice

fun AdbDevice.enableService(enable: Boolean, serviceType: String) {
    execute("$SHELL $SERVICE $serviceType ${enable.toEnable()}")
}

private fun Boolean.toEnable() = when {
    this -> ENABLE
    else -> DISABLE
}