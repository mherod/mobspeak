package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiHierarchy

fun AdbDeviceTestHelper.ifUiHierarchy(predicate: (UiHierarchy) -> Boolean?) = with(adbDevice) {
    sampleUiHierarchy().map { predicate(it) }.blockingFirst(false)
}