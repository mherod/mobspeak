/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.ui.sourceUiHierarchy
import co.herod.kotlin.now
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.sampleUiHierarchy(): Observable<UiHierarchy> =
        Observable.just(now())
                .flatMap { startTime ->
                    sourceUiHierarchy()
                            .sample(20, TimeUnit.MILLISECONDS)
                            .skipWhile { it.dumpTime.isBefore(startTime) }
                }
                .timeout(10, TimeUnit.SECONDS)
