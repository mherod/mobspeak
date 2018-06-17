/*
 * Copyright (c) 2018. Herod
 */

@file:Suppress("unused")

package co.herod.adbwrapper.device

import co.herod.adbwrapper.S.Companion.DISABLE
import co.herod.adbwrapper.S.Companion.ENABLE
import co.herod.adbwrapper.S.Companion.SERVICE
import co.herod.adbwrapper.S.Companion.SHELL
import co.herod.adbwrapper.execute
import co.herod.adbwrapper.model.AdbDevice

fun AdbDevice.enableService(enable: Boolean, serviceType: String) {
    execute("$SHELL $SERVICE $serviceType ${enable.toEnable()}")
}

private fun Boolean.toEnable() = when {
    this -> ENABLE
    else -> DISABLE
}