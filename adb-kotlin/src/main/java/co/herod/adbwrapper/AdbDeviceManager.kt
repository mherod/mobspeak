package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import java.util.concurrent.TimeUnit

object AdbDeviceManager {

    @CheckReturnValue
    fun getConnectedDevice(): AdbDevice? = allDevices()
            .filter(AdbDevice::isConnectedDevice)
            .firstOrError()
            .retryWhen { it.delay(1, TimeUnit.SECONDS) }
            .timeout(10, TimeUnit.SECONDS)
            .blockingGet()

    @CheckReturnValue
    fun getAllDevices(): List<AdbDevice> = allDevices()
            .toList()
            .blockingGet()

    private fun allDevices(): Observable<AdbDevice> =
            AdbProcesses.devices()
                    .filter { "\t" in it }
                    .map { AdbDevice.parseAdbString(it) }
}
