package co.herod.adbwrapper.device.processes

import co.herod.adbwrapper.command
import co.herod.adbwrapper.device.kill
import co.herod.adbwrapper.device.pm
import co.herod.adbwrapper.model.AdbDevice
import co.herod.kotlin.log
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import java.util.concurrent.TimeUnit

@CheckReturnValue
fun AdbDevice.murder(packageName: String): Single<Int> =
        command("shell pidof $packageName")
                .repeat()
                .map { Integer.parseInt(it) }
                .doOnNext { pm().kill(packageName) }
                .timeout(3, TimeUnit.SECONDS)
                .doOnSubscribe { log("Trying to murder $packageName") }
                .doOnDispose { }
                .first(0)