@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiHierarchy
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.ifUiHierarchy(predicate: (UiHierarchy) -> Boolean): Boolean = with(adbDevice) {
    Observable.just(0)
            .flatMap { sampleUiHierarchy() }
            .map { predicate(it) }
            .timeout(10, TimeUnit.SECONDS)
            .doOnError { println("error: $it") }
            .onErrorReturn { false }
            .blockingFirst(false)
}