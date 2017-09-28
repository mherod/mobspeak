@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.ui.sourceUiHierarchy
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.waitWhileUiHierarchy(predicate: (UiHierarchy) -> Boolean?) = with(adbDevice) {
    sourceUiHierarchy()
            .sample(100, TimeUnit.SECONDS)
            .takeWhile { predicate(it) == true }
            .blockingSubscribe()
}
