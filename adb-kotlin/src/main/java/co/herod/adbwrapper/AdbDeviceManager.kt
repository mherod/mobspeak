package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import java.util.concurrent.TimeUnit

object AdbDeviceManager {

    @CheckReturnValue
    fun getConnectedDevice(): AdbDevice =
            getConnectedDeviceSingle()
                    .blockingGet()

    @CheckReturnValue
    private fun getConnectedDeviceSingle(): Single<AdbDevice> =
            connectedDevices()
                    .filter(AdbDevice::isConnectedDevice)
                    .firstOrError()
                    .retryWhen { handler -> handler.delay(1, TimeUnit.SECONDS) }
                    .timeout(10, TimeUnit.SECONDS)

    @CheckReturnValue
    fun getAllDevices(): List<AdbDevice> =
            connectedDevices()
                    .toList()
                    .blockingGet()

    private fun connectedDevices(): Observable<AdbDevice> =
            AdbProcesses.devices()
                    .filter { "\t" in it }
                    .map { AdbDevice.parseAdbString(it) }
}
