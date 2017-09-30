package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.ui.sourceUiHierarchy
import co.herod.kotlin.now
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.sampleUiHierarchy(): Observable<UiHierarchy> =
        Observable.timer(50, TimeUnit.MILLISECONDS)
                .map { now() }
                .flatMap { startTime ->
                    sourceUiHierarchy()
                            .sample(100, TimeUnit.MILLISECONDS)
                            .skipWhile { it.dumpTime.isBefore(startTime) }
                }
