@file:Suppress("unused")

package co.herod.adbwrapper.device

import co.herod.adbwrapper.S
import co.herod.adbwrapper.execute
import co.herod.adbwrapper.model.AdbDevice

fun AdbDevice.enableService(enable: Boolean, serviceType: String) {
    execute("${S.SHELL} ${S.SERVICE} $serviceType ${enable.toEnable()}")
}

private fun Boolean.toEnable() = when {
    this -> S.ENABLE
    else -> S.DISABLE
}