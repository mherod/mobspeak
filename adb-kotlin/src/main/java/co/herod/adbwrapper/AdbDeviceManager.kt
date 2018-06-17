/*
 * Copyright (c) 2018. Herod
 */

@file:Suppress("unused")

package co.herod.adbwrapper

import co.herod.adbwrapper.S.Companion.DEVICES
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.processes.parseAdbString
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import java.util.concurrent.TimeUnit

object AdbDeviceManager {

    @JvmStatic
    @CheckReturnValue
    fun getDevice(): AdbDevice? = allDevices()
            .firstOrError()
            .retryWhen { it.delay(1, TimeUnit.SECONDS) }
            .timeout(10, TimeUnit.SECONDS)
            .onErrorReturn { null }
            .blockingGet()

    @JvmStatic
    @CheckReturnValue
    fun getConnectedDevice(): AdbDevice? = allDevices()
            .filter(AdbDevice::physical)
            .firstOrError()
            .retryWhen { it.delay(1, TimeUnit.SECONDS) }
            .timeout(10, TimeUnit.SECONDS)
            .blockingGet()

    @JvmStatic
    @CheckReturnValue
    fun getAllDevices(): List<AdbDevice> = allDevices()
            .toList()
            .blockingGet()
}

private fun allDevices(): Observable<AdbDevice> =
        AdbCommand.Builder()
                .setCommand(DEVICES)
                .observable()
                .filter { "\t" in it }
                .map(::parseAdbString)

