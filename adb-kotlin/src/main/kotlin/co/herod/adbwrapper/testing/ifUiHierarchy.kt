@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiHierarchy
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.ifUiHierarchy(predicate: (UiHierarchy) -> Boolean): Boolean = with(adbDevice) {
    Observable.just(true)
            .flatMap { sampleUiHierarchy() }
            .map { predicate(it) }
            .timeout(10, TimeUnit.SECONDS)
            .doOnError { println("error: $it"); it.printStackTrace() }
            .onErrorReturn { false }
            .blockingFirst(false)
}