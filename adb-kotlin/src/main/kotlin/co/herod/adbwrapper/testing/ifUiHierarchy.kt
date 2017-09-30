@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiHierarchy

fun AdbDeviceTestHelper.ifUiHierarchy(predicate: (UiHierarchy) -> Boolean): Boolean = with(adbDevice) {
    sampleUiHierarchy().map { predicate(it) }.blockingFirst(false)
}