@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiHierarchy
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.ifUiHierarchy(predicate: (UiHierarchy) -> Boolean): Boolean = with(adbDevice) {
    sampleUiHierarchy()
            .map { predicate(it) }
            .timeout(10, TimeUnit.SECONDS)
            .onErrorReturn { false }
            .blockingFirst(false)
}