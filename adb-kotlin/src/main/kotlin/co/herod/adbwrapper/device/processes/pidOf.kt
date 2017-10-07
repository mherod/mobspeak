package co.herod.adbwrapper.device.processes

import co.herod.adbwrapper.command
import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue

@CheckReturnValue
fun AdbDevice.pidOf(packageName: String): Single<Int> =
        command(cmdStringPid(packageName))
                .map { Integer.parseInt(it) }
                .singleOrError()