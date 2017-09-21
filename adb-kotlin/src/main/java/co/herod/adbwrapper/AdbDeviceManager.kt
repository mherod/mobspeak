package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
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
            .blockingGet()

    @JvmStatic
    @CheckReturnValue
    fun getConnectedDevice(): AdbDevice? = allDevices()
            .filter(AdbDevice::isConnectedDevice)
            .firstOrError()
            .retryWhen { it.delay(1, TimeUnit.SECONDS) }
            .timeout(10, TimeUnit.SECONDS)
            .blockingGet()

    @JvmStatic
    @CheckReturnValue
    fun getAllDevices(): List<AdbDevice> = allDevices()
            .toList()
            .blockingGet()

    private fun allDevices(): Observable<AdbDevice> =
            devices().filter { "\t" in it }.map { AdbDevice.parseAdbString(it) }
}
