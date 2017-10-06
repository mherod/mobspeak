package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.ui.sourceUiHierarchy
import co.herod.kotlin.now
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.sampleUiHierarchy(): Observable<UiHierarchy> =
        Observable.just(this)
                .map { now() }
                .flatMap { startTime ->
                    sourceUiHierarchy()
                            .doOnNext { println("TAKE IT") }
                            .sample(20, TimeUnit.MILLISECONDS)
                            .skipWhile { it.dumpTime.isBefore(startTime) }
                            .doOnError { println("waaah $it") }
                }
                .timeout(10, TimeUnit.SECONDS)
