@file:Suppress("unused")

package co.herod.adbwrapper.device.processes

import co.herod.adbwrapper.device.kill
import co.herod.adbwrapper.device.pm
import co.herod.adbwrapper.model.AdbDevice
import co.herod.kotlin.log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import java.util.concurrent.TimeUnit

@CheckReturnValue
fun AdbDevice.murder(packageName: String): Observable<Int> =
        pidOf(packageName)
                .retry(1)
                .onErrorReturn { 0 }
                .toObservable()
                .doOnNext { println(it) }
                .takeWhile { it > 0 }
                .doOnNext { pm().kill(packageName) }
                .timeout(3, TimeUnit.SECONDS)
                .doOnSubscribe { println("Trying to murder $packageName") }
                .doOnDispose { }

